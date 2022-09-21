#!/bin/bash

container_name="docker_master_db_1"

docker exec -it $container_name mysql -uroot -proot