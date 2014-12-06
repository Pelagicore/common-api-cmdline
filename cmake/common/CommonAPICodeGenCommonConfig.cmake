
set(COMMONAPI_CODEGEN_COMMAND_LINE commonAPICodeGen)
set(COMMONAPI_GENERATED_FILES_LOCATION FrancaGen)

find_package(PkgConfig REQUIRED)

pkg_check_modules(COMMON_API REQUIRED CommonAPI)
add_definitions(${COMMON_API_CFLAGS})
link_directories(${COMMON_API_LIBRARY_DIRS})

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(FRANCA_IDLS_LOCATION ${CMAKE_INSTALL_PREFIX}/include/franca_idls)
set(SERVICE_HEADERS_INSTALLATION_DESTINATION include/CommonAPIServices)
set(SERVICE_HEADERS_INSTALLED_LOCATION ${CMAKE_INSTALL_PREFIX}/${SERVICE_HEADERS_INSTALLATION_DESTINATION})


macro(get_library_name variableName interface)
	set(LIBRARY_NAME ${interface}_CommonAPI)
	STRING(REGEX REPLACE "/" "_" LIBRARY_NAME ${LIBRARY_NAME})
	set ( ${variableName} ${LIBRARY_NAME})
	
	message ("Library name : ${${variableName}} ")
endmacro()

macro(get_generated_files_list VARIABLE_NAME deploymentFile codegenerators)

    execute_process(
        COMMAND ${COMMONAPI_CODEGEN_COMMAND_LINE} -l -f ${deploymentFile} -o ${CMAKE_CURRENT_BINARY_DIR}/${COMMONAPI_GENERATED_FILES_LOCATION} ${codegenerators}
        OUTPUT_VARIABLE ${VARIABLE_NAME} OUTPUT_STRIP_TRAILING_WHITESPACE
    )

    message(STATUS "List of generated files : ${${VARIABLE_NAME}}")

endmacro()


macro(add_generated_files_command GENERATED_FILES deploymentFile idlFile codegenerators)
	message("Command : ${COMMONAPI_CODEGEN_COMMAND_LINE} -f ${deploymentFile} -o ${CMAKE_CURRENT_BINARY_DIR}/${COMMONAPI_GENERATED_FILES_LOCATION} ${codegenerators}")
	add_custom_command(
		OUTPUT ${GENERATED_FILES}
		COMMAND ${COMMONAPI_CODEGEN_COMMAND_LINE} -f ${deploymentFile} -o ${CMAKE_CURRENT_BINARY_DIR}/${COMMONAPI_GENERATED_FILES_LOCATION} ${codegenerators}
		DEPENDS ${deploymentFile} ${idlFile}
	)
	include_directories(${CMAKE_CURRENT_BINARY_DIR}/${COMMONAPI_GENERATED_FILES_LOCATION})
endmacro()


macro(install_franca_idl interfaceName deploymentFile deploymentFileDestinationName idlFile)
	install(FILES ${idlFile} DESTINATION ${FRANCA_IDLS_LOCATION}/${interfaceName}/.. )
	message("configure file ${deploymentFile} ${CMAKE_CURRENT_BINARY_DIR}/${deploymentFileDestinationName}")
	configure_file(${deploymentFile} ${CMAKE_CURRENT_BINARY_DIR}/${deploymentFileDestinationName} @ONLY)
	install(FILES ${CMAKE_CURRENT_BINARY_DIR}/${deploymentFileDestinationName} DESTINATION ${FRANCA_IDLS_LOCATION}/${interfaceName}/.. )
endmacro()


# Use a previously generated CommonAPI proxy/stub library
macro(use_commonapi_service variableName interface)

	get_library_name(LIBRARY_NAME ${interface})
	set(PKGCONFIG_FILENAME ${LIBRARY_NAME})

    pkg_check_modules(${PKGCONFIG_FILENAME}_PKG REQUIRED ${PKGCONFIG_FILENAME})
    add_definitions(${${PKGCONFIG_FILENAME}_PKG_CFLAGS})
    link_directories(${${PKGCONFIG_FILENAME}_PKG_LIBRARY_DIRS})

    # See below why "--no-as-needed" is needed here
	set(${variableName}_LIBRARIES -Wl,--no-as-needed ${${PKGCONFIG_FILENAME}_PKG_LIBRARIES} -Wl,--as-needed)
    set(${variableName}_PKGCONFIG_FILENAME ${PKGCONFIG_FILENAME})

    message("CommonAPI libraries for ${interface} : ${${variableName}_LIBRARIES}")

endmacro()


# Warning : the pkg_check_modules macro seems to remove the libraries specified between "--no-as-needed" and "--as-needed" in pkg-config files, so we need to add the library twice. Once with "--no-as-needed" and once without
# Generates and installs a pkg-config file
macro(add_commonapi_pkgconfig interface)

    if(INSTALL_PKGCONFIG_UNINSTALLED_FILE)
        set(DEVELOPMENT_INCLUDE_PATH " -I${CMAKE_CURRENT_BINARY_DIR}/${COMMONAPI_GENERATED_FILES_LOCATION} #")
        set(DEVELOPMENT_LIBRARY_PATH " -L${CMAKE_CURRENT_BINARY_DIR} #" )
    else()
        set(DEVELOPMENT_INCLUDE_PATH "")
        set(DEVELOPMENT_LIBRARY_PATH "")
    endif()

	get_library_name(LIBRARY_NAME ${interface})
	set(PKGCONFIG_FILENAME ${LIBRARY_NAME}.pc)
    file(WRITE ${PROJECT_BINARY_DIR}/${PKGCONFIG_FILENAME} "prefix=@CMAKE_INSTALL_PREFIX@
exec_prefix=\${prefix}
libdir=\${prefix}/lib
includedir=\${prefix}/include

Name: ${interface} Common-API Service
Description: ${interface} Common-API Service
Version: 
Requires: CommonAPI
Libs: -Wl,--no-as-needed,-l${LIBRARY_NAME}_Backend,--as-needed -l${LIBRARY_NAME}_Backend @DEVELOPMENT_LIBRARY_PATH@ 
Cflags: @DEVELOPMENT_INCLUDE_PATH@ -I\${includedir}/CommonAPIServices
")

    install(FILES ${PROJECT_BINARY_DIR}/${PKGCONFIG_FILENAME} DESTINATION ${CMAKE_INSTALL_PREFIX}/lib/pkgconfig)

endmacro()
