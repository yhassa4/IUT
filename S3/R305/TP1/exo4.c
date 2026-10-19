#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

volatile unsigned int count = 0;
volatile int step = 1;

void handler_SIGUSR1(int sig) {
    count = 1;
}

void handler_SIGUSR2(int sig) {
    step = -step;
}

void handler_SIGALRM(int sig) {
    printf("count = %u\n", count);
    fflush(stdout);
    alarm(1);
}

int main() {
    signal(SIGUSR1, handler_SIGUSR1);
    signal(SIGUSR2, handler_SIGUSR2);
    signal(SIGALRM, handler_SIGALRM);

    alarm(1);

    for (count = 1; count > 0; count += step);

    return 0;
}
