# Docker
to build the image you'll have to run this command from the `server` directory
```bash
docker build . -f inventory-service/Dockerfile -t inventory-service
docker run -p 8000:8000 inventory-service
```
