
cmake_minimum_required(VERSION 3.0.0)
project(basic-example-client)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

find_package(PkgConfig REQUIRED)

pkg_check_modules(DBUS dbus-1)

pkg_check_modules(COMMONAPI CommonAPI CommonAPI-DBus)
add_definitions(${COMMONAPI_CFLAGS_OTHER})
include_directories(${COMMONAPI_INCLUDE_DIRS})
link_directories(${COMMON_API_LIBRARY_DIRS})

find_package(CommonAPICodeGen REQUIRED)

set(CLIENT_NAME basic-example-client)

use_commonapi_service_proxy(COMMONAPI_GENERATED com.pelagicore/BasicExampleInterface)

include_directories(
    src/
)

add_executable(${CLIENT_NAME}
    src/main.cpp
)

target_link_libraries(${CLIENT_NAME}
    ${COMMONAPI_LIBRARIES}
    ${COMMONAPI_GENERATED_LIBRARIES}
)
