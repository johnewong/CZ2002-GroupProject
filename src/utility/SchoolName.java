package utility;

public enum SchoolName {

    EEE(0, "EEE"), MAE(1, "MAE"), SCSE(2, "SCSE"), MSE(3, "MSE");

    private int number;
    private String value;

    public int toInt() {
        return this.number;
    }

    private SchoolName(int number, String value) {

        this.number = number;
        this.value = value;
    }
    public String toString(){ return value;}
    public static String getValue(int number){
        if(number == EEE.number)
            return EEE.value;
        if(number == MAE.number)
            return MAE.value;
        if(number == SCSE.number)
            return SCSE.value;
        if(number == MSE.number)
            return MSE.value;

        return "Not found";
    }
}

