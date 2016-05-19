
#include <iostream>

#include "v0/com/pelagicore/BasicExampleInterfaceStub.hpp"

#include "BasicExampleInterfaceStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

BasicExampleInterfaceStubImpl::BasicExampleInterfaceStubImpl() 
{
}

const CommonAPI::Version& BasicExampleInterfaceStubImpl::getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId)
{
}

generated::BasicExampleInterfaceStubRemoteEvent* BasicExampleInterfaceStubImpl::initStubAdapter(const std::shared_ptr<generated::BasicExampleInterfaceStubAdapter> &stub_adapter)
{
    CommonAPI::Stub<generated::BasicExampleInterfaceStubAdapter, generated::BasicExampleInterfaceStubRemoteEvent>::stubAdapter_ = stub_adapter;
    return nullptr;
}

void BasicExampleInterfaceStubImpl::Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply)
{
    std::cout << _message << std::endl;
    _reply("\"" + _message + "\"" + " said the client!");
}

void BasicExampleInterfaceStubImpl::EchoExampleType(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoExampleTypeReply_t _reply)
{
    std::cout << _message << std::endl;
    const generated::ExampleTypes::FooBar value = generated::ExampleTypes::FooBar::Bar;
    _reply(value);
}

void BasicExampleInterfaceStubImpl::EchoExampleType2(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoExampleType2Reply_t _reply)
{
    std::cout << _message << std::endl;
    const generated::ExampleTypes2::Baz value = generated::ExampleTypes2::Baz::Value1;
    _reply(value);
}

} // namespace example
