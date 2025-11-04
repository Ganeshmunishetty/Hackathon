#!/bin/bash

echo "Creating Smart City Database..."

# Prompt for PostgreSQL username (default: postgres)
read -p "PostgreSQL username [postgres]: " DB_USER
DB_USER=${DB_USER:-postgres}

# Prompt for password
read -sp "PostgreSQL password: " DB_PASSWORD
echo ""

# Create database
PGPASSWORD=$DB_PASSWORD psql -U $DB_USER -h localhost -c "CREATE DATABASE smartcity_db;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Database 'smartcity_db' created successfully!"
else
    echo "⚠️  Database might already exist or there was an error."
    echo "   Continuing anyway..."
fi

echo ""
echo "Database setup complete!"
echo "You can now start the services."

