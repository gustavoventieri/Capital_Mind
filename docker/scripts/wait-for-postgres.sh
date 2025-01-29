#!/bin/bash
set -e

host="$DB_HOST"
port=5432

echo "Waiting for PostgreSQL to start on $host:$port..."

while ! nc -z $host $port; do
  sleep 0.1
done

echo "PostgreSQL started"

exec "$@"
