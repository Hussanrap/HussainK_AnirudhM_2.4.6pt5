import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {

    private static HashMap<String, Double> sentiment = new HashMap<>();
    private static ArrayList<String> posAdjectives = new ArrayList<>();
    private static ArrayList<String> negAdjectives = new ArrayList<>();

    static {
        try {
            Scanner input = new Scanner(new File("ConsumerLab_Code\\cleanSentiment.csv"));
            while (input.hasNextLine()) {
                String[] temp = input.nextLine().split(",");
                sentiment.put(temp[0], Double.parseDouble(temp[1]));
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing cleanSentiment.csv");
        }

        // Read in the positive adjectives in positiveAdjectives.txt
        try {
            Scanner input = new Scanner(new File("ConsumerLab_Code\\positiveAdjectives.txt"));
            while (input.hasNextLine()) {
                posAdjectives.add(input.nextLine().trim());
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing positiveAdjectives.txt\n" + e);
        }

        // Read in the negative adjectives in negativeAdjectives.txt
        try {
            Scanner input = new Scanner(new File("ConsumerLab_Code\\negativeAdjectives.txt"));
            while (input.hasNextLine()) {
                negAdjectives.add(input.nextLine().trim());
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing negativeAdjectives.txt");
        }
    }

    /** 
     * Returns a string containing all of the text in fileName (including punctuation), 
     * with words separated by a single space 
     */
    public static String textToString(String fileName) {
        StringBuilder temp = new StringBuilder();
        try {
            Scanner input = new Scanner(new File(fileName));

            // Add 'words' in the file to the string, separated by a single space
            while (input.hasNext()) {
                temp.append(input.next()).append(" ");
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Unable to locate " + fileName);
        }
        // Remove any additional space that may have been added at the end of the string
        return temp.toString().trim();
    }

    /**
     * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
     */
    public static double sentimentVal(String word) {
        return sentiment.getOrDefault(word.toLowerCase(), 0.0);
    }

    /**
     * Returns the ending punctuation of a string, or the empty string if there is none 
     */
    public static String getPunctuation(String word) {
        StringBuilder punc = new StringBuilder();
        for (int i = word.length() - 1; i >= 0; i--) {
            if (!Character.isLetterOrDigit(word.charAt(i))) {
                punc.append(word.charAt(i));
            } else {
                return punc.toString();
            }
        }
        return punc.toString();
    }

    /**
     * Returns the word after removing any beginning or ending punctuation
     */
    public static String removePunctuation(String word) {
        while (word.length() > 0 && !Character.isAlphabetic(word.charAt(0))) {
            word = word.substring(1);
        }
        while (word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length() - 1))) {
            word = word.substring(0, word.length() - 1);
        }
        return word;
    }

    public static double Totalsentiment(String file) {
        List<String> wordList = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {

            String bruh;
            while ((bruh = reader.readLine()) != null) {
                
                String[] words = bruh.split("\\s+"); 
                for (String word : words) {
                    wordList.add(word); 
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        double values = 0;
        for (String words : wordList) {
            values += Review.sentimentVal((words));
        }
        return values;
    }
    /** 
     * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
     */
    public static String randomPositiveAdj() {
        int index = (int) (Math.random() * posAdjectives.size());
        return posAdjectives.get(index);
    }

    /** 
     * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
     */
    public static String randomNegativeAdj() {
        int index = (int) (Math.random() * negAdjectives.size());
        return negAdjectives.get(index);
    }

    /** 
     * Randomly picks a positive or negative adjective and returns it.
     */
    public static String randomAdjective() {
        boolean positive = Math.random() < .5;
        if (positive) {
            return randomPositiveAdj();
        } else {
            return randomNegativeAdj();
        }
    }

    public static String fakeReview(String filename) {
        String fileContents = textToString(filename);
        boolean hasAsterisk = true;

        while (hasAsterisk) {
            int index = fileContents.indexOf("*");

            if (index == -1) {
                hasAsterisk = false;
            } else {
                int endIndex = index + 1;
                while (endIndex < fileContents.length() && fileContents.charAt(endIndex) != ' ') {
                    endIndex++;
                }

                String randomAdjective = randomAdjective();
                
                // Replace the asterisk with the random adjective
                fileContents = fileContents.substring(0, index) + randomAdjective + fileContents.substring(endIndex);
            }
        }

        return fileContents;
    }
}


