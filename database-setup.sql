-- SQL Server Database Setup Script
-- Run this script to create the SurveyDB database

-- Create Database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'SurveyDB')
BEGIN
    CREATE DATABASE SurveyDB;
END
GO

USE SurveyDB;
GO

-- The application will automatically create tables using JPA/Hibernate
-- with spring.jpa.hibernate.ddl-auto=update

-- Optional: Create a user for the application
-- Uncomment and modify as needed
/*
CREATE LOGIN survey_user WITH PASSWORD = 'YourSecurePassword123!';
CREATE USER survey_user FOR LOGIN survey_user;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE TABLE, ALTER ON DATABASE::SurveyDB TO survey_user;
GO
*/

PRINT 'Database setup complete. The application will create tables automatically on first run.';
