package utility;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

// provide static utility methods for all classes
public class DataUtility {

    public static String loadData(String filePath) {
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

    public static String EncryptPassword() {
        return null;
    }

}
