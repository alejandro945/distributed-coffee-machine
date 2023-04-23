# Distributed Coffee Machine ☕️

## Goals 🫰
Put into practice the deployment of a simple distributed software system, taking into account the considerations and factors that this implies, for example the installation and configuration of the database.

### Components

1. Coffeemach: component that represents an instance of a coffee machine, the point of interaction with the customer of the business of this system.
2. Cm_logistics: component that handles the responsibilities of supply and repair logistics to resolve alarms from coffee machines.
3. Warehouse: component that represents the warehouse or site where the physical inventory of supplies and ingredients is managed, as well as the management of coffee machine repair tools.
4. CentralServer: component in charge of the central administration of all the business flows of the coffee machine system, that is, of the administration of the entire business.
5. Database: subsystem with the definition of the data model in which the information related to the centralized administration of the entire system is persisted and maintained in an integral manner. This excludes the local and own data of each coffee machine, which are handled by each one.

### Remotes machines

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

### 1- Mount our jenkins server on your local machine
In root project folder run the following script:

```bash
docker compose up -d
```
If you see your docker process (containers) that are runnig you must have:

|CONTAINER ID    | IMAGE|   COMMAND  |CREATED|STATUS|PORTS|NAMES|
|----------------|------|------------|-------|------|-----|-----|
8076bbc623ff | jenkins/jenkins | "/usr/bin/tini -- /u…" |  24 seconds ago | Up 21 seconds | 0.0.0.0:8080->8080/tcp, :::8080->8080/tcp, 50000/tcp  | jenkins

See the logs of your previuos procees to extract first dummy **password**

```bash
sudo docker logs -f jenkins
```

1. Navigate to the following route:
    http://localhost:8080

2. Configuring GitHub credentials in Jenkins

    On each Jenkins server, each developer must configure GitHub access credentials so that Jenkins can interact with the corresponding repository. This can be done in the "Credentials" section of the Jenkins configuration, where the developer's GitHub account access credentials, such as username and password or a personal access token, must be configured.

    To set up GitHub credentials in Jenkins, follow these steps:

    - Access your Jenkins instance in your web browser.
    - On the Jenkins home page, select "Credentials" in the left side menu.
    - Click the "Global credentials (unrestricted)" link.
    - Click the "Add Credentials" link to add new credentials.
    - In the "Kind" section, select "Username with password" if you want to use a username and password to      authenticate with GitHub, or select "SSH Username with private key" if you want to use an SSH key to authenticate.
    - Complete the required fields according to the type of credentials you have selected:
    - For "Username with password":
        - "Username": Enter your GitHub username.
        - "Password": Enter your GitHub password.
        - "ID": Enter a unique identifier for these credentials in Jenkins.
    - For "SSH Username with private key":
        - "Username": Enter your GitHub username.
        - "Private Key": Enter your private SSH key in the text field or load the SSH key file from your file system.
        - "Passphrase": If your SSH key is protected with a passphrase, enter it here.
        - "ID": Enter a unique identifier for these credentials in Jenkins.
    - Click the "Add" button to add the credentials.

3. Install "GitHub Integration Plugin"

    To set up the GitHub integration in Jenkins, follow these steps:

    - Access your Jenkins instance in your web browser.
    - On the Jenkins home page, select "Manage Jenkins" in the left side menu.
    - Select "Manage Plugins" from the list of options.
    - Go to the "Available" tab and search for "GitHub Integration Plugin".
    - Select the checkbox next to "GitHub Integration Plugin" to select it.
    - Click the "Install without restart" button to install the plugin in Jenkins.
    -  Wait for the plugin installation to complete and go to the Jenkins home page.
    - On the Jenkins home page, select "Manage Jenkins" in the left side menu again.
    - Select "Configure System" from the list of options. 
    - Scroll down to the "GitHub" section and set the following fields:
    - "GitHub Webhook": Check the checkbox if you want to enable integration with GitHub webhooks.
    - "Credentials": Select the GitHub credentials that you previously configured in Jenkins.
    - "GitHub API URL": Enter the URL of the GitHub API, usually "https://api.github.com".
    - "Manage Hooks": Check the checkbox if you want to enable GitHub hook management from Jenkins.
    - "Override Hook URL": Enter the Jenkins URL for GitHub webhooks, if you want to override the default URL.
    - Click the "Save" button to save the settings.

The only thing missing is to add the webhook in our github repository so that every time a commit is made in the branch specified by the strategy, a request is fired to our jenkins server that must have a public IP. Unfortunately, due to cost issues, our server is local and developers must manually run their pipeline.

## 2- Install sshpass

We want to automate the way we access our remote machines on the second floor of the laboratory, for which we will use the sshpass tool to inject the password of the computer in question, we know the risks that this has but we do not have administrator privileges on these computers to store our public key.

**MacOs 🖥️**
```bash
brew install hudochenkov/sshpass/sshpass
```

**Linux 🐧**
```bash
sudo apt-get install sshpass
```

**Windows 🥹**
You cant run sshpass in windows. You can however use putty via the windows command line, to achieve the same thing 💥:

```bash
putty -load "host" -l username -pw password
```

Try it on your local machine:

```bash
sshpass -p "xxxxx" ssh swarch@xhgrid2
```

## 3-Pipeline

1. Create bash script to automate the data base configuration (Alex Sanchez) ---> Output user and credentials

Once our data base is configured:

**Clone Reposiory stage**
We must pull our repository to see the lasta changes in our jenkinsfile and code respectively.

**Connect to server remote machine**
```bash
sshpass -p "xxxxx" ssh swarch@xhgrid11
```

**Connect to first client remote machine**
```bash
sshpass -p "xxxxx" ssh swarch@xhgrid9
```

**Connect to second client remote machine**
```bash
sshpass -p "xxxxx" ssh swarch@xhgrid10
```

## Remarks
Note that the configuration files must already be created when the pipeline is executed. 💀

It is important to take into account that the pipeline as it is written may have some errors or areas for improvement, such as error handling in case of failures in copy operations or execution of SSH commands, the use of secure access keys instead of passwords in SSH commands, and the management of variables and configurations in a more structured and parameterized way.
