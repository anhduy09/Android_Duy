
#include "DuyService.hpp"
#include <utils/Log.h>

#ifdef LOG_TAG
#undef LOG_TAG
#endif
#define LOG_TAG "duy_service"

namespace duy {
namespace service {

::std::shared_ptr<DuyService> DuyService::S_INSTANCE = NULL;

::std::shared_ptr<DuyService> DuyService::getInstance()
{
   if (S_INSTANCE == NULL)
   {
      S_INSTANCE = ndk::SharedRefBase::make<DuyService>();
   }
   return S_INSTANCE;
}

DuyService::DuyService()
{

}

DuyService::~DuyService()
{

}

::ndk::ScopedAStatus DuyService::getDuyValues(dsapinamespace::DuyType* _aidl_return)
{
   ::aidl::duy::service::api::DuyType extype;
   *_aidl_return = extype;
   return ndk::ScopedAStatus::ok();
}


}
}

