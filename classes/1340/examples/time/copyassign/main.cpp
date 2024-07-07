#include <iostream>
#include "time.h"

using namespace std;

void main()
{
	TimeType time1(12, 59, 59), time2(time1);

	time1.Write("Time 1 is ");

	time2.Write("Time 2 is ");

	time1 = time1 + time2;

	time1.Write("Time 1 is ");

	if (time1 == time2)
	{
		cout << "Time 1 equals time 2\n";
	}
	else if (time1 < time2)
	{
		cout << "Time 1 less than time 2\n";
	}
	else
	{
		cout << "Time 2 less than time 1\n";
	}

}