package page;

import model.User;

import java.text.SimpleDateFormat;

public abstract class Page {
    protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    protected abstract void showPage();

}
