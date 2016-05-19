
#pragma once

#include "v0/com/pelagicore/SharedTypesExampleInterfaceStub.hpp"

namespace generated = v0::com::pelagicore;

namespace example {

class SharedTypesExampleInterfaceStubImpl : public generated::SharedTypesExampleInterfaceStub {
public:
    SharedTypesExampleInterfaceStubImpl();
    ~SharedTypesExampleInterfaceStubImpl() {}

    virtual generated::SharedTypesExampleInterfaceStubRemoteEvent *initStubAdapter(const std::shared_ptr<generated::SharedTypesExampleInterfaceStubAdapter> &_stubAdapter);

    virtual const CommonAPI::Version& getInterfaceVersion(std::shared_ptr<CommonAPI::ClientId> clientId) override;
    virtual void Echo(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoReply_t _reply) override;
    virtual void EchoExampleType(const std::shared_ptr<CommonAPI::ClientId> _client, std::string _message, EchoExampleTypeReply_t _reply) override;
};

} // namespace example
