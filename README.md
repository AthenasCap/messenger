# messenger

A simple backend to test (JEE7) Jax-RS, CDI, JPA. Also using Maven and the swagger-maven-plugin to generate rest-api-docs.
To get a graphical representation of the Rest-APIs, just copy and paste the content of the swagger.json file into the swagger online editor at swagger.io or visit https://swaggerhub.com/apis/AthenasCap/Messenger/

The maven-archetype for this project is Adam Bien's javaee7-essentials-archetype.
A thin-war is produced, which allowes for efficient deployment with Docker containers.

The Api-Endpoints are accessible at {host}/messenger/resources/{endpoint}.

Endpoints implemented so far: profiles, messages, comments.
