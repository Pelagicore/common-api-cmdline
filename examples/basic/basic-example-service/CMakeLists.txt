
cmake_minimum_required(VERSION 3.0.0)
project(basic-example-service)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

find_package(PkgConfig REQUIRED)

pkg_check_modules(DBUS dbus-1)

pkg_check_modules(COMMONAPI CommonAPI CommonAPI-DBus)
add_definitions(${COMMONAPI_CFLAGS_OTHER})
include_directories(${COMMONAPI_INCLUDE_DIRS})
link_directories(${COMMON_API_LIBRARY_DIRS})

find_package(CommonAPICodeGen REQUIRED)

set(FIDL_FILES_DIR ${CMAKE_CURRENT_SOURCE_DIR}/fidl)
set(TESTDATA_LOCATION ${PROJECT_SOURCE_DIR}/../test-data/)
set(SERVICE_NAME basic-example-service)

#
#
#
add_commonapi_service(
    MYINTERFACE_GENERATED
    com.pelagicore/BasicExampleInterface
    ${FIDL_FILES_DIR}/BasicExampleInterface
    ${FIDL_FILES_DIR}/BasicExampleInterface.fidl
    "${TESTDATA_LOCATION}/foopath/;${TESTDATA_LOCATION}/barpath/"
)

include_directories(
    src/
)

add_executable(${SERVICE_NAME}
    src/main.cpp
    src/BasicExampleInterfaceStubImpl.cpp
)

target_link_libraries(${SERVICE_NAME}
    ${COMMONAPI_LIBRARIES}
    ${MYINTERFACE_GENERATED_LIBRARIES}
)
