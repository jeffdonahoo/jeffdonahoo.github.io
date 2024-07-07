#include <iostream>
#include "employee.h"
#include "queue.h"

using namespace std;

void main()
{
	Manager* jane = new Manager("Jane", 50, 3);
	DeliverEmp* bob = new DeliverEmp("Bob", 30, "XXX 999");

/*	jane->print();
	bob->print();

	return; */

	QueType<Employee*> q;

	q.Enqueue(jane);
	q.Enqueue(bob);

	Employee* emp;
	while (!q.IsEmpty())
	{
		q.Dequeue(emp);
		emp->print();
	}
}