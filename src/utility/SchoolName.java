package utility;

public enum SchoolName {

    EEE(0), MAE(1), SCSE(2), MSE(3);

    private int number;

    public int toInt() {
        return this.number;
    }

    private SchoolName(int number) {
        this.number = number;
    }
}
