#include <stdio.h>      // printf()
#include <stdbool.h>    // bool
#include <arpa/inet.h>  // sockaddr_in6, inet_pton()
#include <string.h>     // memset()
#include "Practical.h"

void FillAddress(struct sockaddr *address, const char *paddress, in_port_t port, int family);
void assertEquals(const struct sockaddr *addr1, const struct sockaddr *addr2);
void assertNotEquals(const struct sockaddr *addr1, const struct sockaddr *addr2);

int main(int argc, char *argv[]) {

    struct sockaddr_in ipv4addr1;
    FillAddress((struct sockaddr *) &ipv4addr1, "129.62.148.1", 5000, AF_INET);
    struct sockaddr_in ipv4addr2;
    FillAddress((struct sockaddr *) &ipv4addr2, "129.62.148.1", 5000, AF_INET);

    assertEquals((struct sockaddr *) &ipv4addr1, (struct sockaddr *) &ipv4addr2);

    struct sockaddr_in6 ipv6addr1;
    FillAddress((struct sockaddr *) &ipv6addr1, "fec0::250:56ff:feb5:74fe", 5000, AF_INET6);
    struct sockaddr_in6 ipv6addr2;
    FillAddress((struct sockaddr *) &ipv6addr2, "fec0::250:56ff:feb5:74fe", 5000, AF_INET6);

    assertEquals((struct sockaddr *) &ipv6addr1, (struct sockaddr *) &ipv6addr2);

    assertNotEquals((struct sockaddr *) &ipv4addr1, (struct sockaddr *) &ipv6addr1);

    assertNotEquals((struct sockaddr *) &ipv4addr1, NULL);
    assertNotEquals(NULL, (struct sockaddr *) &ipv4addr1);

    assertNotEquals((struct sockaddr *) &ipv6addr1, NULL);
    assertNotEquals(NULL, (struct sockaddr *) &ipv6addr1);

    ipv4addr2.sin_port = 4000;
    assertNotEquals((struct sockaddr *) &ipv4addr1, (struct sockaddr *) &ipv4addr2);

    ipv6addr2.sin6_port = 4000;
    assertNotEquals((struct sockaddr *) &ipv6addr1, (struct sockaddr *) &ipv6addr2);
}

void FillAddress(struct sockaddr *address, const char *paddress, in_port_t port, int family) {
    int rtnVal;
    if (family == AF_INET) {
    	struct sockaddr_in *address4 = (struct sockaddr_in *) address;
    	memset(address4, 0, sizeof(struct sockaddr_in));
    	address4->sin_family = family;
    	rtnVal = inet_pton(AF_INET, paddress, &(address4->sin_addr.s_addr));
    } else {
    	struct sockaddr_in6 *address6 = (struct sockaddr_in6 *) address;
    	memset(address6, 0, sizeof(struct sockaddr_in6));
    	address6->sin6_family = family;
        rtnVal = inet_pton(AF_INET6, paddress, &(address6->sin6_addr.s6_addr));
    }
    if (rtnVal == 0)
        DieWithUserMessage("inet_pton() failed", "invalid address string");
    else if (rtnVal < 0)
        DieWithSystemMessage("inet_pton() failed");
}

void assertEquals(const struct sockaddr *addr1, const struct sockaddr *addr2) {
    if (!SockAddrsEqual(addr1, addr2)) {
    	PrintSocketAddress(addr1, stderr);
    	fprintf(stderr, " should equal ");
    	PrintSocketAddress(addr2, stderr);
    	fputc('\n', stderr);
    }
}

void assertNotEquals(const struct sockaddr *addr1, const struct sockaddr *addr2) {
    if (SockAddrsEqual(addr1, addr2)) {
    	PrintSocketAddress(addr1, stderr);
    	fprintf(stderr, " should not equal ");
    	PrintSocketAddress(addr2, stderr);
    	fputc('\n', stderr);
    }
}
