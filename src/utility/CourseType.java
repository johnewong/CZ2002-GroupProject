package utility;

public enum CourseType {

    Core(0), GERCore(1), CoreElective(2), UE(3), NA(4);

    private int number;

    public int toInt() {
        return this.number;
    }

    private CourseType(int number) {
        this.number = number;
    }
}
