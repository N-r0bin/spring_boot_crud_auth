# Project Overview
This repository demonstrates the implementation of authentication, authorization, and role-based permissions using Spring Boot. The project includes the following key features:

- <b>Token-Based Authentication:</b> Generates a token upon user registration. The user must be authenticated using this token in the header for subsequent requests.
  
- <b>Role-Based Access Control:</b>
     - <b>RA (Read-Only Access):</b> Users with the RA role can only read data.
     - <b>TA (Full Access):</b> Users with the TA role can read, create, update, and delete data. Additionally, changes made by TA users are logged with timestamps, indicating who made the changes and to whom.

- <b>Database:</b> PostgreSQL is used as the database to store user information and logs.

  Database tables:
  
  <img src="https://github.com/N-r0bin/spring_boot_crud_auth/assets/83023450/5d7c6cc1-9e28-467a-88b1-ae7ffa17ee41" alt="table" width="50%"/>
  <img src="https://github.com/N-r0bin/spring_boot_crud_auth/assets/83023450/ca927c85-75a4-4a0e-b9eb-879161dfe50b" alt="tokenTable" width="50%"/>
  <img src="https://github.com/N-r0bin/spring_boot_crud_auth/assets/83023450/20e716d6-3f0b-4836-bece-9d61bcb44fce" alt="tableContent" width="50%"/>

- <b>Testing:</b> Postman is used for testing the API endpoints.

## Features
1. <b>User Registration:</b>
    - Endpoint: '/api/v1/auth/register'
    - Registers a new user and generates a token.

    <img src="https://github.com/N-r0bin/spring_boot_crud_auth/assets/83023450/853473c7-b64f-4e00-861f-3a4d2e0d5c25" alt="registerPMImage" width="50%"/>

2. <b>User Authentication:</b>
    - Endpoint: '/api/v1/auth/authenticate'
    - Authenticates the user and returns a token.

   <img src="https://github.com/N-r0bin/spring_boot_crud_auth/assets/83023450/45e63c8a-c1f7-42f9-8d45-d6066a825e44" alt="authPMImage" width="50%"/>

3. <b>Role-Based Actions:</b>
    - RA Role: Can only read data.
    - TA Role: Can perform CRUD operations. Changes are logged with timestamps and user details.
      
### Conclusion
This project showcases a basic implementation of security and role management using Spring Boot. 
