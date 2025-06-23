#!/usr/bin/bash
docker stop $(docker ps -a -q)
docker rm -fv $(docker ps -a -q)
docker volume prune --all -f