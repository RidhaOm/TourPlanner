package com.example.tourplanner.DAL.DAO;

import java.util.List;
import java.util.Optional;

public interface dao<T>{
    Optional<T> get(int id);
    List<T> getAll();
    T create(T t);
    void update(T t);
    void delete(T t);

    void addTourLog(T t);
}
