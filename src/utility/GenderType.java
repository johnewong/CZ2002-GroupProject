package utility;

public enum GenderType {
    Male(0, "Male"), Female(1, "Female"), Others(2, "Others");

    private int number;
    private String value;

    public int toInt(){
        return this.number;
    }

    private GenderType(int number, String value){

        this.number=number;
        this.value=value;
    }

    public String toString() {
        return value;
    }
    public static String getValue(int number){
        if(number == Male.number)
            return Male.value;
        if(number == Female.number)
            return Female.value;
        if(number == Others.number)
            return Others.value;

        return "Not found";
    }
}
