package zad1;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DataRecord {
    private final Locale locale;
    private final String country;
    private final Date dateFrom;
    private final Date dateTo;
    private final String location;
    private final BigDecimal price;
    private final String currency;

    DataRecord(String locale, String country, Date dateFrom, Date dateTo, String location, String price, String currency) {
        this.locale = Locale.forLanguageTag(locale.replace("_", "-"));
        this.country = country;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.location = location;
        this.price = parseBigDecimal(price, this.locale);
        this.currency = currency;
    }

    Locale getLocale() {
        return locale;
    }

    String getCountry() {
        return country;
    }

    Date getDateFrom() {
        return dateFrom;
    }

    Date getDateTo() {
        return dateTo;
    }

    String getLocation() {
        return location;
    }

    BigDecimal getPrice() {
        return price;
    }

    String getCurrency() {
        return currency;
    }


    private static BigDecimal parseBigDecimal(String numberString, Locale locale) {
        NumberFormat numberFormat = DecimalFormat.getInstance(locale);
        try {
            Number parsedNumber = numberFormat.parse(numberString);
            return BigDecimal.valueOf(parsedNumber.doubleValue());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
