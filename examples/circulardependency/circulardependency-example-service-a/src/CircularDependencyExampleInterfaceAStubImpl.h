
#pragma once

#include "v0/com/pelagicore/CircularDependencyExampleInterfaceAStub.hpp"

#include "CircularDependencyExampleInterfaceAStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

class CircularDependencyExampleInterfaceAStubImpl : public generated::CircularDependencyExampleInterfaceAStub {
public:
    CircularDependencyExampleInterfaceAStubImpl();
    ~CircularDependencyExampleInterfaceAStubImpl() {}

    virtual generated::CircularDependencyExampleInterfaceAStubRemoteEvent *initStubAdapter(const std::shared_ptr<generated::CircularDependencyExampleInterfaceAStubAdapter> &_stubAdapter);

    virtual const CommonAPI::Version& getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId) override;
    virtual void Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply) override;
};

} // namespace example
