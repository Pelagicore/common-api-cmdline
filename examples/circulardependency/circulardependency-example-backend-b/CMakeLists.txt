
cmake_minimum_required(VERSION 3.0.0)
project(circulardependency-example-backend-b)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

find_package(PkgConfig REQUIRED)

pkg_check_modules(DBUS dbus-1)

pkg_check_modules(COMMONAPI CommonAPI CommonAPI-DBus)
add_definitions(${COMMONAPI_CFLAGS_OTHER})
include_directories(${COMMONAPI_INCLUDE_DIRS})
link_directories(${COMMON_API_LIBRARY_DIRS})

find_package(CommonAPICodeGen REQUIRED)

set(FIDL_FILES_DIR ${CMAKE_CURRENT_SOURCE_DIR}/fidl)
set(SERVICE_NAME circulardependency-example-service-b)

add_commonapi_service(
    MYINTERFACE_GENERATED
    com.pelagicore/CircularDependencyExampleInterfaceB
    ${FIDL_FILES_DIR}/CircularDependencyExampleInterfaceB
    ${FIDL_FILES_DIR}/CircularDependencyExampleInterfaceB.fidl
    ""
)
