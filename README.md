# data-extraction-quarkus-experiments

# Let's serve the model with Ollama:

In a first shell, execute command below:

```
docker run --rm -it -v cqex-data-extract-ollama:/root/.ollama -p 11434:11434 --name cqex-data-extract-ollama ollama/ollama:0.5.7
```

In a second shell, execute command below:

```
docker exec -it cqex-data-extract-ollama ollama pull granite3-dense
```

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/data-extraction-quarkus-experiments-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing Jakarta REST and more
