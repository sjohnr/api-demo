# api-demo
API demo for the talk "How an API-driven redesign can jumpstart your new UX" at HDC 2018.

## Project Structure

* `api`: Spring Boot application containing the demo API
* `buildSrc`: Groovy module containing a custom jsonschema2pojo annotator to add Swagger annotations
* `model`: Playground for building POJOs with JS2P - Deprecated and will be removed
* `reddit-client`: API client for Reddit, similar to a generated API client but hand-coded
* `reddit-swagger-client`: Generated API client for Reddit - Deprecated and will be removed
* `sportradar-client`: API client for NFL data with sportradar.us, similar to a generated API client but hand-coded

## Getting Started

To get started, run the parent pom (Bill of Materials) at the root of the project with Maven:

> `mvn install`

Then run the gradle build to generate sources and create an executable jar file:

> `gradle bootJar`

Finally, push to PCF with the cloud-foundry CLI using the included `manifest.yml` at the root of the project:

> `cf push`
