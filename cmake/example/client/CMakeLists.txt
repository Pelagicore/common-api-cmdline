cmake_minimum_required(VERSION 2.6)

PROJECT(test-commonapi-cmake)

# Dependencies
FIND_PACKAGE(CommonAPICodeGen REQUIRED)

# Use the common API service for the climate service
use_commonapi_service(COMMONAPI_GENERATED climate/Climate)

add_executable(client client.cpp)
install(TARGETS client DESTINATION bin)
target_link_libraries(client ${COMMONAPI_GENERATED_LIBRARIES})
