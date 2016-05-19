
#include <iostream>
#include <thread>

#include <CommonAPI/CommonAPI.hpp>

#include "SharedTypesExampleInterfaceStubImpl.h"


int main(int argc, char **argv)
{
    auto commonAPIRuntime = CommonAPI::Runtime::get();

    std::string domain = "local";
    std::string instance = "com.pelagicore.SharedTypesExampleInterface";
    std::string connection = "sharedtypes-example-service";

    std::shared_ptr<example::SharedTypesExampleInterfaceStubImpl> myService = std::make_shared<example::SharedTypesExampleInterfaceStubImpl>();
    commonAPIRuntime->registerService(domain, instance, myService, connection);

    while (true) {
        std::cout << "Waiting for calls... (Abort with CTRL+C)" << std::endl;
        std::this_thread::sleep_for(std::chrono::seconds(60));
    }

    return 0;
}
