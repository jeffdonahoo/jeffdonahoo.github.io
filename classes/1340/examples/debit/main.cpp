#include <iostream>
#include "debit.h"

using namespace std;

void main()
{
	DebitCard c(1000);
	
	if (!c.Credit(500))
	{
		cerr << "Credit of $500 failed" << endl;
	}
	
	if (!c.Debit(300))
	{
		cerr << "Debit of $300 failed" << endl;
	}
	
	if (!c.Debit(300))
	{
		cerr << "Debit of $300 failed" << endl;
	}
	
	cout << "Balance: $" << c.Balance() << endl;
	
	if (!c.Credit(800))
	{
		cerr << "Credit of $800 failed" << endl;
	}
	
	if (!c.Debit(800))
	{
		cerr << "Credit of $800 failed" << endl;
	}
	
}