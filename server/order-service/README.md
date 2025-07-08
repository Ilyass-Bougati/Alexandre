# Docker
to build the image you'll have to run this command from the `server` directory
```bash
docker build . -f order-service/Dockerfile -t order-service
docker run -p 8000:8000 order-service
```
