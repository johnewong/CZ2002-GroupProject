package utility;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;

// provide static utility methods for all classes
public class DataUtil {

    public static String loadFile(String filePath) {
        StringBuilder sb = null;
        RandomAccessFile rFile = null;

        try {
            File a = new File(filePath);
            if (new File(filePath).exists()) {
                sb = new StringBuilder();
                rFile = new RandomAccessFile(filePath, "rw");
                String line = null;
                while (null != (line = rFile.readLine())) {
                    sb.append(line).append(";");
                }
                rFile.close();
            } else {
                throw new Exception(String.format("File: %s is not found", filePath));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return sb.toString();
    }

    public static void setObjectList(){

    }

    public static void setObject(Object obj, String headers, String row) {

        String[] columnNames = headers.split(",");
        String[] columnValues = row.split(",");

        try {
            for (int i = 0; i < columnNames.length; i++) {
                String columnName =  columnNames[i].trim();
                Field f = obj.getClass().getField(columnName);
                if (f.getType() == int.class) {
                    f.set(obj, Integer.parseInt(columnValues[i]));
                }
                else if (f.getType() == boolean.class) {
                    f.set(obj, Boolean.parseBoolean(columnValues[i]));
                }
                else if (f.getType() == float.class) {
                    f.set(obj, Float.parseFloat(columnValues[i]));
                }
                else
                    f.set(obj, columnValues[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String EncryptPassword() {
        return null;
    }

}
