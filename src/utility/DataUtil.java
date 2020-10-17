package utility;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;

// provide static utility methods for all classes
public class DataUtil {

    public static String loadFile(String filePath) {
        StringBuilder sb = null;
        RandomAccessFile rFile = null;

        try {
            if (new File(filePath).exists()) {
                sb = new StringBuilder();
                rFile = new RandomAccessFile(filePath, "rw");
                String line = null;
                while (null != (line = rFile.readLine())) {
                    sb.append(line);
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

    public static void writeFile(ArrayList<?> list, String path) {
        StringBuilder sb = new StringBuilder();
        String header = "";
        for (int rowCount = 0; rowCount < list.size(); rowCount++) {
            String row = "";

            Field[] fields = list.get(rowCount).getClass().getFields();
            Object obj = list.get(rowCount);

            try {
                for (int i = 0; i < fields.length; i++) {
                    if (rowCount == 0) {
                        header += fields[i].getName();
                        if (i < fields.length - 1)
                            header += ",";
                    }
                    fields[i].setAccessible(true);
                    row += fields[i].get(obj);

                    if (i < fields.length - 1)
                        row += ",";
                }
                sb.append(row + ";\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.insert(0, header + ";\n");

        try {
            FileWriter writer = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(writer);

            bw.write(sb.toString());
            bw.close();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void setObject(Object obj, String header, String row) {
//        if(row.isEmpty())
//            return;

        String[] columnNames = header.split(",");
        String[] columnValues = row.split(",");
        try {
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].trim();
                Field f = obj.getClass().getField(columnName);
                if (f.getType() == int.class) {
                    f.set(obj, Integer.parseInt(columnValues[i]));
                } else if (f.getType() == boolean.class) {
                    f.set(obj, Boolean.parseBoolean(columnValues[i]));
                } else if (f.getType() == float.class) {
                    f.set(obj, Float.parseFloat(columnValues[i]));
                } else
                    f.set(obj, columnValues[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String encryptPassword() {
        return null;
    }

}
