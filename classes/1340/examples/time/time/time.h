class TimeType 
{

public:
	void Set(int hours, int minutes, int seconds);
	void Write() const;
	bool Equal(TimeType otherTime) const;
	bool LessThan(TimeType otherTime) const;

private:
	bool ValidTime() const;
	int m_hrs;
	int m_mins;
	int m_secs;
};