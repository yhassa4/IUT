#include <stdio.h>
void echange2(float *a, float *b) {
   float temp = *a;
   a = b;
   b = &temp;
}

int main() {
float pi = 2.71828, e = 3.14159;
printf("Avant echange : pi = %f, e = %f.\n",pi,e);
echange2(pi,e);
printf("Apres echange : pi = %f, e = %f.\n",pi,e);
return 0;
}
