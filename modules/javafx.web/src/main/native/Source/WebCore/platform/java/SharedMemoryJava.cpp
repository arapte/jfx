#include "config.h"
#include "SharedMemory.h"

#include <wtf/text/CString.h>
#include <wtf/text/MakeString.h>
#include <wtf/text/WTFString.h>

namespace WebCore {


RefPtr<SharedMemory> SharedMemory::allocate(size_t size)
{
    WTFLogAlways("Dummy implementation %s %s %d\n", __FILE__, __FUNCTION__, __LINE__);
    return nullptr;
}

SharedMemory::~SharedMemory()
{

}

RefPtr<SharedMemory> SharedMemory::map(Handle&& handle, Protection protection, CopyOnWrite copyOnWrite)
{
    WTFLogAlways("Dummy implementation %s %s %d\n", __FILE__, __FUNCTION__, __LINE__);
    return nullptr;
}

auto SharedMemory::createHandle(Protection) -> std::optional<Handle>
{
    WTFLogAlways("Dummy implementation %s %s %d\n", __FILE__, __FUNCTION__, __LINE__);
    return {};
}

} //namespace WebCore