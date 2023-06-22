package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TravelData {
    private final List<DataRecord> dataRecords;

    public TravelData(File dataDir) {
        File[] files = dataDir.listFiles();
        dataRecords = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    br.lines().map(line -> line.split("\t")).filter(parts -> parts.length == 7).forEach(parts -> {
                        try {
                            dataRecords.add(new DataRecord(parts[0],
                                    parts[1],
                                    sdf.parse(parts[2]),
                                    sdf.parse(parts[3]),
                                    parts[4],
                                    parts[5],
                                    parts[6]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String localizedCountry(String country, Locale orig, Locale dest) {
        for (Locale l : Locale.getAvailableLocales()) {
            if (l.getDisplayCountry(orig).equals(country)) {
                return l.getDisplayCountry(dest);
            }
        }
        return country;
    }

    public static String localizedLocation(String place, Locale loc) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/locations", loc);
        return resourceBundle.getString(place);
    }

    public static String localizedPrice(BigDecimal value, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return numberFormat.format(value);
    }

    public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        Locale locale = Locale.forLanguageTag(loc.replace("_", "-"));
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return dataRecords
                .stream()
                .map(e -> String.format("%s %s %s %s %s %s",
                        localizedCountry(e.getCountry(), e.getLocale(), locale),
                        sdf.format(e.getDateFrom()),
                        sdf.format(e.getDateTo()),
                        localizedLocation(e.getLocation(), locale),
                        localizedPrice(e.getPrice(), locale),
                        e.getCurrency()))
                .collect(Collectors.toList());
    }

    public List<DataRecord> getDataRecords() {
        return dataRecords;
    }
}