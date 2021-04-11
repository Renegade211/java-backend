# Device Manager API

To run use IDE or Docker

To run the database:
docker run --name [container_name] -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres

Then in root directory of your project:

docker cp deviceservice_db.sql [container_name]:/

docker exec -it [container_name] bash

psql -U [postgres] --file deviceservice_db.sql

Main API endpoints:

POST /api/users/login

POST /api/users/register

GET /api/devices/[device_id]

POST /api/devices/add

GET /api/devices/all

DELETE /api/devices/delete/[device_id]

PUT /api/devices/update/[device_id]

To perform device operations you must provide authorization token as header:

Authorization: Bearer [token]

To access all devices as backoffice:
Authorization: Bearer BACKOFFICE

Backoffice has only permission to view and delete devices.

If you have any questions, feel free to ask.

Thanks.

