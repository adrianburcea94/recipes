#!/bin/bash

echo 'Stopping MongoDB and the Recipes application'
echo '...'
docker-compose -p abnamrorecipes down

echo 'Doing system cleanup'
echo '...'
docker system prune -f