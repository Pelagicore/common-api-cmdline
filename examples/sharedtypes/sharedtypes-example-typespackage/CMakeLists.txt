
cmake_minimum_required(VERSION 3.0.0)

project(sharedtypes-example-typespackage)

install(FILES fidl/ExampleTypes.fidl DESTINATION include/franca_idls/com.pelagicore/)
