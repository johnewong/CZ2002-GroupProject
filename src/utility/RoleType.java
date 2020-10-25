package utility;

public enum RoleType {
    Student(0), Admin(1);

    private int number;

    public int toInt() {
        return this.number;
    }

    private RoleType(int number) {
        this.number = number;
    }

//    REJECTED(0), REGISTERED(1), INWAITLIST(2);
//
//    private int number;
//
//    public int toInt(){
//        return this.number;
//    }
//
//    private StatusEnum(int number){
//        this.number=number;
//    }
}
