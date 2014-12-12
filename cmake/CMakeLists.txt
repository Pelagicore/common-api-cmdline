cmake_minimum_required(VERSION 2.6)

project(common-api-cmdline-cmake)

set(CMAKE_MODULES_DESTINATION_DIR lib/cmake)

# Dependencies
FIND_PACKAGE(PkgConfig REQUIRED)

set(JAR_DESTINATION_PATH share/CommonAPICodeGen)

add_subdirectory(common)
