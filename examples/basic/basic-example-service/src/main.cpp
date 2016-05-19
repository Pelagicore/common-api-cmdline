
#include <iostream>
#include <thread>

#include <CommonAPI/CommonAPI.hpp>

#include "BasicExampleInterfaceStubImpl.h"


int main(int argc, char **argv)
{
    auto commonAPIRuntime = CommonAPI::Runtime::get();

    std::string domain = "local";
    std::string instance = "com.pelagicore.BasicExampleInterface";
    std::string connection = "basic-example-service";

    std::shared_ptr<example::BasicExampleInterfaceStubImpl> myService = std::make_shared<example::BasicExampleInterfaceStubImpl>();
    commonAPIRuntime->registerService(domain, instance, myService, connection);

    while (true) {
        std::cout << "Waiting for calls... (Abort with CTRL+C)" << std::endl;
        std::this_thread::sleep_for(std::chrono::seconds(60));
    }

    return 0;
}

