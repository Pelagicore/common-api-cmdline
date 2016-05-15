# Introduction
This package provides CMake integration for CommonAPI code generators.
It enables CommonAPI generated code to be generated at compile-time instead of generating it manually and storing the generated files in your version control system. Using CMake ensures that the generated code is always in sync with both the CommonAPI version used for compilation and the Franca interface definition file.

# Usage
The package is meant to be easy to use. Several macros are available, depending on which code generator you want to use:

* add_commonapi_dbus_service(VARIABLE_NAME DeploymentFile-dbus.fdepl Service.fidl mypackage/Service) : generates and installs a library containing a DBUS backend for the given service
* add_commonapi_someip_service(VARIABLE_NAME DeploymentFile-someip.fdepl Service.fidl mypackage/Service) : generates and installs a library containing a SomeIP backend for the given service
* add_commonapi_service(VARIABLE_NAME DeploymentFilePrefix Service.fidl mypackage/Service) : generates and installs a library containing a backend for the given service. Please note that the first parameter is an incomplete file name. In order to obtain the actual deployment file name, the macro appends either "-dbus.fdepl" of "-someip.fdepl" to the parameter, depending on which backend is used. The backend is chosen the following way:
	* DBus if the following conditions are met:
		* CommonAPI-DBUS package is available
		* the "DEFAULT_COMMONAPI_BACKEND" variable is either not defined or set to the value "dbus". That variable is usually set using "-DDEFAULT_COMMONAPI_BACKEND=dbus" when calling cmake to configure the package.
	* SomeIP if DBus is not used.
* use_commonapi_service(VARIABLE_NAME mypackage/Service) : use the backend library for the given service. That library needs to be generated using one of the macros above first. Note that the backend type does not need to be specified, since no code generation takes place when using that macro.

# Example
Here is an example of CMake file producing a DBUS backend for a service called "MyService" :

```cmake
FIND_PACKAGE(CommonAPIDBusCodeGen REQUIRED)

# Generate and install a library containing DBUS stubs and proxies for our service. The CMake variable called "COMMONAPI_GENERATED_LIBRARIES" will contain the name of the libraries to link against, in order to use the generated CommonAPI backend
add_commonapi_dbus_service(COMMONAPI_GENERATED ${CMAKE_CURRENT_SOURCE_DIR}/MyService-dbus.fdepl ${CMAKE_CURRENT_SOURCE_DIR}/MyService.fidl mypackage/MyService)

# Add an executable
add_executable(my-service service.cpp)

# Link against the generated CommonAPI libraries
target_link_libraries(my-service ${COMMONAPI_GENERATED_LIBRARIES})
```


A complete application example can be found in the "example" folder.
