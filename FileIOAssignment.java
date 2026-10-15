
import java.io.*;
import java.util.*;

public class FileIOAssignment {
    public static void main(String[] args) {
        Map<String, Integer> frequency = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("input.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase()
                        .split("[^a-z0-9]+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        frequency.put(
                            word,
                            frequency.getOrDefault(word, 0) + 1
                        );
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter("output.txt"))) {

                bw.write("Word Frequency Report");
                bw.newLine();
                bw.write("---------------------");
                bw.newLine();

                for (Map.Entry<String, Integer> entry
                        : frequency.entrySet()) {
                    bw.write(entry.getKey() + " : "
                            + entry.getValue());
                    bw.newLine();
                }
            }

            System.out.println("Word frequency counted successfully.");
            System.out.println("Result saved in output.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Error: input.txt not found.");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
