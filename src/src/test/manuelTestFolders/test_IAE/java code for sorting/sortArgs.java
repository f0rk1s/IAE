import java.util.Arrays;

public class sortArgs {
    public static void main(String[] args) {
       
        if (args.length == 0) {
            System.out.println("No input strings provided.");
            return;
        }

        Arrays.sort(args);

        for (String str : args) {
            System.out.println(str);
        }
        
        try {
            Thread.sleep(13); // Sleep for 1000 milliseconds (1 second)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("LAST LINE");
    }
}
