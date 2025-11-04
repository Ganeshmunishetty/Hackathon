-- Smart City Database Initialization Script
-- Run this after creating the database

-- Connect to the database first
\c smartcity_db;

-- This script is optional as JPA will auto-create tables
-- But you can use this to create initial data

-- Note: The tables will be created automatically by Hibernate
-- when you start the services with ddl-auto: update

-- Optional: Create initial admin user (after first service start)
-- This would typically be done via API or a data initialization script

-- Example roles (create via API):
-- INSERT INTO roles (id, name, description, created_at, updated_at) 
-- VALUES (gen_random_uuid(), 'ADMIN', 'System Administrator', NOW(), NOW());

-- Example permissions (create via API):
-- INSERT INTO permissions (id, name, description, resource, action, created_at, updated_at)
-- VALUES 
--   (gen_random_uuid(), 'READ_INCIDENTS', 'Read incidents', 'incidents', 'read', NOW(), NOW()),
--   (gen_random_uuid(), 'WRITE_INCIDENTS', 'Write incidents', 'incidents', 'write', NOW(), NOW()),
--   (gen_random_uuid(), 'ADMIN_USERS', 'Admin users', 'users', 'admin', NOW(), NOW());

-- For now, just ensure the database exists
-- The services will handle table creation automatically

