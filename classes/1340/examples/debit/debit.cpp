// Implementation file for TimeType

#include <iostream>
#include "debit.h"

using namespace std;

DebitCard::DebitCard()
{
	m_balance = 0;
	m_creditLimit = 500;
}

DebitCard::DebitCard(int limit)
{
	m_balance = 0;
	m_creditLimit = limit;
}

DebitCard::~DebitCard()
{
	cout << "Card has expired.  You lost $" << m_balance << endl;
}

bool DebitCard::Debit(float payment)
{
	if (m_balance >= payment)
	{
		m_balance -= payment;
	}
	else
	{
		return false;
	}

	return true;
}

bool DebitCard::Credit(float payment)
{
	if ((m_balance + payment) <= m_creditLimit)
	{
		m_balance += payment;
	}
	else
	{
		return false;
	}

	return true;
}

float DebitCard::Balance() const
{
	return m_balance;
}
