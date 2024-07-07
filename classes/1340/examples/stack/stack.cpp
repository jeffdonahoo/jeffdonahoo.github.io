#include "stack.h"

StackType::StackType()
{
	top = -1;
}

bool StackType::push(const UrlType& url)
{
	if (top == (MAXSTACK - 1))  // Check if stack is full
	{
		return false;
	}

	top++;
	item[top] = url;

	return true;
}

bool StackType::pop(UrlType& url)
{
	if (top == -1)   // Check if stack is empty
	{
		return false;
	}

	url = item[top];

	top--;

	return true;
}