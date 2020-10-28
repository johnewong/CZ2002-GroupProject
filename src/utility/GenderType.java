package utility;

public enum GenderType {
    REJECTED(0), REGISTERED(1), INWAITLIST(2);

    private int number;

    public int toInt(){
        return this.number;
    }

    private GenderType(int number){
        this.number=number;
    }
}
