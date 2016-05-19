
cmake_minimum_required(VERSION 3.0.0)
project(sharedtypes-example-service)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

find_package(PkgConfig REQUIRED)

pkg_check_modules(DBUS dbus-1)

pkg_check_modules(COMMONAPI CommonAPI CommonAPI-DBus)
add_definitions(${COMMONAPI_CFLAGS_OTHER})
include_directories(${COMMONAPI_INCLUDE_DIRS})
link_directories(${COMMON_API_LIBRARY_DIRS})

find_package(CommonAPICodeGen REQUIRED)

set(FIDL_FILES_DIR ${CMAKE_CURRENT_SOURCE_DIR}/fidl)
set(SERVICE_NAME sharedtypes-example-service)

# The type-collection used in SharedTypesExampleInterface is installed by
# another package into 'com.pelagicore', which is passed as the fifth argument.
# The path can either be absolute or relative. If the path is relative
# CMAKE_INSTALL_PREFIX will be used together with internally (to this cmake API)
# known paths to construct the complete prefix. For relative paths in a
# Yocto build context, the variable 'COMMONAPI_INTEGRATION_SYSROOT'
# needs to be set as well, typically by the recipe for this component.
#
add_commonapi_service(
    MYINTERFACE_GENERATED
    com.pelagicore/SharedTypesExampleInterface
    ${FIDL_FILES_DIR}/SharedTypesExampleInterface
    ${FIDL_FILES_DIR}/SharedTypesExampleInterface.fidl
    "com.pelagicore/"
)

include_directories(
    src/
)

add_executable(${SERVICE_NAME}
    src/main.cpp
    src/SharedTypesExampleInterfaceStubImpl.cpp
)

target_link_libraries(${SERVICE_NAME}
    ${COMMONAPI_LIBRARIES}
    ${MYINTERFACE_GENERATED_LIBRARIES}
)
