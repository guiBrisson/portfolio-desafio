# Network Module

This module provides the networking capabilities for the application, primarily focusing on fetching
data from the Globo.com API.

## Functionality

The network module is responsible for:

* **Creating and configuring an HTTP client:** It utilizes Ktor's `HttpClient` with the Android
  engine. The client is configured to make requests to the default url and handle JSON responses.
* **Defining API endpoints:** The `FeedApi` interface outlines the available API calls related to
  retrieving feed data.
* **Implementing API calls:** The `feedApi` function provides an implementation of the `FeedApi`
  interface using the configured HTTP client. It handles constructing the request URLs and executing
  the requests.

## Usage

To use the network module, you can obtain an instance of the `FeedApi` using the `feedApi` function:
```kotlin
    // This returns a HttpResponse
    feedApi().getFeed("g1")
```
