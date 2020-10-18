package dao;

import java.util.ArrayList;

public interface IDAO<T> {
    public ArrayList<T> getAll();
    public T get(int id);
    public void add(T item);
    public void update(T item);
    public void delete(int id);
}
