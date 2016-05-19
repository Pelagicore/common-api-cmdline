
#include <iostream>
#include <thread>
#include <unistd.h>

#include <CommonAPI/CommonAPI.hpp>

#include <v0/com/pelagicore/CircularDependencyExampleInterfaceAProxy.hpp>
#include "CircularDependencyExampleInterfaceBStubImpl.h"

namespace generated = v0::com::pelagicore;

int main(int argc, char **argv)
{
    auto commonAPIRuntime = CommonAPI::Runtime::get();

    std::string domain = "local";
    std::string instance_service = "com.pelagicore.CircularDependencyExampleInterfaceB";

    std::shared_ptr<example::CircularDependencyExampleInterfaceBStubImpl> myService = std::make_shared<example::CircularDependencyExampleInterfaceBStubImpl>();
    commonAPIRuntime->registerService(domain, instance_service, myService);

    std::string instance_client = "com.pelagicore.CircularDependencyExampleInterfaceA";
    std::shared_ptr<generated::CircularDependencyExampleInterfaceAProxyDefault> myProxy = commonAPIRuntime->buildProxy<generated::CircularDependencyExampleInterfaceAProxy>(domain, instance_client);

    while(!myProxy->isAvailable()) {
        usleep(10);
    }

    const std::string message = "This is the client message";
    CommonAPI::CallStatus callStatus;
    std::string returnMessage;

    std::cout << "Waiting for calls... (Abort with CTRL+C)" << std::endl;
    while(true) {
        myProxy->Echo(message, callStatus, returnMessage);

        if(callStatus != CommonAPI::CallStatus::SUCCESS) {
            std::cout << "Remote call failed" << std::endl;
            return -1;
        }

        std::cout << "Remote call succeeded: " << returnMessage << std::endl;
        std::this_thread::sleep_for(std::chrono::seconds(1));
    }

    return 0;
}
