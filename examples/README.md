
# CommonAPI examples

This is a collection of example Franca/CommonAPI implementations used to test the
Franca/CommonAPI tooling and cmake integration used in meta-bistro.

The design patterns for managing dependencies that are supported and suggested
by the integration are examplified and described by these example implementations as well.

The intention is to use the cmake CommonAPI tooling integration to build
the components in this repo, in order to verify the integration and have a set
of working examples to experiment with or use as templates.


## Structure

The directory structure and contents reflect the patterns described below.
In addition to the patterns, there are some functional requirements verified
by the examples which means that there are test data directories and some
additional files related to some examples.


## Patterns

The typical patterns supported by the CommonAPI cmake integration are:

### Basic
A service implements an interface which is used by a client.

#### Implementation pattern
The service owns the interface in the sense that the interface definition is version controlled
in the same package as the service. The service package builds a library with stub and proxy
code for the interface, and installs this library together with the generated headers,
and the relevant interface files. The client ‘uses’ the service, and does not generate any
code itself.

#### Example
In the directory `basic`, there are examples of a service and a client that exemplifies the
basic pattern.


### Circular dependency
Service `A` and service `B` both implement interfaces which the other service uses as a client.
Service `A` fails to build because it depends on service `B` to be installed and service `B` fails
to build because it depends on service `A` to be installed.

#### Implementation pattern
The interfaces of each service are put in separate packages, called 'backends'. The backend packages
builds and installs as a normal services but does not implement the interface. Service `A` and
service `B` both 'uses' the respective backends.

#### Example
In the direcotry `circulardependency`, there are examples of two services which interfaces are
generated and installed by two respective backend packages.


### Shared types
Service `A` and service `B` both implements diffrent interfaces that includes the same type-collection `T`.
Both services need to include `T` but the definition of `T` should not be version controlled together with
each service.

#### Implementation pattern
The type-collection `T` is installed by a separate package, e.g. a normal CMake project that installs
the fidl file in an appropriate location. Any service that needs it specifies the location of the
type-collections file to include it in the service build.

#### Example
In the directory `sharedtypes`, there are examples of a package in `sharedtypes-example-typespackage`
that installs a type-collections file and an example of a service in `sharedtypes-example-service`
that uses it. The pattern is for when more than one service uses the same type-collection but for
this pattern example, it is enough to have one service exemplified. Any additional services would use
the same pattern and API.


## Configuring and building the examples

As it is often covenient to keep things separated from the system while developing and testing,
the below section describes how to work with the examples with the assumption that the dependencies
are installed in one location and the examples in another.

The description in this section is based on the assumption that D-Bus, CommonAPI and
CommnonAPI-DBus runtimes, the generator, and this cmake integration, are installed in
`/tmp/fakeroot/`. And that these examples are to be installed in `/tmp/testing/`

Set up the path to the generator binary:

    export PATH=/tmp/fakeroot/bin/:PATH

Configure an example by running the following from the directory where the first `CMakeLists.txt`
file is located (same for all examples):

    PKG_CONFIG_PATH=/tmp/fakeroot/lib/pkgconfig/:/tmp/testing/lib/pkgconfig/ LD_LIBRARY_PATH=/tmp/fakeroot/lib:/tmp/testing/lib/ cmake -H.  -Bbuild -DCMAKE_PREFIX_PATH=/tmp/fakeroot/:/tmp/testing/ -DCMAKE_INSTALL_PREFIX=/tmp/testing/

To build:

    cd build
    make

To install (when there is an install target):

    make install
