package Back_end.BW2;

import java.io.*;

public class esempioCSV {

    public static void main(String[] args) {
        String file = "src/province-italiane.csv";
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                for (String index : row) {
                    if (index.equals("#RIF!")) {
                        index = "N/A";
                    }
                    System.out.printf("%-30s", index);
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    System.out.println("Error closing the reader.");
                    ex.printStackTrace();
                }
            }
        }
    }
}
