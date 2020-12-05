import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Task2 {
    /*
    Написати програму, яка приймає на вхід текстовий файл, і повертає наступну
    інформацію
    - Найкоротше слово і кількість його повторень в тексті. Якщо в тексті є декілька,
    однаково коротких слів, то повертати те, яке раніше по алфавіту. Наприклад,
    між словами “Олег” та “Анна” потрібно вибрати “Анна”
    - Найдовше слово і кількість його повторень в тексті. Якщо в тексті є декілька,
    однаково довгих слів, то повертати те, яке пізніше по алфавіту. Наприклад, між
    словами “Олег” та “Анна” потрібно вибрати “Олег”.
    - Регістр не враховувати
     */
    public static void main(String[] args) {

        Map<String, Integer> uniqueWordsQuantity = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Map<Integer, String> uniqueWordsQuantityOfLetters = new TreeMap<>();
        List<String> allWords = new ArrayList<>();
        List<String> shortestWordsList = new ArrayList<>();
        List<String> longestWordsList = new ArrayList<>();
        final String path = "src/TolstoiVoinaIMir1.txt";

        try {
            FileInputStream inputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            scanner.useDelimiter("  ");
            while (scanner.hasNext()) {
                String tempString = scanner.next();
                tempString = tempString.replaceAll("[\\[\\],.!&?\"<>'{}()*+@#$|/=:;—«»]", " ").replaceAll("[\\p{Digit}]", " ");
                StringBuilder word = new StringBuilder();
                int counterOfWords = 1;
                int counterOfLetters = 0;
                for (int i = 0; i < tempString.length(); i++) {
                    Character ch = tempString.charAt(i);
                    if ((Character.isAlphabetic(ch)) || (ch == '-')) {
                        word.append(ch);
                        counterOfLetters++;
                    } else if (Character.isSpaceChar(ch) || word.length() >= 1) {
                        allWords.add(word.toString());
                        uniqueWordsQuantityOfLetters.put(counterOfLetters, word.toString());
                        if (uniqueWordsQuantity.containsKey(word.toString())) {
                            uniqueWordsQuantity.put(word.toString(), ++counterOfWords);
                        } else {
                            uniqueWordsQuantity.put(word.toString(), 1);
                        }
                        word.delete(0, word.length());
                        counterOfLetters = 0;
                        counterOfWords = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Integer minLengthOfWord = 1;
        Integer maxLengthOfWord = 45;
        findShortestWords(allWords, shortestWordsList, minLengthOfWord);
        if (shortestWordsList.isEmpty()) {
            minLengthOfWord++;
            findShortestWords(allWords, shortestWordsList, minLengthOfWord);
        }
        findLongestWords(allWords, longestWordsList, maxLengthOfWord);
        if (longestWordsList.isEmpty()) {
            maxLengthOfWord--;
            findLongestWords(allWords, longestWordsList, maxLengthOfWord);
        }
        Collections.sort(shortestWordsList);
        int countOfShortWords = 0;
        for (int i = 0; i < shortestWordsList.size(); i++) {
            if (shortestWordsList.get(i).equals(shortestWordsList.get(0))) {
                countOfShortWords++;
            }
        }
        Collections.sort(shortestWordsList);
        int countOfLongWords = 0;
        for (int i = 0; i < longestWordsList.size(); i++) {
            if (longestWordsList.get(i).equals(longestWordsList.get(longestWordsList.size()))) {
                countOfLongWords++;
            }
        }


        System.out.println("The shortest word in War And Piece Tom 1 is: \"" + shortestWordsList.get(0) + "\" it occurs " + countOfShortWords + " times");
        System.out.println("The longest word in War And Piece Tom 1 is: \"" + longestWordsList.get(longestWordsList.size()) + "\" it occurs " + countOfLongWords + " times");
    }

    private static void findShortestWords(List<String> allWords, List<String> shortestWordList, Integer minLengthOfWord) {
        for (String word : allWords) {
            if ((word.length() == minLengthOfWord) && !word.equals("-")) {
                shortestWordList.add(word.toLowerCase());
            }
        }
    }

    private static void findLongestWords(List<String> allWords, List<String> longestWordList, Integer maxLengthOfWord) {
        for (String word : allWords) {
            if ((word.length() == maxLengthOfWord) && !word.equals("-")) {
                longestWordList.add(word.toLowerCase());
            }
        }
    }


}
