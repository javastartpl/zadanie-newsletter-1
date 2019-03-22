import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class Solution {
    void printSolution() {
        CsvReader csvReader = new CsvReader();
        List<String> names = csvReader.getAllNames();
        List<String> femaleNames = csvReader.getFemaleNames();
        List<Character> firstLetterOfName = csvReader.getFirstLetter();

        Map<String, Integer> mapOfNamesFrequecy = new HashMap<>();
        Map<String, Integer> mapOfFemaleNamesFrequency = new HashMap<>();
        Map<Character, Integer> mapOfFirstLetterOfNamesFrequency = new HashMap<>();

        for (String string : names) {
            mapOfNamesFrequecy.put(string, Collections.frequency(names, string));
        }
        for (String string : femaleNames) {
            mapOfFemaleNamesFrequency.put(string, Collections.frequency(femaleNames, string));
        }

        for (Character c : firstLetterOfName) {
            mapOfFirstLetterOfNamesFrequency.put(c, Collections.frequency(firstLetterOfName, c));
        }

        for (int i = 0; i < 10; i++) {
            Optional<Map.Entry<String, Integer>> maxEntry = mapOfNamesFrequecy.entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue));
            System.out.println("Najczęstsze imie: " + maxEntry.get().getKey() + " " + maxEntry.get().getValue());
            mapOfNamesFrequecy.remove(maxEntry.get().getKey(), maxEntry.get().getValue());
        }

        System.out.println("A teraz żeńskie: ");

        Optional<Map.Entry<String, Integer>> maxEntry = mapOfFemaleNamesFrequency.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        System.out.println("Najczęstsze imie żeńskie: " + maxEntry.get().getKey() + " " + maxEntry.get().getValue());

        System.out.println("A teraz litery: ");
        for (int i = 0; i < 3; i++) {
            Optional<Map.Entry<Character, Integer>> maxEntryLetter = mapOfFirstLetterOfNamesFrequency.entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue));
            System.out.println("Najczęstsze imie: " + maxEntryLetter.get().getKey() + " " + maxEntryLetter.get().getValue());
            mapOfFirstLetterOfNamesFrequency.remove(maxEntryLetter.get().getKey(), maxEntryLetter.get().getValue());
        }
    }
}
