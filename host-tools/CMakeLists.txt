cmake_minimum_required(VERSION 2.6)

project(common-api-cmdline-host-tools)

set(CMAKE_MODULES_DESTINATION_DIR lib/cmake)

#set(FRANCA_VERSION "0.8.9.201308271211")
#set(FRANCA_VERSION "0.8.11.201401091023")

#set(G_OUTPUT_JAR ${CMAKE_CURRENT_SOURCE_DIR}/target/org.genivi.commonapi.cmdline/org.genivi.commonapi.cmdline-1.0.0.jar)
#set(OUTPUT_JAR org.genivi.commonapi.cmdline-1.0.0.jar)

#add_custom_command(
#	OUTPUT ${G_OUTPUT_JAR}
#	COMMAND mvn ${MAVEN_OPTIONS} ${MAVEN_BUILD_DIR_OPTIONS} -f ${CMAKE_CURRENT_SOURCE_DIR}/org.genivi.commonapi.cmdline/pom.xml install
#)

#add_custom_target(
#	${OUTPUT_JAR} ALL
#	COMMAND cp ${G_OUTPUT_JAR} ${OUTPUT_JAR}
#	DEPENDS ${G_OUTPUT_JAR}
#)

set(MAVEN_BUILD_DIR_OPTIONS -DbuildPrefix=${CMAKE_CURRENT_BINARY_DIR})

add_custom_target(
	launcher ALL
	COMMAND mvn ${MAVEN_OPTIONS} ${MAVEN_BUILD_DIR_OPTIONS}/org.genivi.commonapi.cmdlinelauncher -f ${CMAKE_CURRENT_SOURCE_DIR}/org.genivi.commonapi.cmdlinelauncher/pom.xml install
)

add_custom_target(
	cmdline ALL
	COMMAND mvn ${MAVEN_OPTIONS} ${MAVEN_BUILD_DIR_OPTIONS}/org.genivi.commonapi.cmdline -f ${CMAKE_CURRENT_SOURCE_DIR}/org.genivi.commonapi.cmdline/pom.xml install
	DEPENDS launcher
)

# Install POM defining Eclipse + Franca repositories
add_custom_target(
	installcodegen ALL
	COMMAND mvn ${MAVEN_OPTIONS} ${MAVEN_BUILD_DIR_OPTIONS} -f ${CMAKE_CURRENT_SOURCE_DIR}/codegen.pom.xml install
	DEPENDS ${CMAKE_CURRENT_SOURCE_DIR}/codegen.pom.xml launcher cmdline
)

set(JAR_DESTINATION_PATH share/CommonAPICodeGen)

#install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${OUTPUT_JAR} DESTINATION ${JAR_DESTINATION_PATH})

#install(FILES codegen.pom.xml DESTINATION share/CommonAPICodeGen)
#install(FILES codegen.properties DESTINATION share/CommonAPICodeGen)

configure_file(commonAPICodeGen.in commonAPICodeGen @ONLY)
install(FILES ${CMAKE_CURRENT_BINARY_DIR}/commonAPICodeGen DESTINATION bin
	PERMISSIONS OWNER_READ OWNER_WRITE OWNER_EXECUTE GROUP_READ GROUP_EXECUTE WORLD_READ WORLD_EXECUTE
)

#configure_file(codegen.pom.xml.in gen/codegen.pom.xml @ONLY)
#install(FILES ${CMAKE_CURRENT_BINARY_DIR}/gen/codegen.pom.xml DESTINATION ${JAR_DESTINATION_PATH})

# Install dependencies
#install(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/target/dependencies/ DESTINATION ${JAR_DESTINATION_PATH})


#add_subdirectory(cmake)

