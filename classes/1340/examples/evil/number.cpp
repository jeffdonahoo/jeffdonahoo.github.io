#include "number.h"

Number::Number(int num) 
{
	m_num = num;
}

Number::Number(const Number& srcNum) 
{
	CopyNumber(srcNum);
}

void Number::CopyNumber(Number srcNum) 
{
	m_num = srcNum.m_num;
}