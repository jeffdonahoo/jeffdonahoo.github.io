// Implementation file for TimeType

#include <iostream>
#include "time.h"

using namespace std;

TimeType::TimeType(int hrs, int mins, int secs)
{
	m_hrs = hrs;
	m_mins = mins;
	m_secs = secs;
}

TimeType TimeType::TopTime(const TimeType& otherTime)
{
	if (otherTime < *this)
	{
		return *this;
	}
	else
	{
		return otherTime;
	}
}

bool TimeType::operator<(const TimeType& otherTime) const
{
	return ((m_hrs < otherTime.m_hrs) ||
		((m_hrs == otherTime.m_hrs) && (m_mins < otherTime.m_mins)) ||
		((m_hrs == otherTime.m_hrs) && (m_mins == otherTime.m_mins) && (m_secs < otherTime.m_secs)));
}

ostream& operator<<(ostream &os, const TimeType& time)
{

	os << time.m_hrs << ":" << time.m_mins << ":" << time.m_secs;

	return os;
}