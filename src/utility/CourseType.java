/**
 Class type enum

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */


package utility;

public enum CourseType {

    Core(0, "Core"), GERCore(1, "GERCore"), CoreElective(2, "CoreElective"), UE(3, "UE");

    private int number;
    private String value;

    public int toInt() {
        return this.number;
    }

    private CourseType(int number, String value) {
        this.number = number;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    public static String getValue(int number){
        if(number == Core.number)
            return Core.value;
        if(number == GERCore.number)
            return GERCore.value;
        if(number == CoreElective.number)
            return CoreElective.value;
        if(number == UE.number)
            return UE.value;

        return "Not found";
    }
}
