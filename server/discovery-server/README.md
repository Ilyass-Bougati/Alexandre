# Docker
to build the image you'll have to run this command from the `server` directory
```bash
docker build . -f discovery-server/Dockerfile -t alexandre/discovery-server
docker run -p 8761:8761 alexandre/discovery-server
```
