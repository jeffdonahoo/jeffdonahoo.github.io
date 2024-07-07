class TimeType 
{

public:
	TimeType();
	TimeType(int hours, int minutes, int seconds);
//	TimeType(const TimeType& s);
	~TimeType();
	void Write() const;

private:
	bool ValidTime() const;
	int m_hrs;
	int m_mins;
	int m_secs;

	static int s_timeCount;  // Number of instance of TimeType
};