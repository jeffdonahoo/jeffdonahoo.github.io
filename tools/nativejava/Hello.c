#include "Hello.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_Hello_printGreeting(JNIEnv *env, jobject obj) {
  printf("Hello World\n");
}
