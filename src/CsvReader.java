import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CsvReader {
    private URL urlWithSourceFile;

    private List<List<String>> recordsFromCsv = new ArrayList<>();

    {
        try {
            urlWithSourceFile =
                    new URL("https://data.cityofnewyork.us/api/views/25th-nujf/rows.csv?accessType=DOWNLOAD");
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    CsvReader() {
        try {
            csvRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csvRead() throws IOException {

        URLConnection connection = urlWithSourceFile.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());

        try (BufferedReader br = new BufferedReader(input)) {
            String lineOfCsvFile;
            while ((lineOfCsvFile = br.readLine()) != null) {
                String[] values = lineOfCsvFile.split(",");
                recordsFromCsv.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (List<String> rec : recordsFromCsv) {
            names.add(rec.get(3));
        }
        return names;
    }

    List<String> getFemaleNames() {
        List<String> femaleNames = new ArrayList<>();
        for (List<String> rec : recordsFromCsv) {
            if (rec.get(1).equals("FEMALE")) {
                femaleNames.add(rec.get(3));
            }
        }
        return femaleNames;
    }

    List<Character> getFirstLetter() {
        List<Character> firstCharacterOfName = new ArrayList<>();
        for (List<String> rec : recordsFromCsv) {
            firstCharacterOfName.add(rec.get(3).charAt(0));
        }
        return firstCharacterOfName;
    }
}
