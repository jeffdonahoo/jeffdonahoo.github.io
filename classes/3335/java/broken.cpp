#include <iostream>

using namespace std;

class Inside {

  public:
    Inside(int p) {cout << "Inside constructor: " << p << endl;}
};

class Outside {
  Inside memVar;
};

main() {
  Outside x;
}

// bob.c: In function `int main()':
// bob.c:16: no matching function for call to `Outside::Outside ()'
/  bob.c:13: candidates are: Outside::Outside(const Outside &)
