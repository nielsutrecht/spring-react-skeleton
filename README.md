# Parallel Builds / Serving Static Files in Spring example

This repository contains an example on how you can set up a single repository containing both a Spring REST API as well as a Single Page Application front-end (in this case: React) where you can then have both front-end and back-end builds use their respective tooling, being able to run both builds in parallel, and still package the files in a single Docker container having Tomcat serve the static files.

This is ideal for example projects, REST APIs that need to have a simple user interface, or Spring services serving a low volume of users.

In addition the work can easily be moved to building a separate container, using for example Nginx, that serves the front-end. 

## Components

### Front-end

The front-end is a bare bones React application. All it does it call the back-end `/api/ip` endpoint to demonstrate that it can interact with the back-end in both development and production builds. 

You can start the front-end in a dev server with `yarn start`. Make sure you install the dependencies with `npm install` first!

### Back-end 

The back-end is a very simple Spring + Kotlin REST API. There are 3 primary components in the Application.kt file:

* WebConfig: the configuration class that adds a ResourceHandler to serve /front-end on /
* MvcController: a simple controller that redirects traffic on "/" to "/index.html", because this does not happen automatically.
* ApiController: a simple REST controller serving a single `GET /api/ip` endpoint for demonstration purposes.

### Clients

A single Client class that demonstrates that you can have any number of maven modules that all get combined into your Spring service.

## Building

The project contains 3 scripts: `1-build-fe.sh` (builds the front-end using Yarn), `2-build-be.sh` (builds the back end using Maven) and `3-build.docker.sh` (combines the artifacts into a single docker file). These scripts can be called from your CI/CD pipeline. The first two can run in parallel and don't depend on each other; but make sure that the actual artifacts written to `build/back-end` and `build/front-end` get stored for the 3rd stage.

The 3rd script will build a docker image with the name parallel-builds and tags 1.0 and latest. In a CI/CD pipeline you'd want to use for example the build number for versioning. 

## Running

You can run the docker image:

    docker run -p 8080:8080 --name parallel-builds-example parallel-builds:latest 
    
And then should be able to navigate to http://localhost:8080