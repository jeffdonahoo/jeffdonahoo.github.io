#include <iostream>
#include "time.h"

using namespace std;

void main()
{
	TimeType time1(1, 0, 0), time2(2, 0, 0);

	TimeType time3(time1.TopTime(time2));

	cout << time3 << endl;
}