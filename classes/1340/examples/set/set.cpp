#include <iostream>
#include "set.h"

using namespace std;

SetType::SetType()
{
	elements = new int[DEFNOELMTS];
	maxElements = DEFNOELMTS;
	noElements = 0;
}

SetType::SetType(int setSize)
{
	elements = new int[setSize];
	maxElements = setSize;
	noElements = 0;
}

SetType::~SetType()
{
	delete [] elements;
}

bool SetType::Insert(ElmtType newElmt)
{
	if ((ElementOf(newElmt)) || (IsFull()))
	{
		return false;  // Already there or full
	}
	else
	{
		elements[noElements++] = newElmt;
	}

	return true;
}

bool SetType::Delete(ElmtType newElmt)
{
	int i;

	if (!ElementOf(newElmt))
	{
		return false;
	}

	for (i=0; elements[i] != newElmt; i++);

    elements[i] = elements[noElements-1];  // Copy last one  (works even deleting last)

	noElements--;

	return true;
}

bool SetType::ElementOf(ElmtType newElmt)
{
	int i = 0;

	while (i < noElements)
	{
		if (elements[i] == newElmt)
		{
			return true;
		}
		i++;
	}
	return false;
}

bool SetType::IsFull()
{
	return (noElements >= maxElements);
}

void SetType::Print()
{
	for (int j = 0; j < noElements; j++)
	{
		cout << elements[j] << " ";
	}
	cout << endl;
}
