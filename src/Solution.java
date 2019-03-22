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

        Map<String, Integer> mapOfNamesFrequency = new HashMap<>();
        Map<String, Integer> mapOfFemaleNamesFrequency = new HashMap<>();
        Map<Character, Integer> mapOfFirstLetterOfNamesFrequency = new HashMap<>();

        makeMapStringWithFrequency(names, mapOfNamesFrequency);
        makeMapStringWithFrequency(femaleNames, mapOfFemaleNamesFrequency);
        makeMapCharacterWithFrequency(firstLetterOfName, mapOfFirstLetterOfNamesFrequency);

        System.out.println("***** Oto 10 najpopularniejszych imion: *****");
        print_N_NamesOfSolution(mapOfNamesFrequency, 10);
        System.out.println("***** Oto najpopularniejsze imię/imiona żeńskie: ");
        print_N_NamesOfSolution(mapOfFemaleNamesFrequency, 1);
        System.out.println("***** Na te litery imiona zaczynają się najczęściej *****");
        print_N_CharactersOfSolution(mapOfFirstLetterOfNamesFrequency, 3);
    }

    private void print_N_CharactersOfSolution(Map<Character, Integer> mapOfFirstLetterOfNamesFrequency,
            int howManyCharacters) {
        for (int i = 0; i < howManyCharacters; i++) {
            Optional<Map.Entry<Character, Integer>> maxEntryLetter = mapOfFirstLetterOfNamesFrequency.entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue));
            System.out.println(maxEntryLetter.get().getKey() + " " + maxEntryLetter.get().getValue());
            mapOfFirstLetterOfNamesFrequency.remove(maxEntryLetter.get().getKey(), maxEntryLetter.get().getValue());
        }
    }

    private void print_N_NamesOfSolution(Map<String, Integer> mapWithFrequency, int howManyNames) {
        for (int i = 0; i < howManyNames; i++) {
            Optional<Map.Entry<String, Integer>> maxEntry = mapWithFrequency.entrySet()
                    .stream()
                    .max(Comparator.comparing(Map.Entry::getValue));
            System.out.println(maxEntry.get().getKey() + " " + maxEntry.get().getValue());
            mapWithFrequency.remove(maxEntry.get().getKey(), maxEntry.get().getValue());
        }
    }

    private void makeMapCharacterWithFrequency(List<Character> firstLetterOfName,
            Map<Character, Integer> mapOfFirstLetterOfNamesFrequency) {
        for (Character c : firstLetterOfName) {
            mapOfFirstLetterOfNamesFrequency.put(c, Collections.frequency(firstLetterOfName, c));
        }
    }

    private void makeMapStringWithFrequency(List<String> names, Map<String, Integer> mapWithFrequency) {
        for (String string : names) {
            mapWithFrequency.put(string, Collections.frequency(names, string));
        }
    }
}
