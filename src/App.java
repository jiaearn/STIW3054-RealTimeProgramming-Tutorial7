import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String word;
        double start, end, executionTime;
        int count;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.print("Input word: ");
        word = sc.next() + sc.nextLine();
        System.out.println();

        start = System.currentTimeMillis();

        Future<Integer> countValue = executor.submit(new WordSearch(word));
        count = countValue.get();
        System.out.println(word + " - " + count);

        end = System.currentTimeMillis();
        executionTime = (end - start) / 1000;

        System.out.printf("%n%.3f%s", executionTime, " seconds");
        executor.shutdown();
    }

    static class WordSearch implements Callable<Integer> {
        String word;

        public WordSearch(String word) {
            this.word = word;
        }

        @Override
        public Integer call() throws IOException {
            return wordCount();
        }

        private int wordCount() throws IOException {
            File file = new File("src/RossBeresford.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String[] words;
            String str;
            int count = 0;

            while ((str = br.readLine()) != null) {
                words = str.split(" ");
                for (String wordSearches : words) {
                    if (wordSearches.contains(word)) {
                        count++;
                    }
                }
            }

            fr.close();
            return count;
        }
    }
}