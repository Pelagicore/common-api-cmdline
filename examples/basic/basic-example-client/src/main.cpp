
#include <iostream>
#include <thread>
#include <unistd.h>

#include <CommonAPI/CommonAPI.hpp>

#include <v0/com/pelagicore/BasicExampleInterfaceProxy.hpp>

namespace generated = v0::com::pelagicore;

int main(int argc, char **argv)
{
    auto commonAPIRuntime = CommonAPI::Runtime::get();

    std::string domain = "local";
    std::string instance = "com.pelagicore.BasicExampleInterface";
    std::string connection = "basic-example-client";

    std::shared_ptr<generated::BasicExampleInterfaceProxyDefault> myProxy = commonAPIRuntime->buildProxy<generated::BasicExampleInterfaceProxy>(domain, instance, connection);

    while(!myProxy->isAvailable()) {
        usleep(10);
    }

    const std::string message = "This is the client message";
    CommonAPI::CallStatus callStatus;
    std::string returnMessage;

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
