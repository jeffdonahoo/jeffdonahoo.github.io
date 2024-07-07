#include <stdio.h>

void Leak()
{
  char str[30];
}

int main()
{
  Leak();
  exit(0);
}
