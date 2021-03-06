/**
 IDAO. Interface of all DAO class
 Contains all the necessary methods

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */

package dao;

import java.util.ArrayList;

public interface IDAO<T> {
    public ArrayList<T> getAll();
    public ArrayList<T> getAllValid();
    public T get(int id);
    public void add(T item);
    public void update(T item);
    public void delete(int id);
}
