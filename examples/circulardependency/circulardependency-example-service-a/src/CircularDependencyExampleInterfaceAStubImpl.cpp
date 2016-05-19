
#include <iostream>

#include "v0/com/pelagicore/CircularDependencyExampleInterfaceAStub.hpp"

#include "CircularDependencyExampleInterfaceAStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

CircularDependencyExampleInterfaceAStubImpl::CircularDependencyExampleInterfaceAStubImpl() 
{
}

const CommonAPI::Version& CircularDependencyExampleInterfaceAStubImpl::getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId)
{
}

generated::CircularDependencyExampleInterfaceAStubRemoteEvent* CircularDependencyExampleInterfaceAStubImpl::initStubAdapter(const std::shared_ptr<generated::CircularDependencyExampleInterfaceAStubAdapter> &stub_adapter)
{
    CommonAPI::Stub<generated::CircularDependencyExampleInterfaceAStubAdapter, generated::CircularDependencyExampleInterfaceAStubRemoteEvent>::stubAdapter_ = stub_adapter;
    return nullptr;
}

void CircularDependencyExampleInterfaceAStubImpl::Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply)
{
    std::cout << _message << std::endl;
    _reply("\"" + _message + "\"" + " said the client!");
}

} // namespace example
