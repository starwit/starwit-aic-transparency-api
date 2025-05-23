# Docker compose setup
These are some notes, on how to use Docker compose scripts.

## Dev setup without authentication
Compose file [start-services-noauth.yaml](start-services-noauth.yaml) starts
* Minio
* SbomGenerator
* Postgres
* AI Cockpit
* SbomHoster

SbomHoster is running Apache httpd and serves static files and so simulates downloadable content. It serves content of folder [static-content](static-content/). Add anything you need for module description/documentation.

## Dev setup with authentication
TODO