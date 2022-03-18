#!/bin/bash

echo 'Starting MongoDB and the Recipes application'
echo '...'
docker-compose -p abnamrorecipes up --build -d

MONGODB_PRIMARY_CID=$(docker ps -q -f 'name=mongodb-primary')