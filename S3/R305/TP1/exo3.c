#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <sys/wait.h> 
#include <time.h>
#include <signal.h>

#define TABSIZE 10000

void gestion_signal(int sig) {
    printf("PID %d out.\n", getpid()); 
    exit(0);
}

int main(int argc, char ** argv) { 
    pid_t id;
    int nbfils = 0, n = 0;
    int i = 0, status = 0;
    unsigned char arr[TABSIZE]; 
    srandom(time(NULL));

    for (i = 0; i < TABSIZE; i++)
        arr[i] = (unsigned char) (random() % 255) + 1;

    printf("Enter a number between 0 and %d: ", TABSIZE- 1); 
    scanf(" %d", &i);
    if (i >= 0 && i < TABSIZE) arr[i] = 0; 
    
    if (argc > 1) nbfils = atoi(argv[1]);
    else {
        fprintf(stderr, "Usage: ./exo3 number \n");
        exit(1);
    }

    if (nbfils < 1 || nbfils > 100) nbfils = 1;

    signal(SIGTERM, SIG_IGN);

    printf("Main process: %d\n", getpid()); 

    for (n = 0; n < nbfils; n++) {
        id = fork();
        if (id < 0) {
            perror("fork");
            exit(1);
        }
        if (id == 0) {
            signal(SIGTERM, gestion_signal);
            printf("Child process: %d (part %d)\n", getpid(), n);

            int start = (TABSIZE / nbfils) * n;
            int end = (n == nbfils - 1) ? TABSIZE : (TABSIZE / nbfils) * (n + 1);

            for (i = start; i < end; i++) {
                putc('.', stdout); fflush(stdout);
                if (arr[i] == 0) {
                    exit(1);
                }
            }
            exit(0);
        }
    }
    
    pid_t fils;
    for (n = 0; n < nbfils; n++) {
        fils = wait(&status);
        if (fils == -1) {
            perror("wait");
            exit(1);
        }

        if (WIFEXITED(status)) {
            if (WEXITSTATUS(status) == 1) {
                printf("\nProcess %d got a needle!\n", fils);
                kill(0, SIGTERM);
                exit(0);
            }
        } else {
            fprintf(stderr, "Child %d terminated abnormally.\n", fils);
        }
    }

    printf("\nNo needles.\n");
    return 0; 
}