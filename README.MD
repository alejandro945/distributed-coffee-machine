# Distributed Coffee Machine ☕️

## Goals 🫰
Put into practice the deployment of a simple distributed software system, taking into account the considerations and factors that this implies, for example the installation and configuration of the database.

## Components ⛹🏾‍♂️

1. Coffeemach: component that represents an instance of a coffee machine, the point of interaction with the customer of the business of this system.
2. Cm_logistics: component that handles the responsibilities of supply and repair logistics to resolve alarms from coffee machines.
3. Warehouse: component that represents the warehouse or site where the physical inventory of supplies and ingredients is managed, as well as the management of coffee machine repair tools.
4. CentralServer: component in charge of the central administration of all the business flows of the coffee machine system, that is, of the administration of the entire business.
5. Database: subsystem with the definition of the data model in which the information related to the centralized administration of the entire system is persisted and maintained in an integral manner. This excludes the local and own data of each coffee machine, which are handled by each one.

## Remotes machines

As group we decide to choose the following servers in Liason network:

|COMPONENT  |REMOTE PC NAME| 
|-----------|------------|
|Database   |hgrid2      |
|Coffeemach |hgrid9      |
|Coffeemach |hgrid10     |
|CentralServer|hgrid11   |
|Cm_logistics |hgrid12   |
|Warehouse  |hgrid13     |

**First Aproach:**

Initially, to check the operation of the Central Server, coffemach and Database component, a student will deploy the Central Server, another the database and the two remaining members will deploy a coffeMach (each one) in the selected computers.

## Deployment Stage 🚀

In order to optimize the deployment process of our development cell, we propose a relatively simple continuous integration pipeline that will use a local jenkins server to remotely connect to the defined swarch machine and carry out the compilation scripts.

## 1- Mount our jenkins server on your local machine

In root project folder run the following script:

```bash
docker compose up -d
```
If you see your docker process (containers) that are runnig you must have:

```bash
docker ps
```

|CONTAINER ID    | IMAGE|   COMMAND  |CREATED|STATUS|PORTS|NAMES|
|----------------|------|------------|-------|------|-----|-----|
8076bbc623ff | jenkins/jenkins | "/usr/bin/tini -- /u…" |  24 seconds ago | Up 21 seconds | 0.0.0.0:8080->8080/tcp, :::8080->8080/tcp, 50000/tcp  | jenkins

See the logs of your previuos procees to extract first dummy **password**

```bash
sudo docker logs -f jenkins
```

#### 1.1- Exposing Jenkins to the Internet
You need a way to expose Jenkins to outside networks since this is a requisite to work with external services such as Webhooks. To do so, we are going to use a service called: ngrok. Ngrok can expose local servers to the internet through a public IP. The free tier can work with one online ngrok process and up to 4 tunnels. In this case, we only need one for Jenkins, so the free tier is great for our needs. 

**Install ngrok:**

1. MacOS ans Linux:
    ```bash
    brew install --cask ngrok
    ```

