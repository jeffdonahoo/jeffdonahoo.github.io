#include <iostream>
#include "set.h"

using namespace std;

void main()
{
    SetType s;

	if (!s.Delete(5))
	{
		cout << "Bad Delete\n";
	}

	if (!s.Insert(6))
	{
		cout << "Bad Insert\n";
	}

	if (!s.Insert(5))
	{
		cout << "Error\n";
	}

	if (!s.Delete(5))
	{
		cout << "Error\n";
	}


	if (s.ElementOf(5))
	{
		cout << "Error\n";
	}
}
