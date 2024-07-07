#include <iostream>
#include "time.h"

using namespace std;

void main()
{
	TimeType time1(1, 0, 0);

	time1.Write("Time 1 is ");

	time1 = time1 + 5;

	time1.Write("Now Time 1 is ");

	time1 = 5 + time1;

	cout << "Final Time 1 is " << time1 << endl;

}