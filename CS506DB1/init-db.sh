#!/bin/bash

DB_HOST=localhost
DB_PORT=33306
DB_USER=root
DB_PASS=root123
DB_NAME=UserValues

echo "Setting up the database..."

mysql -u "$DB_USER" -p"$DB_PASS" -h "$DB_HOST" -P "$DB_PORT" "$DB_NAME" < path/to/your/schema.sql

if [ $? -eq 0 ]; then
    echo "Schema applied successfully!"
else
    echo "Failed to apply schema."
    exit 1
fi

mysql -u "$DB_USER" -p"$DB_PASS" -h "$DB_HOST" -P "$DB_PORT" "$DB_NAME" < path/to/your/data.sql

if [ $? -eq 0 ]; then
    echo "Data inserted successfully!"
else
    echo "Failed to insert data."
    exit 1
fi

echo "Database setup complete!"

# 1) make script executable: chmod +x init-db.sh
# 2) run "./init-db.sh" to reset database