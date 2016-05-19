
#include <iostream>

#include "v0/com/pelagicore/SharedTypesExampleInterfaceStub.hpp"

#include "SharedTypesExampleInterfaceStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

SharedTypesExampleInterfaceStubImpl::SharedTypesExampleInterfaceStubImpl() 
{
}

const CommonAPI::Version& SharedTypesExampleInterfaceStubImpl::getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId)
{
}

generated::SharedTypesExampleInterfaceStubRemoteEvent* SharedTypesExampleInterfaceStubImpl::initStubAdapter(const std::shared_ptr<generated::SharedTypesExampleInterfaceStubAdapter> &stub_adapter)
{
    CommonAPI::Stub<generated::SharedTypesExampleInterfaceStubAdapter, generated::SharedTypesExampleInterfaceStubRemoteEvent>::stubAdapter_ = stub_adapter;
    return nullptr;
}

void SharedTypesExampleInterfaceStubImpl::Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply)
{
    std::cout << _message << std::endl;
    _reply("\"" + _message + "\"" + " said the client!");
}

void SharedTypesExampleInterfaceStubImpl::EchoExampleType(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoExampleTypeReply_t _reply)
{
    std::cout << _message << std::endl;
    const generated::ExampleTypes::TheTypes value = generated::ExampleTypes::TheTypes::Value1;
    _reply(value);
}

} // namespace example
