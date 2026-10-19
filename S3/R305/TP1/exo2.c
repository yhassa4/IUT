#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/wait.h>
#include <time.h>

int main(int argc, char *argv[]){

    const int TABSIZE =10000;
    unsigned char arr[TABSIZE];
    int i = 0;
    int n;

    if (argc > 1) N = atoi(argv[1]);
    if (N < 1 || N > 100) N = 1;

    srandom(time(NULL));
    // entasser du foin

    for (i = 0; i < TABSIZE; i++){
        arr[i] = (unsigned char) (random() % 255) + 1;
    }

    // cacher l'aiguille
    printf("Enter a number between 0 and %d: ", TABSIZE);
    scanf(" %d", &i);
            
    if (i >= 0 && i < TABSIZE){ 
                arr[i] = 0;}

    pid_t pid = fork();

    if (pid < 0) {
        perror("fork failed");
        exit(1);
    }
            
    if (pid == 0) {

        int found = 0;
        for (i = 0; i < TABSIZE / 2; i++) {
            if (arr[i] == 0) {
            found = 1;
            break;
            }
            exit(found); 
            }
    }
    else {

        int found_parent = 0;
        for (i = TABSIZE / 2; i < TABSIZE; i++) {
            if (arr[i] == 0) {
                found_parent = 1;
                break;
                }
            }
        int status;
        wait(&status);
        int found_child = WEXITSTATUS(status); //verifier que le fils s'est terminer normalement (extrait uniquement le code de sortie du fils )


        if (found_parent || found_child) {
                printf("Got a needle!\n");
            } else {
                printf("No needles.\n");
            }
        }

        return 0;
}

