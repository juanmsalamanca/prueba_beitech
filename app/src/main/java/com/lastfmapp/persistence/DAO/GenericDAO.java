package com.lastfmapp.persistence.DAO;

import java.util.List;

public abstract class GenericDAO<T> {


    public abstract boolean insert(T t);
    public abstract List<T> findAll();
    public abstract List<T> findByName(String s);



}
