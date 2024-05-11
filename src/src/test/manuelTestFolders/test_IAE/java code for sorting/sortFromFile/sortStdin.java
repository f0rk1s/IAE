import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class sortStdin {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ArrayList<String> wordList = new ArrayList();
        while (input.hasNext()) {
            String word = input.next();
            wordList.add(word);
        }

        Collections.sort(wordList);

        for (String s : wordList) {
            System.out.println(s);
        }
    }
}