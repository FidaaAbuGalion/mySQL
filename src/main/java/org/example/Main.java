package org.example;

import org.example.dao.DBConnection;
import org.example.dao.Student;
import org.example.dao.StudentDao;
import org.example.dao.StudentDaoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        StudentDao studentDao = new StudentDaoImp();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add a new student");
            System.out.println("2. Check if a student exists");
            System.out.println("3. Delete a student");
            System.out.println("4. Update student details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add a new student
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter student name: ");
                    String name = scanner.next();
                    System.out.print("Enter student phone: ");
                    String phone = scanner.next();
                    studentDao.save(new Student(id, name, phone));
                    break;
                case 2:
                    // Check if a student exists
                    System.out.print("Enter student ID to check: ");
                    int idToCheck = scanner.nextInt();
                    if (studentDao.existsById(idToCheck)) {
                        System.out.println("Student with ID " + idToCheck + " exists.");
                    } else {
                        System.out.println("Student with ID " + idToCheck + " does not exist.");
                    }
                    break;
                case 3:
                    // Delete a student
                    System.out.print("Enter student ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    studentDao.deleteById(idToDelete);
                    break;
                case 4:
                    // Update student details
                    System.out.print("Enter student ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    System.out.print("Enter new name: ");
                    String newName = scanner.next();
                    System.out.print("Enter new phone: ");
                    String newPhone = scanner.next();
                    studentDao.update(new Student(idToUpdate, newName, newPhone));
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }
}


