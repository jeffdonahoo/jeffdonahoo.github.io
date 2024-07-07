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

TimeType::TimeType(const TimeType& srcTime)
{
	m_hrs = srcTime.m_hrs;
	m_mins = srcTime.m_mins;
	m_secs = srcTime.m_secs;
	
	cout << "Copying ";
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

void TimeType::Write(char header[]) const
{
	cout << header;
	Write();
}

bool TimeType::ValidTime() const
{
	return ((m_hrs <= 12) && (m_mins <=59) && (m_secs <= 59) &&
		(m_hrs >= 1) && (m_mins >= 0) && (m_secs >= 0));
}

bool TimeType::operator==(const TimeType& otherTime) const
{
	return ((m_hrs == otherTime.m_hrs) && 
		(m_mins == otherTime.m_mins) &&
		(m_secs == otherTime.m_secs));
}

bool TimeType::operator<(const TimeType& otherTime) const
{
	return ((m_hrs < otherTime.m_hrs) ||
		((m_hrs == otherTime.m_hrs) && (m_mins < otherTime.m_mins)) ||
		((m_hrs == otherTime.m_hrs) && (m_mins == otherTime.m_mins) && (m_secs < otherTime.m_secs)));
}

TimeType TimeType::operator+(const TimeType& otherTime) const
{

	int hrs, secs, mins;

	secs = m_secs + otherTime.m_secs;  // Compute number of seconds
	if (secs >= 60)  // Test if number of seconds exceeds or equal 60
	{
		mins = 1;   // Increment number of minutes
		secs %= 60; 
	}

	mins += m_mins + otherTime.m_mins;
	if (mins >= 60)
	{
		hrs = 1;  // Increment number of hours
		mins %= 60;
	}

	hrs += m_hrs + otherTime.m_hrs;
	if (hrs > 12)
	{
		hrs %= 12;
	}

	return TimeType(hrs, mins, secs);
}

void TimeType::operator=(const TimeType& srcTime)
{
	m_hrs = srcTime.m_hrs;
	m_mins = srcTime.m_mins;
	m_secs = srcTime.m_secs;
	
	cout << "Assigning ";
	Write();
}
