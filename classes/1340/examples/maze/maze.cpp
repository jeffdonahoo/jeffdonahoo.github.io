#include <iostream>

using namespace std;

const int BOUND = 8;

void Print(int maze[][BOUND], int x, int y);
void North(int maze[][BOUND], int x, int y);
void South(int maze[][BOUND], int x, int y);
void West(int maze[][BOUND], int x, int y);
void East(int maze[][BOUND], int x, int y);

void main()
{

	int maze[BOUND][BOUND] = {	{1,1,1,0,0,0,0,0},
								{0,0,1,0,0,0,0,0},
								{0,0,1,0,0,1,1,0},
								{0,0,1,1,1,1,0,0},
								{0,0,0,1,0,0,0,0},
								{0,0,0,1,0,0,0,0},
								{0,0,0,1,1,1,0,0},
								{0,0,1,1,0,1,1,1} };

	Print(maze, 0, 0);
	South(maze, 0, 0);
	East(maze, 0, 0);
}

void Print(int maze[][BOUND], int x, int y)
{
	char in;
	for (int i=0; i < 24; i++)
	{
		cout << endl;
	}

	for (i=0; i < BOUND; i++)
	{
		for (int j=0; j < BOUND; j++)
			if ((i == x) && (j == y))
				cout << "X ";
			else
			    cout << maze[i][j] << " ";
		cout << endl;
	}
	cin.get(in);
}

void Done(int maze[][BOUND], int x, int y)
{
	// Am I done?
	if ((x == (BOUND - 1)) && (y == (BOUND - 1)))
	{
		Print(maze, x, y);
		cout << "Done!" << endl;
		exit(0);
	}
}
void North(int maze[][BOUND], int x, int y)
{
	// Can I go North?
	if (x == 0)
		return;  // No!

	if (!maze[x-1][y])
		return;  // Again No!

	// I can go North

	x--;

	// Am I done?
	Done(maze, x, y);

	// Not done
	Print(maze, x, y);
	North(maze, x, y);
	West(maze, x, y);
	East(maze, x, y);
}

void South(int maze[][BOUND], int x, int y)
{
	// Can I go South?
	if (x == (BOUND - 1))
		return;  // No!

	if (!maze[x+1][y])
		return;  // Again No!

	// I can go South

	x++;

	// Am I done?
	Done(maze, x, y);

	// Not done
	Print(maze, x, y);
	South(maze, x, y);
	West(maze, x, y);
	East(maze, x, y);
}

void West(int maze[][BOUND], int x, int y)
{
	// Can I go West?
	if (y == 0)
		return;  // No!

	if (!maze[x][y-1])
		return;  // Again No!

	// I can go West

	y--;

	// Am I done?
	Done(maze, x, y);

	// Not done
	Print(maze, x, y);
	West(maze, x, y);
	North(maze, x, y);
	South(maze, x, y);
}

void East(int maze[][BOUND], int x, int y)
{
	// Can I go East?
	if (y == (BOUND - 1))
		return;  // No!

	if (!maze[x][y+1])
		return;  // Again No!

	// I can go East

	y++;

	// Am I done?
	Done(maze, x, y);

	// Not done
	Print(maze, x, y);
	East(maze, x, y);
	North(maze, x, y);
	South(maze, x, y);
}
