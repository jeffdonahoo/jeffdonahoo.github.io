#include <iostream>

using namespace std;

class TimeType 
{

public:
	TimeType(int hours, int minutes, int seconds);
	TimeType TopTime(const TimeType& otherTime);
	bool operator<(const TimeType& otherTime) const;
	friend ostream& operator<<(ostream& os, const TimeType& time);

private:
	int m_hrs;
	int m_mins;
	int m_secs;
};