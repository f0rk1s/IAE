#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Comparator function for sorting strings
int compareStrings(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

int main(int argc, char *argv[]) {
    // Check if there are arguments passed
    if (argc < 2) {
        printf("Usage: %s <string1> <string2> ... <stringN>\n", argv[0]);
        return 1;
    }

    // Sort the input strings
    qsort(argv + 1, argc - 1, sizeof(char *), compareStrings);

    // Print the sorted strings
    printf("Sorted strings:\n");
    for (int i = 1; i < argc; i++) {
        printf("%s\n", argv[i]);
    }

    return 0;
}
