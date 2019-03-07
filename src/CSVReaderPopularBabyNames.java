import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVReaderPopularBabyNames {
    public static void main(String... args) {
        List<Names> names = readPopularBabyNamesFromCSV("src/Popular_Baby_Names.csv");

        //10 najpopularniejszych imion
        Map<String, Long> tenMostPopularName = names.stream()
                .collect(Collectors.groupingBy(Names::getFirstNameChild, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.print("10 najpopularniejszych imion: ");
        for (Map.Entry<String, Long> entry : tenMostPopularName.entrySet()) {
            System.out.print(entry.getKey() + ", ");
        }

        //najpopularniejsze imię żeńskie
        String mostPopularWomanName = names.stream().
                filter(name -> name.getGender()
                        .contains("FEMALE"))
                .collect(Collectors.groupingBy(Names::getFirstNameChild, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();
        System.out.println("\nNajpopularniejsze imię żeńskie: " + mostPopularWomanName);

        //imiona rozpoczynające się od jakich liter są najpopularniejsze. Wyświetl 3 najpopularniejsze litery wraz z
        //ilością imion rozpoczynających się daną literą
        StringBuilder firstLettersOfNames = new StringBuilder();
        for (Names name : names) {
            firstLettersOfNames.append(name.getFirstNameChild().charAt(0));
        }

        Map<Character, Long> characterFrequency = firstLettersOfNames.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("Trzy najpopularniejsze pierwsze litery imion wraz z ilością imion rozpoczynających się " +
                "daną literą: ");
        for (Map.Entry<Character, Long> entry : characterFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static List<Names> readPopularBabyNamesFromCSV(String fileName) {
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
        }
        return informationAboutBabyToList;
    }

    private static Names createInformationAboutBaby(String[] metadata) {
        String yearOfBirth = metadata[0].toUpperCase();
        String gender = metadata[1].toUpperCase();
        String firstNameChild = metadata[3].toUpperCase();
        String count = metadata[4].toUpperCase();
        String rank = metadata[5].toUpperCase();

        return new Names(yearOfBirth, gender, firstNameChild, count, rank);
    }
}

