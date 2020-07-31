IF OBJECT_ID ('dbo.EMPLOYEE') IS NOT NULL
	DROP TABLE dbo.EMPLOYEE
GO

CREATE TABLE dbo.EMPLOYEE
	(
	EMPUNIQUEID UNIQUEIDENTIFIER NOT NULL,
	NAME        NVARCHAR (100) NOT NULL,
	AGE         INT NULL,
	GENDER      NVARCHAR (100) NULL,
	CONSTRAINT PK_EMPLOYEE PRIMARY KEY (EMPUNIQUEID)
	)
GO

IF OBJECT_ID ('dbo.Credentials') IS NOT NULL
	DROP TABLE dbo.Credentials
GO

CREATE TABLE dbo.Credentials
	(
	UID      UNIQUEIDENTIFIER DEFAULT (newid()) NOT NULL,
	EMPUID   UNIQUEIDENTIFIER NOT NULL,
	USERNAME NVARCHAR (100) NOT NULL,
	PASSWORD NVARCHAR (1024) NOT NULL,
	CONSTRAINT PK_Credentials PRIMARY KEY (EMPUID)
	)
GO

IF OBJECT_ID ('dbo.EmployeeSession') IS NOT NULL
	DROP TABLE dbo.EmployeeSession
GO

CREATE TABLE dbo.EmployeeSession
	(
	EMPUNIQUEID  UNIQUEIDENTIFIER NOT NULL,
	sessionToken UNIQUEIDENTIFIER NOT NULL,
	ISALIVE      BIT DEFAULT ((0)) NULL,
	CONSTRAINT PK_EmployeeSession PRIMARY KEY (EMPUNIQUEID)
	)
GO
