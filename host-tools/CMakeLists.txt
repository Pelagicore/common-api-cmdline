cmake_minimum_required(VERSION 2.6)

project(common-api-cmdline-host-tools)

set(CMAKE_MODULES_DESTINATION_DIR lib/cmake)

option(USE_SHARED_MAVEN_REPOSITORY "Use shared Maven repository (incompatible with Yocto)" OFF)

if(USE_SHARED_MAVEN_REPOSITORY)
	set(ENABLE_MAVEN_REPOSITORY_EXPORT 0)
else()
	set(ENABLE_MAVEN_REPOSITORY_EXPORT 1)
endif()

configure_file(CommonAPICodeGenFrameworkConfig.cmake.in CommonAPICodeGenFrameworkConfig.cmake @ONLY)

include(${CMAKE_CURRENT_BINARY_DIR}/CommonAPICodeGenFrameworkConfig.cmake)
install(FILES ${CMAKE_CURRENT_BINARY_DIR}/CommonAPICodeGenFrameworkConfig.cmake DESTINATION CommonAPICodeGenFramework)

add_maven_package(launcher ${CMAKE_CURRENT_SOURCE_DIR}/org.genivi.commonapi.cmdlinelauncher "")
add_maven_package(cmdline ${CMAKE_CURRENT_SOURCE_DIR}/org.genivi.commonapi.cmdline launcher)

#Install POM defining Eclipse + Franca repositories
add_maven_package(installcodegen ${CMAKE_CURRENT_SOURCE_DIR} "launcher;cmdline")

configure_file(commonAPICodeGen.in commonAPICodeGen @ONLY)
install(FILES ${CMAKE_CURRENT_BINARY_DIR}/commonAPICodeGen DESTINATION bin
	PERMISSIONS OWNER_READ OWNER_WRITE OWNER_EXECUTE GROUP_READ GROUP_EXECUTE WORLD_READ WORLD_EXECUTE
)

export_maven_repository(common-api-cmdline)
