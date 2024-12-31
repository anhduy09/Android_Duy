 
#ifndef DUY_SERVICE
#define DUY_SERVICE

#include <aidl/duy/service/api/BnDuyService.h>

namespace dsapinamespace = ::aidl::duy::service::api;

namespace duy {
namespace service {

class DuyService
   : public ::aidl::duy::service::api::BnDuyService
{
public:
   DuyService();
   ~DuyService();

   static ::std::shared_ptr<DuyService> getInstance();

   // IDuyService AIDL interface callbacks
   ::ndk::ScopedAStatus getDuyValues(dsapinamespace::DuyType* _aidl_return) final;


    const std::string getServiceName(void)
   {
      return std::string() + descriptor + "/default";
   }

private:
   static ::std::shared_ptr<DuyService> S_INSTANCE;

};

}
}


#endif  // DUY_SERVICE
