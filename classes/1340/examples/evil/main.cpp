#include <iostream>
#include "number.h"

using namespace std;

void main()
{
	Number x(3), z; // Declaration of any constructor means the compiler will not
	                // automatically create a default constructor for you.

	Number y(x);
}