#include <string>

using namespace std;

class Employee 
{
public:
	Employee(string name, int salary);
	virtual void print();     // TRY THIS WITHOUT VIRTUAL!!

private:
	string m_name;
	int m_salary;
};

class Manager : public Employee
{
public:
	Manager(string name, int salary, int level);
	void print();
private:
	int m_level;
};

class DeliverEmp : public Employee
{
public:
	DeliverEmp(string name, int salary, string truckTag);
	void print();
private:
	string m_truckTag;
};