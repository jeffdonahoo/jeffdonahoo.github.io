// Implementation file for TimeType

#include <iostream>
#include "time.h"

using namespace std;


TimeType::TimeType()
{
	m_hrs = 12;
	m_mins = 0;
	m_secs = 0;

	cout << "Constructing ";
	Write();
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

	cout << "Constructing ";
	Write();
}

TimeType::~TimeType()
{
	cout << "Time Flies...";
	Write();
}

void TimeType::Set(int hrs, int mins, int secs)
{
	m_hrs = hrs;
	m_mins = mins;
	m_secs = secs;

	if (!ValidTime())
	{
		cerr << "Bad Time\n";
		exit(1);
	}
}

void TimeType::Write() const
{
	cout << m_hrs << ":" << m_mins << ":" << m_secs << endl;
}

bool TimeType::Equal(TimeType otherTime) const
{
	return ((m_hrs == otherTime.m_hrs) && 
		(m_mins == otherTime.m_mins) &&
		(m_secs == otherTime.m_secs));
}

bool TimeType::LessThan(TimeType otherTime) const
{
	return ((m_hrs < otherTime.m_hrs) ||
		((m_hrs == otherTime.m_hrs) && (m_mins < otherTime.m_mins)) ||
		((m_hrs == otherTime.m_hrs) && (m_mins == otherTime.m_mins) && (m_secs < otherTime.m_secs)));
}

bool TimeType::ValidTime() const
{
	return ((m_hrs <= 12) && (m_mins <=59) && (m_secs <= 59) &&
		(m_hrs >= 1) && (m_mins >= 0) && (m_secs >= 0));
}