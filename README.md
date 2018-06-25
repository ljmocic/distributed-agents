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

Database:
 
- `cd 'C:\Program Files\MongoDB\Server\3.6\bin\'`
- `.\mongod.exe`

ClientApp:

- `npm install`
- `ng serve -o`