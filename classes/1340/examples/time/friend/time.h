#include <iostream>

using namespace std;

class TimeType 
{

public:
	TimeType();
	TimeType(int hours, int minutes, int seconds);
	TimeType(const TimeType& srcTime);
	~TimeType();
	void Set(int hours, int minutes, int seconds);
	void Write() const;
	void Write(char header[]) const;
	bool operator==(const TimeType& otherTime) const;
	bool operator<(const TimeType& otherTime) const;
	void operator=(const TimeType& srcTime);
	TimeType operator+(const TimeType& otherTime) const;
	TimeType operator+(int hrs) const;
	friend TimeType operator+(int hrs, const TimeType& time);
	friend ostream& operator<<(ostream& os, const TimeType& time);

private:
	bool ValidTime() const;
	int m_hrs;
	int m_mins;
	int m_secs;
};