template <class Type>
class StackType
{
public:
	StackType();
	bool push(const Type& newitem);
	bool pop(Type& newitem);

private:
	enum {MAXSTACK = 100};
	int top;
	Type item[MAXSTACK];
};

template <class Type>
StackType<Type>::StackType()
{
	top = -1;
}

template <class Type>
bool StackType<Type>::push(const Type& newitem)
{
	if (top == (MAXSTACK - 1))  // Check if stack is full
	{
		return false;
	}

	top++;
	item[top] = newitem;

	return true;
}

template <class T>
bool StackType<T>::pop(T& newitem)
{
	if (top == -1)   // Check if stack is empty
	{
		return false;
	}

	newitem = item[top];

	top--;

	return true;
}