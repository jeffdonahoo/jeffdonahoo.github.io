#include <stdio.h>

char *greetDad();
int writeLetter(char *(*getPerson)(void));

int main() {

  writeLetter(greetDad);
}

int writeLetter(char *(*getPerson)(void)) {
  printf("Dear %s\nHi\n", getPerson());
}
 
char *greetDad() {
  return "Dad";
}

char *greetMom() {
  return "Mom";
}
