# ubiquitous-burger project

## How to build

We are using the frontend-maven-plugin to
install node and npm, download the frontend dependencies and run webpack.
All this will be executed on maven `genereta-resources` phase so it will be
triggered when running the following maven command:

```$bash
./mvnw clean install
```


To rebuild only the frontend you can run:

```$bash
./mvnw frontend:webpack
```

## How to run

As we are using spring boot, to run the generated package you might
use the maven frontend plugin:

```$bash
./mvnw spring-boot:run
```

or you can run the fat jar directly:

```$bash
java -jar target/core-0.0.1-SNAPSHOT.jar
```

It will serve the application in your local machine on port 8080 so
you can access the app on the browser at:
`http:\\localhost:8080\`
