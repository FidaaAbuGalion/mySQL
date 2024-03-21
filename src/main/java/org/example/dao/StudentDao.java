package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {

     void update(Student student) throws SQLException;
     boolean existsById(int id) throws SQLException;
     void save(Student student) throws SQLException;
     void deleteById(int id);
}
