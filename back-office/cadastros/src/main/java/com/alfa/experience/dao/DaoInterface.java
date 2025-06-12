package com.alfa.experience.dao;

import java.util.List;

public interface DaoInterface {
    boolean insert(Object entity);
    boolean update(Object entity);
    boolean delete(Long pk);
    List<Object> select(Long pk);
    List<Object> selectAll();
}
