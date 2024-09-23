package Back_end.BW2;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class esempioCSV {

    private static final String COMUNI_CSV_FILE_PATH = "src/comuni-italiani.csv";
    private static final String PROVINCE_CSV_FILE_PATH = "src/province-italiane.csv";
    private static final String ENV_FILE_PATH = "env.properties";

    public static void main(String[] args) {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(ENV_FILE_PATH)) {
            props.load(reader);
        } catch (IOException e) {
            System.out.println("Errore nel caricamento del file: " + e.getMessage());
            return;
        }

        String dbUrl = props.getProperty("PG_URL");
        String dbUser = props.getProperty("PG_USERNAME");
        String dbPass = props.getProperty("PG_PW");

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            createTables(conn);
            importComuni(conn);
            importProvince(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        String createComuniSQL = "CREATE TABLE IF NOT EXISTS comuni (" +
                "codice_provincia VARCHAR(10), " +
                "progressivo_comune VARCHAR(10), " +
                "denominazione_italiano VARCHAR(100), " +
                "provincia VARCHAR(100))";

        String createProvinceSQL = "CREATE TABLE IF NOT EXISTS province (" +
                "codice_provincia VARCHAR(10), " +
                "nome_provincia VARCHAR(100), " +
                "regione VARCHAR(100))";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createComuniSQL);
            stmt.executeUpdate(createProvinceSQL);
            System.out.println("Tabelle create.");
        }
    }

    private static void importComuni(Connection conn) {
        String line;
        String csvSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(COMUNI_CSV_FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                String[] comune = line.split(csvSplitBy);
                if (comune.length >= 4) {
                    String codiceProvincia = comune[0];
                    String progressivoComune = comune[1];
                    String denominazione = comune[2];
                    String provincia = comune[3];

                    inserisciComune(conn, codiceProvincia, progressivoComune, denominazione, provincia);
                    System.out.println("Comune inserito: " + denominazione);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserisciComune(Connection conn, String codiceProvincia, String progressivoComune,
                                        String denominazione, String provincia) throws SQLException {
        String insertSQL = "INSERT INTO comuni (codice_provincia, progressivo_comune, denominazione_italiano, provincia) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, codiceProvincia);
            pstmt.setString(2, progressivoComune);
            pstmt.setString(3, denominazione);
            pstmt.setString(4, provincia);
            pstmt.executeUpdate();
        }
    }

    private static void importProvince(Connection conn) {
        String line;
        String csvSplitBy = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(PROVINCE_CSV_FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                String[] provincia = line.split(csvSplitBy);
                if (provincia.length >= 3) {
                    String codiceProvincia = provincia[0];
                    String nomeProvincia = provincia[1];
                    String regione = provincia[2];

                    inserisciProvincia(conn, codiceProvincia, nomeProvincia, regione);
                    System.out.println("Provincia inserita: " + nomeProvincia);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserisciProvincia(Connection conn, String codiceProvincia, String nomeProvincia,
                                           String regione) throws SQLException {
        String insertSQL = "INSERT INTO province (codice_provincia, nome_provincia, regione) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, codiceProvincia);
            pstmt.setString(2, nomeProvincia);
            pstmt.setString(3, regione);
            pstmt.executeUpdate();
        }
    }
}
