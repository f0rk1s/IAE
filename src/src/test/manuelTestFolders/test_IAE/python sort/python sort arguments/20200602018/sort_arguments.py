import sys

# Remove the first argument (script name) from sys.argv
args = sys.argv[1:]

# Sort the arguments
args.sort()

# Print the sorted arguments
print("Sorted arguments:", args)
