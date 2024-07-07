#include <stdio.h>

void Leak(char *inStr)
{
  char *str = (char *) malloc(strlen(inStr));
  memcpy(str, inStr, strlen(inStr));
}

char *AvoidLeak(char *inStr)
{
  char *str = (char *) malloc(strlen(inStr));
  memcpy(str, inStr, strlen(inStr));
  return str;
}

int main()
{
  char *str;

  Leak("This leaks 19 bytes");
  str = AvoidLeak("This is not a 26 byte leak");
  free(str);
  str = AvoidLeak("12 byte leak");
  exit(0);
}