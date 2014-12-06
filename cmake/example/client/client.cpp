#include <memory>
#include "climate/ClimateProxy.h"

int main(int argc, const char** argv) {
	auto climateProxy = std::make_shared<climate::ClimateProxy>();
	assert(climateProxy.get() != nullptr);
	return 0;
}
