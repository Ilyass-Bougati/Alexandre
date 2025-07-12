# Server
To run the server, you'll first have to create a `tempo-data` directory
```bash
mkdir configurations/tempo/tempo-data
chmod 777 configurations/tempo/tempo-data
```
The you can run the project using `docker`
```bash
# Running the tracing services
docker compose -f compose.traces.yaml up
# Running the postgresql databases containers, and keycloak
docker compose up
```
Then you can run each microservice individually using
```bash
cd microservice-dir
mvn spring-boot:run
```

```bash
./deploy.sh --compose --build-all --ntfy
```
