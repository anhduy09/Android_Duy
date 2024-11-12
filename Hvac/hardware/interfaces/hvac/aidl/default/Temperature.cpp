

#define LOG_TAG "Temperature"
#include "includes/Temperature.h"



namespace aidl {
namespace vendor {
namespace duy {
namespace hvac {

::ndk::ScopedAStatus Temperature::getTemperature(int32_t* _aidl_return) {
	*_aidl_return = mTemperature;
	return ndk::ScopedAStatus::ok();
}


::ndk::ScopedAStatus Temperature::setTemperature(int32_t in_temp) {
	mTemperature = in_temp;
	return ndk::ScopedAStatus::ok();
}

}
}
}
}