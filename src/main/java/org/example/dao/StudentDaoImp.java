package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class StudentDaoImp implements StudentDao{


    @Override
    public void update(Student student) throws SQLException {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            String query = "UPDATE students SET name = ?, phone = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getPhone());
                preparedStatement.setInt(3, student.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Student updated successfully.");
                } else {
                    System.out.println("Failed to update student.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }


    @Override
    public boolean existsById(int id) throws SQLException {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            throw new SQLException("Failed to establish database connection.");
        }

        try {
            String query = "SELECT COUNT(*) FROM students WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }

        return false;
    }


    private boolean executeQuery(Function<Connection, Boolean> queryFunction) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return false;
        }

        try {
            return queryFunction.apply(con);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }



    @Override
    public void save(Student student) throws SQLException {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            if (student.getId() > 0) {
                // Check if student with this ID already exists
                String checkQuery = "SELECT COUNT(*) FROM students WHERE id = ?";
                try (PreparedStatement checkStatement = con.prepareStatement(checkQuery)) {
                    checkStatement.setInt(1, student.getId());
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        resultSet.next();
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            // Student exists, update the record
                            String updateQuery = "UPDATE students SET name = ?, phone = ? WHERE id = ?";
                            try (PreparedStatement updateStatement = con.prepareStatement(updateQuery)) {
                                updateStatement.setString(1, student.getName());
                                updateStatement.setString(2, student.getPhone());
                                updateStatement.setInt(3, student.getId());

                                int rowsAffected = updateStatement.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Student updated successfully.");
                                } else {
                                    System.out.println("Failed to update student.");
                                }
                            }
                            return; // Exit the method
                        }
                    }
                }
            }

            // If student doesn't exist or ID is not set, insert a new record
            String insertQuery = "INSERT INTO students (id, name, phone) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = con.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, student.getId());
                insertStatement.setString(2, student.getName());
                insertStatement.setString(3, student.getPhone());

                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Student inserted successfully.");
                } else {
                    System.out.println("Failed to insert student.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }



    @Override
    public void deleteById(int id) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            String query = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Student deleted successfully.");
                } else {
                    System.out.println("No student found with ID " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

}
