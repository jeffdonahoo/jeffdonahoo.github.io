#include <string>

using namespace std;

typedef string UrlType;
class StackType
{
public:
	StackType();
	bool push(const UrlType& url);
	bool pop(UrlType& url);

private:
	enum {MAXSTACK = 100};
	int top;
	UrlType item[MAXSTACK];
};