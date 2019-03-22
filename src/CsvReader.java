import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CsvReader {
    private List<List<String>> records = new ArrayList<>();

    CsvReader() {
        try {
            csvRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csvRead() throws IOException {

        URL url = new URL("https://data.cityofnewyork.us/api/views/25th-nujf/rows.csv?accessType=DOWNLOAD");
        URLConnection connection = url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());

        try (BufferedReader br = new BufferedReader(input)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (List<String> rec : records) {
            names.add(rec.get(3));
        }
        return names;
    }

    List<String> getFemaleNames() {
        List<String> femaleNames = new ArrayList<>();
        for (List<String> rec : records) {
            if (rec.get(1).equals("FEMALE")) {
                femaleNames.add(rec.get(3));
            }
        }
        return femaleNames;
    }

    List<Character> getFirstLetter() {
        List<Character> firstCharacterOfName = new ArrayList<>();
        for (List<String> rec : records) {
            firstCharacterOfName.add(rec.get(3).charAt(0));
        }
        return firstCharacterOfName;
    }
}
