
#pragma once

#include <aidl/vendor/duy/hvac/BnTemperature.h>

namespace aidl {
namespace vendor {
namespace duy {
namespace hvac {
class Temperature : public BnTemperature {

	private:
		int mTemperature = 26;
	public:
		::ndk::ScopedAStatus getTemperature(int32_t* _aidl_return);
 		::ndk::ScopedAStatus setTemperature(int32_t in_temp);
};

}
}
}
}