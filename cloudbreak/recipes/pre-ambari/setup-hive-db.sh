#!/bin/bash

postgres='postgres'
db='hive'
user='hive'
passwd='Hadoop3_1'

postgres_dir='/var/lib/pgsql'
postgres_jdbc_driver='/usr/share/java/postgresql-jdbc.jar'
pg_ctl='/usr/bin/pg_ctl'


cd $postgres_dir

echo "CREATE DATABASE $db;" | sudo -u $postgres psql -U postgres
echo "CREATE USER $user WITH PASSWORD '$passwd';" | sudo -u $postgres psql -U postgres
echo "GRANT ALL PRIVILEGES ON DATABASE $db TO $user;" | sudo -u $postgres psql -U postgres

ambari-server setup --jdbc-db=postgres --jdbc-driver=$postgres_jdbc_driver

cat >> "$postgres_dir/data/pg_hba.conf" <<EOF
local  all  ambari,hive trust
host   all  ambari,hive 0.0.0.0/0	trust
host   all  ambari,hive ::/0	trust
EOF

cd $postgres_dir && sudo -u postgres $pg_ctl -D "$postgres_dir/data" reload
