#!/bin/sh
echo trying to stop already running container...
sh ./docker.stop.sh
echo running container...
docker run --rm -d --name sakila-db -p 33306:3306 jinahya/sakila-db
