class Number
{

public:
	Number(int num);
	Number(const Number& srcNum);;
	void CopyNumber(Number srcNum);	

private:
	int m_num;  // Number to remember
};