# Back Stack

A simple backend framework for CRUD applications designed to consolidate each layer into an abstract `Entity` implementation which can be overridden as needed.
For simple CRUD types, only model definition and dependency injection need to be implemented explicitly. 
Data access, service layer, REST endpoints, and HTML presentation is all handled automatically by the base `Entity` implementations.

## Modules

`core`: Model type definition and common utilities used by the entire stack.

`data`: Data access (Readers/Writers) using JDBC.

`service`: Services for handling business logic.

`api`: Controllers for REST endpoints returning json.

`web`: Presenters to render HTML for web application.

`app`: Spring-Boot application that ties it all together.

## Development

Below are the steps of the development process for adding a new type:

**Note: WIth more work, only step 1 will be required.**
(Reader, Writer, Service, Controller, Presenter, and dependency injection can all be handled automatically.)

1. Define a new type in `com.alver.core.model` extending `Entity` using `@Immutables` annotation.
2. Implement a Reader/Writer in `com.alver.data` extending `EntityReader`/`EntityWriter`.
    1. Ensure your database schema matches.
3. Implement a Service in `com.alver.service` extending `EntityService`.
4. Implement a Controller/Presenter in `com.alver.api` extending `EntityController`/`EntityPresenter`.
   1. Write a Mustache template for Presenter.
5. Configure Spring dependencies in `com.alver.app`.


## Presentation

Uses a Mustache based component architecture and a sprinkle of Reflection to dynamically generate HTML for any type.
Recursively traverses java object hierarchy generating a `Model` for each complex type and a `FieldModel` for each terminal type (`String`, `Integer`, `Instant`, etc.).
`Models` can also customize their rendering by overriding the default `renderTemplate` field and passing a custom render function.

### Field Components
Current:
1. String
2. Select Dropdown (for enums)
3. Radio Buttons (for boolean)

Future:
1. Primitives String, Character, Integer, Short, Long, Float, Double, Boolean, Byte)
2. Multi-Select
3. Tables
