
#pragma once

#include "v0/com/pelagicore/CircularDependencyExampleInterfaceBStub.hpp"

#include "CircularDependencyExampleInterfaceBStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

class CircularDependencyExampleInterfaceBStubImpl : public generated::CircularDependencyExampleInterfaceBStub {
public:
    CircularDependencyExampleInterfaceBStubImpl();
    ~CircularDependencyExampleInterfaceBStubImpl() {}

    virtual generated::CircularDependencyExampleInterfaceBStubRemoteEvent *initStubAdapter(const std::shared_ptr<generated::CircularDependencyExampleInterfaceBStubAdapter> &_stubAdapter);

    virtual const CommonAPI::Version& getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId) override;
    virtual void Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply) override;
};

} // namespace example
