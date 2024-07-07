#include <iostream>
#include "stack.h"

using namespace std;

enum {ENTERURL, BACK, QUIT};
int prompt();

void main()
{
	UrlType url;
	StackType urlStack;
	
	int choice = prompt();
	while (choice != QUIT)
	{
		
		switch (choice)
		{
		case ENTERURL:
			cout << "Enter URL: ";
			cin >> url;
			if (!urlStack.push(url))
			{
				cout << "Unable to push any more" << endl;
			}
			break;
		case BACK:
			if (!urlStack.pop(url))
			{
				cout << "Unable to pop" << endl;
			}
			else
			{
				cout << url << endl;
			}
			break;
		}
		choice = prompt();
	}
}

int prompt()
{
	int choice;
	bool done = false;
	do
	{
		cout << ENTERURL << " - Enter new URL" << endl;
		cout << BACK << " - Back" << endl;
		cout << QUIT << " - Quit" << endl << endl;
		cout << "Choice: ";
		
		cin >> choice;
		
		if ((choice != ENTERURL) && (choice != BACK) && (choice != QUIT))
		{
			cerr << "Bad choice.  Try again." << endl;
		}
		else
		{
			done = true;
		}
	}
	while (!done);
	
	return choice;
}