
#define LOG_TAG "Temperature"

#include <android-base/logging.h>
#include <android/binder_manager.h>
#include <android/binder_process.h>
#include <binder/ProcessState.h>
#include <binder/IServiceManager.h>
#include "includes/Temperature.h"

using aidl::vendor::duy::hvac::Temperature;
using std::string_literals::operator""s;

void logd(std::string msg) {
    std::cout << msg << std::endl;
    ALOGD("%s", msg.c_str());
}

void loge(std::string msg) {
    std::cout << msg << std::endl;
    ALOGE("%s", msg.c_str());
}

int main() {
// Enable vndbinder to allow vendor-to-venfor binder call
    android::ProcessState::initWithDriver("/dev/vndbinder");

    ABinderProcess_setThreadPoolMaxThreadCount(0);
    ABinderProcess_startThreadPool();

    std::shared_ptr<Temperature> temp = ndk::SharedRefBase::make<Temperature>();
    const std::string name = Temperature::descriptor + "/default"s;

    if (temp != nullptr) {
        if(AServiceManager_addService(temp->asBinder().get(), name.c_str()) != STATUS_OK) {
            loge("Failed to register ITemperature service");
            return -1;
        }
    } else {
        loge("Failed to get ITemperature instance");
        return -1;
    }

    logd("ITemperature service starts to join service pool");
    ABinderProcess_joinThreadPool();

    return EXIT_FAILURE;  // should not reached
}