
#include <iostream>

#include "v0/com/pelagicore/CircularDependencyExampleInterfaceBStub.hpp"

#include "CircularDependencyExampleInterfaceBStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

CircularDependencyExampleInterfaceBStubImpl::CircularDependencyExampleInterfaceBStubImpl() 
{
}

const CommonAPI::Version& CircularDependencyExampleInterfaceBStubImpl::getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId)
{
}

generated::CircularDependencyExampleInterfaceBStubRemoteEvent* CircularDependencyExampleInterfaceBStubImpl::initStubAdapter(const std::shared_ptr<generated::CircularDependencyExampleInterfaceBStubAdapter> &stub_adapter)
{
    CommonAPI::Stub<generated::CircularDependencyExampleInterfaceBStubAdapter, generated::CircularDependencyExampleInterfaceBStubRemoteEvent>::stubAdapter_ = stub_adapter;
    return nullptr;
}

void CircularDependencyExampleInterfaceBStubImpl::Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply)
{
    std::cout << _message << std::endl;
    _reply("\"" + _message + "\"" + " said the client!");
}

} // namespace example
