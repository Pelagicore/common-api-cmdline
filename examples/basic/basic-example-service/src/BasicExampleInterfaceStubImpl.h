
#pragma once

#include "v0/com/pelagicore/BasicExampleInterfaceStub.hpp"

#include "BasicExampleInterfaceStubImpl.h"

namespace generated = v0::com::pelagicore;

namespace example {

class BasicExampleInterfaceStubImpl : public generated::BasicExampleInterfaceStub {
public:
    BasicExampleInterfaceStubImpl();
    ~BasicExampleInterfaceStubImpl() {}

    virtual generated::BasicExampleInterfaceStubRemoteEvent *initStubAdapter(const std::shared_ptr<generated::BasicExampleInterfaceStubAdapter> &_stubAdapter);

    virtual const CommonAPI::Version& getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId) override;
    virtual void Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply) override;
    virtual void EchoExampleType(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoExampleTypeReply_t _reply) override;
    virtual void EchoExampleType2(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoExampleType2Reply_t _reply) override;
};

} // namespace example