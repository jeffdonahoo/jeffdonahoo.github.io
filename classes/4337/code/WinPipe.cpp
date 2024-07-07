#include <iostream>
#include <windows.h>

using namespace std;

// Maximum message size for sending from parent to child
const int MESSAGE_SIZE = 1024;

// Windows handles for read and write ends of a pipe.
// These are global so the child thread can see them.
HANDLE r_handle, w_handle;

//
// Function run by the child thread.  Note the Windows Prototype.
//
DWORD WINAPI childThread(LPVOID dummy_val) {
	// Read a message from the parent thread
	char message[MESSAGE_SIZE];

	// Get the message from the parent thread
	unsigned long count;
	ReadFile(r_handle, message, MESSAGE_SIZE, &count, 0);

	cout << "Child Got: " << message << endl;
	return 0;
}

int main() {
	// Make a new pipe.
	if (!CreatePipe( &r_handle, &w_handle, 0, 0)) {
		cout << "Can't create pipe!" << endl;
		return 0;
	}

	// Make a child thread to communicate with
	unsigned long child_id;
	HANDLE child_thread = CreateThread(0, 8096, childThread, 0, 0, &child_id);

	// Get a message from the user
	char message[MESSAGE_SIZE];
	cout << "msg> ";
	cin.getline(message, MESSAGE_SIZE);

	// Send the contents of the message to the child thread
	unsigned long count;
	WriteFile(w_handle, message, MESSAGE_SIZE, &count, 0);

	// Wait 10 seconds to make sure the child has a chance to exit.
	// Note: This is the wrong way to accomplish this.
	Sleep(10000);

	return 0;
}
