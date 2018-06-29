# distributed-agents

Development:

- Download Wildfly 9.0.2
- Set as target runtime
- Set standalone.xml from root folder as configuration file.

- Clone repo
- File -> import -> existing projects into workspace -> browse -> select "distributed-agents"
- Make sure that projects 2 projects and 4 modules are selected(Check "Search for nested projects" is not checked) and finish
- Make sure this folder is empty before deployment `%PATH_TO_WILDFLY_FOLDER%\wildfly-9.0.2.Final\standalone\deployments`
- AgentApp -> Run as -> Run on Server -> Wildfly 9.0.2 

Two Server Environment:

- Delete master.properties file from AgentEJB/src/test folder, if present.
- Deploy project to server.
- Find master.properties file inside data folder of project and set the master-address property to IP address of the server you deployed project on.

- 1) Create a new WildFly 9.0.2 server on your file system.
    - Make sure this folder is empty `%PATH_TO_WILDFLY_FOLDER%\wildfly-9.0.2.Final\standalone\deployments`
- 2) Rename the 127.0.0.1 address in standalone.xml file to another IP address.
- 3) Create new server in Eclipse and new runtime based on new Wildfly 9.0.2 server.
- 4) Set new server's host to the IP address that you replaced 127.0.0.1 in standalone.xml with.
- 5) Copy the master.properties file to AgentAppEJB/src/test.
- 6) Rename the node-name property of AgentAppEJB/src/test/master.properties file to something unique.
- 7) Rename the node-address property of AgentAppEJB/src/test/master.properties file to the IP address from 4).
- 8) Deploy the project on new server.

--Repeat steps 1 to 8 for each new server you run.

Database:
 
- `cd 'C:\Program Files\MongoDB\Server\3.6\bin\'`
- `.\mongod.exe`

ClientApp:

- `npm install`
- `ng serve -o`