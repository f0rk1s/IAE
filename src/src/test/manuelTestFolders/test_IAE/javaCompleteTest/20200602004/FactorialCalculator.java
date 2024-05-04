public class FactorialCalculator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FactorialCalculator <number>");
            return;
        }

        int number;
        try {
            number = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }
		
		try {
            Thread.sleep(5000); // Sleep for 1000 milliseconds (1 second)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (number < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
            return;
        }

        long factorial = calculateFactorial(number);
        System.out.println("Factorial of " + number + " is: " + factorial);
    }

    private static long calculateFactorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * calculateFactorial(n - 1);
    }
}
