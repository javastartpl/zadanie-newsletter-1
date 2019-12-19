import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVReaderPopularBabyNames {
    public static void main(String... args) throws ExceptionReadFile {
        List<Names> names = readPopularBabyNamesFromCSV("src/Popular_Baby_Names.csv");

        showMostPopularNames(names, 10);
        showMostPopularFemaleName(names);
        showMostPopularLettersAlongWithNumberOfNamesBeginningWithTheFirstLetter(names, 3);
    }

    private static void showMostPopularLettersAlongWithNumberOfNamesBeginningWithTheFirstLetter(List<Names> names,
                                                                                                int limit) {
        StringBuilder firstLettersOfNames = new StringBuilder();
        Set<Names> namesUnique = new HashSet<>(names);
        for (Names name : namesUnique) {
            firstLettersOfNames.append(name.getFirstNameChild().charAt(0));
        }

        Map<Character, Long> characterFrequency = getEntriesByCharacters(firstLettersOfNames)
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        printMostPopularLettersAlongWithNumberOfNamesBeginningWithTheGivenLetter(limit, characterFrequency);
    }

    private static Set<Map.Entry<Character, Long>> getEntriesByCharacters(StringBuilder firstLettersOfNames) {
        return firstLettersOfNames.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet();
    }

    private static Set<Map.Entry<String, Integer>> getEntriesByFemaleNames(List<Names> names) {
        return names.stream().
                filter(name -> name.getGender()
                        .contains("FEMALE"))
                .collect(Collectors.groupingBy(Names::getFirstNameChild, Collectors.summingInt(Names::getCount)))
                .entrySet();
    }

    private static void printMostPopularLettersAlongWithNumberOfNamesBeginningWithTheGivenLetter(int limit, Map<Character, Long> characterFrequency) {
        System.out.println(limit + " najpopularniejsze pierwsze litery imion wraz z ilością imion rozpoczynających " +
                "się daną literą: ");
        for (Map.Entry<Character, Long> entry : characterFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void printMostPopularFemaleName(String mostPopularWomanName) {
        System.out.println("\nNajpopularniejsze imię żeńskie: " + mostPopularWomanName);
    }

    private static void printMostPopularNames(int limit, Map<String, Long> tenMostPopularName) {
        System.out.print(limit + " najpopularniejszych imion: ");
        for (Map.Entry<String, Long> entry : tenMostPopularName.entrySet()) {
            System.out.print(entry.getKey() + ", ");
        }
    }

    private static void showMostPopularFemaleName(List<Names> names) {
        String mostPopularWomanName = getEntriesByFemaleNames(names)
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();
        printMostPopularFemaleName(mostPopularWomanName);
    }

    private static void showMostPopularNames(List<Names> names, int limit) {
        Map<String, Long> tenMostPopularName = getEntriesByFirstName(names)
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        printMostPopularNames(limit, tenMostPopularName);
    }

    private static Set<Map.Entry<String, Long>> getEntriesByFirstName(List<Names> names) {
        return names.stream()
                .collect(Collectors.groupingBy(Names::getFirstNameChild, Collectors.summingLong(Names::getCount)))
                .entrySet();
    }

    private static List<Names> readPopularBabyNamesFromCSV(String fileName) throws ExceptionReadFile {
        List<Names> informationAboutBabyToList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] informationAboutBaby = line.split(",");
                Names name = createInformationAboutBaby(informationAboutBaby);
                informationAboutBabyToList.add(name);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionReadFile("Problem z odczytem pliku");
        }
        return informationAboutBabyToList;
    }

    private static Names createInformationAboutBaby(String[] metadata) {
        String gender = metadata[1].toUpperCase();
        String firstNameChild = metadata[3].toUpperCase();
        Integer count = Integer.valueOf(metadata[4]);

        return new Names(gender, firstNameChild, count);
    }
}

