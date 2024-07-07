#include <iostream>

using namespace std;

void print(int item);
int Power(int x, int n);

void main()
{

	print(10);
//	Power(2,5);
}

void print(int item)
{
	if (item != 1)
	{
		print(item - 1);
	}
	cout << item << endl;
}

int Power(int x, int n)	
{
	if (n == 0)
	{
		cout << "returning Power(" << x << ", 0) = 1" << endl;
		return 1;
	}
	else
	{
		int answer = x * Power(x, n-1);
		cout << "returning Power(" << x << ", " << n << ") = " << answer << endl;
		return answer;
	}
}