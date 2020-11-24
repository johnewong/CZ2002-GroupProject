package utility;

public enum ClassType {

    Lecture(1, "Lecture"), Tutorial(2, "Tutorial"), Lab(3, "Lab");

    private int number;
    private String value;

    public int toInt() {
        return this.number;
    }

    private ClassType(int number, String value) {
        this.number = number;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    public static String getValue(int number){
        if(number == Lecture.number)
            return Lecture.value;
        if(number == Tutorial.number)
            return Tutorial.value;
        if(number == Lab.number)
            return Lab.value;

        return "Not found";
    }
}
