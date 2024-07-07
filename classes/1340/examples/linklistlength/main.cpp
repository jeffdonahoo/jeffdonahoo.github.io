#include <iostream>

using namespace std;

struct Node
{
	int number;
	Node *nPtr;
};

int ListLen(Node *head)
{
	int ct=0;
	while (head)
	{
		ct++;
		head = head->nPtr;
	}
	return ct;
}

void main()
{

	Node* head=NULL, *tmp;
	for (int i=0; i < 23; i++)
	{
		tmp = new Node;
		tmp->nPtr = head;
		tmp->number = i;
		head = tmp;
	}

	cout << ListLen(NULL) << endl;
}