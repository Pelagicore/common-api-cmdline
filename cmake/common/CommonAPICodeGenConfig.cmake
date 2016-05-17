
# Copyright (C) 2016 Pelagicore AB
#
# Permission to use, copy, modify, and/or distribute this software for
# any purpose with or without fee is hereby granted, provided that the
# above copyright notice and this permission notice appear in all copies.
#
# THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
# WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR
# BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES
# OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
# WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION,
# ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS
# SOFTWARE.
#
# For further information see LICENSE.txt
#

find_package(CommonAPICodeGenCommon REQUIRED)
find_package(PkgConfig)

# Macro: add_commonapi_service
#
# Generates and installs a library containing a CommonAPI stub and a proxy for the given interface.
# By default, the D-Bus backend is used unless the "DEFAULT_COMMONAPI_BACKEND" variable is set to "someip"
#
# A variable is created with the name <variableName>_LIBRARIES which contains the generated library typically
# used in e.g. target_link_libraries(). For example if 'foo' is passed as the variableName argument then
# a variable 'foo_LIBRARIES' is created and populated with the generated library and relevant linker flags.
#
# Parameters:
#
# variableName           A string which is used to create the <variableName>_LIBRARIES variable which contains the
#                        generated library, i.e. typically used in target_link_libraries()
#
# interface              A string to identify the interface.
#                        This string should be used by a client that calls 'use_commonapi_service()'
#
# deploymentFilePrefix   A string with the path and name of the deployment file up to the -<backend>.fdepl part.
#                        For example, given a deployment file '/tmp/MyInterface-dbus.fdepl' the sring passed here
#                        should be '/tmp/MyInterface'
#
# idlFiles               A string with the full path to the IDL file, e.g. '/tmp/MyInterface.fidl'.
#                        A semicolon separated string list within double quotes can also be passed. All fidl
#                        files passed will be accessible and included in the build and also added to the install target.
#
# fidl_include_paths     A path or a list of paths to interface files that should be included. Multiple values are
#                        passed as a semicolon separated list in double quotes, e.g.: "${path_one};${path_two}"
#                        If no include paths are needed, an empty string should be passed as argument.
#
macro(add_commonapi_service variableName interface deploymentFilePrefix idlFiles fidl_include_paths)

    get_library_name(BASE___ ${interface})

    set(BACKEND dbus)

    if(${DEFAULT_COMMONAPI_BACKEND} MATCHES "someip")
        set(BACKEND someip)
    endif()

    message("Using ${BACKEND} backend")

    set(deploymentFile ${deploymentFilePrefix}-${BACKEND}.fdepl)

    # Set --no-as-needed for this generated library so the linker does not remove linkage to what
    # it percieves as an unused backend library.
    set(${variableName}_LIBRARIES -Wl,--no-as-needed ${${variableName}_LIBRARIES} ${BASE___}_${BACKEND} -Wl,--as-needed)

    message("variableName_LIBRARIES: ${${variableName}_LIBRARIES}")
    message("{BASE___}_{BACKEND}: ${BASE___}_${BACKEND}")

    if(${BACKEND} MATCHES "someip")
        find_package(CommonAPISomeIPCodeGen REQUIRED)
        install_commonapi_someip_backend(${BASE___} ${variableName} ${deploymentFile} ${idlFiles} ${interface})
    else()
        find_package(CommonAPIDBusCodeGen REQUIRED)
        install_commonapi_dbus_backend(${BASE___} ${variableName} ${deploymentFile} "${idlFiles}" ${interface} "${fidl_include_paths}")
    endif()

    install_franca_idl(${interface} ${deploymentFile} ${deploymentFilePrefix}.fdepl "${idlFiles}")

endmacro()


# Macro: use_commonapi_service_stub
#
# Use an installed CommonAPI service stub.
#
# Parameters:
#
# variableName  A string which is used to create the <variableName>_LIBRARIES variable which contains the
#               generated library, i.e. typically used in target_link_libraries()
#
# interface     A string with the same value which was passed when calling 'add_commonapi_service'.
#
macro(use_commonapi_service_stub variableName interface)
    use_commonapi_service(${variableName} ${interface})
endmacro(use_commonapi_service_stub)


# Macro: use_commonapi_service_proxy
#
# Use an installed CommonAPI service proxy.
#
# Parameters:
#
# variableName  A string which is used to create the <variableName>_LIBRARIES variable which contains the
#               generated library, i.e. typically used in target_link_libraries()
#
# interface     A string with the same value which was passed when calling 'add_commonapi_service'.
#
macro(use_commonapi_service_proxy variableName interface)
    use_commonapi_service(${variableName} ${interface})
endmacro(use_commonapi_service_proxy)
