class TimeType 
{

public:
	TimeType();
	TimeType(int hours, int minutes, int seconds);
	~TimeType();
	void Set(int hours, int minutes, int seconds);
	void Write() const;
	void Write(char header[]) const;
	bool operator==(const TimeType& otherTime) const;
	bool operator<(const TimeType& otherTime) const;
	TimeType operator+(const TimeType& otherTime) const;

private:
	bool ValidTime() const;
	int m_hrs;
	int m_mins;
	int m_secs;
};