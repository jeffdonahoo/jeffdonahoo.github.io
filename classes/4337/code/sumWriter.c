#include <sys/types.h>
#include <sys/stat.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void lock(int fd);
void unlock(int fd);

const bool enableLock = true;
const int SUMVALUE = 1000;
const char* FILENAME = "data.txt";

int min(int a, int b) {return (a > b) ? b:a;}

int main(int argc, char *argv[]) {

  while (true) {
    int fd = open(FILENAME, O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd < 0) {
      perror("open() failed");
      exit(1);
    }

    lock(fd);
    int32_t left = SUMVALUE;
    while (left > 0) {
      int32_t dec = rand() % min(10, left+1);
      if (write(fd, &dec, sizeof(dec)) == -1) {
        perror("write() failed");
        exit(1);
      }
      left -= dec;
    }
    ftruncate(fd, lseek(fd, 0, SEEK_CUR));
    if (enableLock) unlock(fd);
    close(fd);
  }
}

void lock(int fd) {
  struct flock lock = {.l_type = F_WRLCK, .l_start = 0, .l_whence = SEEK_SET, .l_len = 0};
  if (fcntl(fd, F_SETLKW, &lock) == -1) {
    perror("fcntl() failed");
    exit(1);
  }
}

void unlock(int fd) {
  struct flock lock = {.l_type = F_UNLCK, .l_start = 0, .l_whence = SEEK_SET, .l_len = 0};
  if (fcntl(fd, F_SETLK, &lock) == -1) {
    perror("fcntl() failed");
    exit(1);
  }
}
