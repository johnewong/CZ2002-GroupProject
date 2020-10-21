package utility;

public enum StatusEnum {
    REJECTED(0), REGISTERED(1), INWAITLIST(2);

    private int number;

    public int toInt(){
        return this.number;
    }

    private StatusEnum(int number){
        this.number=number;
    }
}
