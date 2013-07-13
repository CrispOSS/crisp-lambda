
# Crisp Lambda 

Crisp Lambda is an implementation of actor model using Java 8 lambda expressions.

## Build

Crisp Lambda uses [Gradle][gradle] as its build tool. To build the source code:

```bash
./gradlew build
```

It configures and downloads Gradle if necessary and then builds the project.

### Examples

As an example a ping-pong model of actors is provided in the example package of the source.
There are two examples with two different approaches of concurrency using Crisp Lambda API. 

1. `DedicatedThreadExample` that uses a dedicated thread ownership to execute actor's messages.
2. `SharedThreadPoolExample` that uses a shared pool of threads and shared message queue to execute actor's messages.

To run the examples, edit `build.gradle` and choose the main class, then:

```bash
./gradlew run
```

[gradle]: http://www.gradle.org/
 