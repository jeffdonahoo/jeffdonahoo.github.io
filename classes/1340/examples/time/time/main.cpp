#include <iostream>
#include "time.h"

using namespace std;

void main()
{
	TimeType time1, time2;

	time1.Set(11, 45, 36);
	time2.Set(11, 46, 32);

	cout << "Time 1 is ";
	time1.Write();

	cout << "Time 2 is ";
	time2.Write();

	if (time1.Equal(time2))
	{
		cout << "Time 1 equals time 2\n";
	}
	else if (time1.LessThan(time2))
	{
		cout << "Time 1 less than time 2\n";
	}
	else
	{
		cout << "Time 2 less than time 1\n";
	}

}