import sys

shopping = open(sys.argv[1])
lines = shopping.readlines()
words = sorted(lines[0].split())
words.sort()
for word in words:
    print(word)