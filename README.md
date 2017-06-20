# messenger

A simple backend to test (JEE7) Jax-RS, CDI, JPA. Also using Maven and the swagger-maven-plugin to generate rest-api-documentary.
To get a graphical representation of the rest-apis, just copy and paste the content of the swagger.json file into the swagger online editor at swagger.io. The latest api is also available at https://swaggerhub.com/apis/AthenasCap/Messenger/

The maven-archetype for this project is Adam bien's airhacks/jee7-essentials.
A thin-war is produced, which allowes for efficient deployment with docker.

The Api-Endpoints are accessible at {host}/messenger/resources/{endpoint}.

Endpoints implemented so far: profiles, messages, comments.

All still work in progress, buggy and a mess in general. Sounds like fun =).