2. Windows: Download the latest version of Ngrok from the official website (https://ngrok.com/download).

**Create an ngrok account to receive token**: Go to https://ngrok.com/ and sign up

**Push authtoken**:
```bash
ngrok config add-authtoken XXXXXXXXXXXXXXXX

ngrok.exe authtoken <token>
```

**Create a Temporal public IP**: Take in mind that every time you will test jenkins locally, you have to run the following command ⚠️
```bash
ngrok http 8080
# https://5b2b-200-3-193-227.ngrok.io
```
#### 1.2- Create a jenkins API Token

To connect our Jenkins server with our Webhook, we will first need to create an API Token to authenticate from the version control provider (Github in this case). 

1. To do so, we need to click on our account and then on the configure option.
2. Click on Add new Token, write a name for it and then press Generate. We need to store this Token in a safe place because there is no way to recover it in the future unless creating a new one.

```bash
114ae705e84a26e1acb7f258612f984ec4
```

#### 1.3 Adding GitHub Credentials

1. To add our Github credentials on Jenkins, we need to go to the dashboard, Manage Jenkins, and Configure System. If a message appears saying that our reverse proxy set up is broken, just click on dismiss. This message is due to the proxy connection using ngrok. 

2. Scroll down to the GitHub section (you need first to have the GitHub plugin installed on Jenkins; if you installed all the recommended plugins at Jenkins installation, it should be there) click on Add GitHub Server, GitHub Server

3. Inside the credentials section click on add, Jenkins.

4. It'll appear a new window. On this window, we should select Secret text

5. Inside the Secret box, we paste the GitHub API token

6. Give it some ID and Description and click on ADD

7. Now we can see this new credential inside the credentials option box, select it, and then click on Test connection. If everything goes right, it should say, Credentials verified for user <My-Github-User>, rate limit: <Number>. If you receive some error, recheck your GitHub API token. Finally, enable the Manage Hooks option and click on save.

#### 1.4  Creating a Jenkins Pipeline

1. To create a Jenkins pipeline to test our webhook, we go to the dashboard, new item, give it a name and click on the Pipeline job.

2. In the General Tab add a description for this pipeline, check the option: GitHub project, and inside the Project Url text box, write the URL for the remote repository to be tested.

3. In the Build Triggers tab, check the box: GitHub hook trigger for GITScm polling. This option will enable this build to run after the Webhook sends the POST request.

4. In the pipeline tab, choose: Pipeline script from SCM option to execute the steps declared in the Jenkinsfile for our project, select Git and paste your repository URL. Then we need to add our credentials. This step is not completely necessary but will enable GitHub to set a mark if our test passed or failed.

5. After clicking on add, it'll appear the credentials window. This time choose Username and Password. Write your GitHub username, but in the password text box, paste your GitHub API token, not your current password to access your GitHub account through the webpage.

6. Now your credential should be visible from the options. 

7. Finally, we choose our branch to build the pipeline and the path for the Jenkinsfile. Click on save

## 2- Github repository configuration

1. To create our GitHub Webhook, we need to open our repository and click on settings. Then click on Webhooks and Add webhook

2. Inside the Payload URL text box, we need to write the direction to our Jenkins Server **[ngrok public ip]**, plus: /github-webhook/. The payload must have both / to work, otherwise it'll throw some error. Inside the Secret box, we'll paste the Jenkins API Token from section **1.2**. 

## 3-Database Configuration

1. Modify the pg_hba.conf file inside data, to give network permissions.

2. Create user and database.
    - psql -h xhgrid2 -U postgres -c "\i script/coffeemach-user.sql"

3. Execute the creation and population scripts to the server.
    - psql -h xhgrid2 -U cofmachu_ssv -d coffeemachine -c "\i script/coffeemach-ddl.sql"
    - psql -h xhgrid2 -U cofmachu_ssv -d coffeemachine -c "\i script/coffeemach-inserts.sql"

For managing the postgres server:

- pg_ctl status
- pg_ctl stop
- pg_ctl -D /var/lib/pgsql/data -l pgstartup.log start

## 4-Jenkins Pipeline
Once our **database** is configured:

**1- Clone Reposiory stage**

We must pull our repository to see the last changes in our jenkinsfile and code respectively.

**2- Copy Source code from jenkins agent to remote pc**
```bash
sshpass -p 'swarch' scp -o StrictHostKeyChecking=no -r ./* swarch@${ip}:./ci-cd-coffee-machine/
```

**3- Build project in remote machine**
```bash
sshpass -p 'swarch' ssh -o StrictHostKeyChecking=no swarch@${ip} 'cd ./ci-cd-coffee-machine/src && gradle build
```

**4- Compile node**
```bash
java -jar ServidorCentral/build/libs/ServidorCentral.jar
```

## 5-X11 Port forwarding

**Windows**

1. Download and install an X11 server for Windows, such as Xming or VcXsrv.

2. Run X11 server on your Windows PC.

3. Verify that the X11 connection is working correctly by running the xclock command. If everything is set up correctly, you should see a graphical clock in a separate window.

**MacOs**

1. Install an X11 server on macOS. You can use XQuartz as an X11 server. You can download and install it from the official website: https://www.xquartz.org/. Once installed, you must start it before proceeding with the next steps.

2. Make sure the "Allow connections from network clients" option is enabled in the XQuartz preferences. You can enable it in the "Security" tab of the XQuartz preferences.

3. Run the Docker container with the -e DISPLAY option to set the DISPLAY environment variable to the container. This environment variable specifies the address of the X11 server on your Mac that the container should use to display graphical applications. The value of the environment variable should be the IP address of your Mac followed by :0

**Linux**

1. Update Package manager
```bash
sudo apt-get update && sudo apt-get upgrade
```

2. Install the X11 server and the necessary tools:

```bash
sudo apt-get install -y xauth xorg openbox
```

3. Configure the X11 server to allow remote connections via SSH:

```bash
sudo sed -i 's/#X11Forwarding no/X11Forwarding yes/' /etc/ssh/sshd_config
sudo sed -i 's/#X11UseLocalhost yes/X11UseLocalhost no/' /etc/ssh/sshd_config
```
4. Restart the SSH service to apply the changes:

```bash
sudo service ssh restart
```

## 6- Remarks
Note that the configuration files must already be created when the pipeline is executed. 💀

It is important to take into account that the pipeline as it is written may have some errors or areas for improvement, such as error handling in case of failures in copy operations or execution of SSH commands, the use of secure access keys instead of passwords in SSH commands, and the management of variables and configurations in a more structured and parameterized way.

Enter as root to jenkins container: 🚦

```bash
docker exec -it --user=root jenkins /bin/bash
```
