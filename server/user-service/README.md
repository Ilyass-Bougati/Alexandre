# UserService
This service is responsible for the management of user credentials, profile details and payment methods.

![img](imgs/database.png)

Each entity has its own corresponding _service, mapper, DTO, repository and controller_. With the **CRUD** implemented
for all the entities.

# Docker
to build the image you'll have to run this command from the `server` directory
```bash
docker build . -f user-service/Dockerfile -t alexandre/user-service
docker run -p 8080:8080 alexandre/user-service
```
