#include <iostream>
#include "employee.h"

using namespace std;

Employee::Employee(string name, int salary)
{
	m_name = name;
	m_salary = salary;
}

void Employee::print()
{
	cout << "Name: " << m_name << "  " << "Salary: " << m_salary << "  ";
}

Manager::Manager(string name, int salary, int level) : Employee(name, salary)
{
	m_level = level;
}

void Manager::print()
{
	Employee::print();
	cout << "Mgr Level: " << m_level << " Manager" << endl;
}

DeliverEmp::DeliverEmp(string name, int salary, string truckTag) : Employee(name, salary)
{
	m_truckTag = truckTag;
}

void DeliverEmp::print()
{
	Employee::print();
	cout << "Truck Tag: " << m_truckTag << " Delivery Person" << endl;
}

