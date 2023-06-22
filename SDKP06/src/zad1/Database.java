package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Database {
    private final String url;
    private final TravelData travelData;
    private JTable table;
    private JButton languageButton;

    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }

    public void create() {
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            String createTableQuery = "CREATE TABLE offers (" +
                    "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
                    "localization VARCHAR(5)," +
                    "country VARCHAR(30)," +
                    "start_date DATE," +
                    "end_date DATE," +
                    "place VARCHAR(15)," +
                    "price REAL," +
                    "currency VARCHAR(5))";

            try {
                statement.executeUpdate(createTableQuery);
            } catch (SQLTransactionRollbackException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    String dropTableQuery = "DROP TABLE offers";
                    statement.executeUpdate(dropTableQuery);
                    statement.executeUpdate(createTableQuery);
                } else
                    throw new SQLException(e);
            }

            String insertQuery = "INSERT INTO offers (localization, country, start_date, end_date, place, price, currency) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (DataRecord dr : travelData.getDataRecords()) {
                preparedStatement.setString(1, dr.getLocale().toString());
                preparedStatement.setString(2, dr.getCountry());
                preparedStatement.setDate(3, new Date(dr.getDateFrom().getTime()));
                preparedStatement.setDate(4, new Date(dr.getDateTo().getTime()));
                preparedStatement.setString(5, dr.getLocation());
                preparedStatement.setDouble(6, dr.getPrice().doubleValue());
                preparedStatement.setString(7, dr.getCurrency());

                preparedStatement.executeUpdate();
            }

            statement.close();
        } catch (SQLException ignored) {
        }
    }

    public void showGui() {
        JFrame frame = new JFrame("Travel Offers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Kraj");
        model.addColumn("Data od");
        model.addColumn("Data do");
        model.addColumn("Lokalizacja");
        model.addColumn("Cena");
        model.addColumn("Waluta");

        Locale locale = Locale.forLanguageTag("pl-PL");
        fillTable(model, locale);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        languageButton = new JButton("Zmień język");
        buttonPanel.add(languageButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        languageButton.addActionListener(e -> {
            String[] languages = {"pl_PL", "en_GB"};
            String selectedLanguage = (String) JOptionPane.showInputDialog(frame,
                    "Select Language:", "Language Selection", JOptionPane.QUESTION_MESSAGE,
                    null, languages, languages[0]);
            if (selectedLanguage != null) {
                changeLanguage(selectedLanguage);
            }
        });

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void changeLanguage(String loc) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        if (loc.equals("pl_PL"))
            setUItoPL(model);
        else
            setUItoEN(model);
        Locale locale = Locale.forLanguageTag(loc.replace("_", "-"));
        fillTable(model, locale);
    }

    private void setUItoEN(DefaultTableModel model) {
        model.setColumnIdentifiers(new String[]{"Country", "Start Date", "End Date", "Location", "Price", "Currency"});
        languageButton.setText("Change language");
    }

    private void setUItoPL(DefaultTableModel model) {
        model.setColumnIdentifiers(new String[]{"Kraj", "Data od", "Data do", "Lokalizacja", "Cena", "Waluta"});
        languageButton.setText("Zmień język");
    }

    private void fillTable(DefaultTableModel model, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DataRecord dr : travelData.getDataRecords()) {
            model.addRow(new Object[]{
                    TravelData.localizedCountry(dr.getCountry(), dr.getLocale(), locale),
                    sdf.format(dr.getDateFrom()),
                    sdf.format(dr.getDateTo()),
                    TravelData.localizedLocation(dr.getLocation(), locale),
                    TravelData.localizedPrice(dr.getPrice(), locale),
                    dr.getCurrency()
            });
        }
    }
}