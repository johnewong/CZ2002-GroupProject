package utility;

public enum GenderType {
    Male(0), Female(1), Others(2);

    private int number;

    public int toInt(){
        return this.number;
    }

    private GenderType(int number){
        this.number=number;
    }
}
