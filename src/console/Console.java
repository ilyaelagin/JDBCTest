package console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Console {
	protected static final String NAME_PATTERN = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?";
    protected static final String DATE_PATTERN = "yyyy-MM-dd";
    protected static final String INT_PATTERN = "-?\\d+";
    protected static final String NO_PATTERN = "no";
    protected static final String YES_PATTERN = "yes";

    protected static boolean isValidFormat(String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
                System.out.println("Некорректная дата. Формат даты: 2020-12-31");
            }
        } catch (ParseException ex) {
            System.out.println("Ошибка. Формат даты: 2020-12-31");
        }
        return date != null;
    }

    /**
     * Всех наследников этого класса обязуем реализовывать метод operate()
     */
    protected abstract void operate();

}
