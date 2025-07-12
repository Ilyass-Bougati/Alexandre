# Server
To run the server, you'll first have to create a `tempo-data` directory
```bash
mkdir configurations/tempo/tempo-data
chmod 777 configurations/tempo/tempo-data
```
The you can run the project using `docker`

# Running the project
You can run the project using the `deploy.sh` script, it'll take care of building and running the microservices for you
```bash
# --compose    : to use docker compose, otherwise it'll use k8s
# --build-all  : to build all the images, if not used you'll use the already existing images in your local docker registry
# --ntfy       : when used with --build-all or --precompile, notifies your phone when building the project is done using Ntfy
# --precompile : compile the project before building the image, makes building it faster
./deploy.sh --compose --build-all --ntfy

# for fast building, need to have mvn and jdk installed locally
./deploy.sh --compose --precompile --ntfy
```
