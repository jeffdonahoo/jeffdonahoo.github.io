// Implementation file for TimeType

#include <iostream>
#include "time.h"

using namespace std;

int TimeType::s_timeCount = 0; // Initialize constant

TimeType::TimeType()
{
	m_hrs = 12;
	m_mins = 0;
	m_secs = 0;

	s_timeCount++;
}

TimeType::TimeType(int hrs, int mins, int secs)
{
	m_hrs = hrs;
	m_mins = mins;
	m_secs = secs;

	if (!ValidTime())
	{
		cerr << "Bad Time\n";
		exit(1);
	}

	s_timeCount++;
}

TimeType::~TimeType()
{

	s_timeCount--;
	cout << s_timeCount << " instances of TimeType left" << endl;
}

void TimeType::Write() const
{
	cout << m_hrs << ":" << m_mins << ":" << m_secs << endl;
}

bool TimeType::ValidTime() const
{
	return ((m_hrs <= 12) && (m_mins <=59) && (m_secs <= 59) &&
		(m_hrs >= 1) && (m_mins >= 0) && (m_secs >= 0));
}

/*TimeType::TimeType(const TimeType& s)
{
	m_hrs = s.m_hrs;
	m_mins = s.m_mins;
	m_secs = s.m_secs;

	s_timeCount++;
} */