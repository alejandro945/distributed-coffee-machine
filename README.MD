# Distributed Coffee Machine ☕️

## Deployment Stage

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

See the logs of your previuos procees to extract first dummy password

```bash
sudo docker logs -f jenkins
```

Navigate to the following route:
http://localhost:8080


