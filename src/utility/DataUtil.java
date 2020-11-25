/**
 Utility class to provide utility method for data manipulation
 Contains read file, write file, encrpty password

 @author Weng Yifei, Huang Xiao Yan
 @version 1.0
 @since Nov-2020
 */


package utility;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class DataUtil {
    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Method to load a file
     *
     * @param filePath
     * @return file content in string
     */
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

    /**
     * Method to wirte a file. Convert object list to string
     * Using reflection
     *
     * @param path
     * @param list data in list
     */
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
                    if(fields[i].get(obj).getClass() == Date.class){
                        row +=  DATE_FORMATTER.format(fields[i].get(obj));
                    }else {
                        row += fields[i].get(obj);
                    }

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

    /**
     * Method to set the value of a object
     * Using reflection
     *
     * @param obj  object to be assigned values
     * @param header data header
     * @param row  data
     */
    public static void setObject(Object obj, String header, String row) {

        String[] columnNames = header.split(",");
        String[] columnValues = row.split(",");
        try {
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i].trim();
                Field f = obj.getClass().getField(columnName); // java reflection
                if (f.getType() == int.class) {
                    f.set(obj, Integer.parseInt(columnValues[i]));
                } else if (f.getType() == boolean.class) {
                    f.set(obj, Boolean.parseBoolean(columnValues[i]));
                } else if (f.getType() == float.class) {
                    f.set(obj, Float.parseFloat(columnValues[i]));
                }  else if(f.getType() == Date.class){
                    Date date = DATE_FORMATTER.parse(columnValues[i]);
                    f.set(obj, date);
                }

                else
                    f.set(obj, columnValues[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to encrypt password
     * Using SHA-256 algorithm to encrypt
     *
     * @param password  raw password
     * @return encrypted password
     */
    public static String encryptPassword(String password) {
        try {
            // Create MessageDigest instance for Sha-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes()); //Add password bytes to digest
            //get the hash bytes
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            // get complete hash password
            password = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

}


