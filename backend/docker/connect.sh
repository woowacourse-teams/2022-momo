#!/bin/bash

container_name="momo_mysql"

docker exec -it $container_name mysql -uroot -proot --database=momo
