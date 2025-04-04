package ca.ucalcary.cpsc.groupprojectgui.objects;

import java.util.*;

/**
 * Represents a school that can enroll students. Each school has a unique ID, a name, and is located in a specific province.
 * Schools can be compared based on their IDs to determine their natural ordering.
 *
 */
public class School implements Comparable<School>{
    private static final String SCHOOL_FORMAT = "%n%-30s %-4s %-20s%n"; // Format string for toString method
    private int id; // Unique identifier
    private String name;
    private String province;
    private final ArrayList<Student> students; // List to keep track of enrolled students

    // Constructor
    /**
     * Constructs a new School instance with specified id, name, and province. Initializes an empty list of students.
     *
     * @param id the unique identifier for the school
     * @param name the name of the school
     * @param province the province where the school is located
     *
     */
    public School(int id, String name, String province) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.students = new ArrayList<>();
    }

    // Method to add a student to the school
    /**
     * Adds a student to the school if they are not already enrolled.
     *
     * @param student the student to add
     *
     */
    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    // Getters and Setters
    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        // Use the format string to construct the student's information string
        return String.format(SCHOOL_FORMAT, name, id, province);
    }
    @Override
    public int compareTo(School other) {
        // Sort by ID
        return Integer.compare(this.id, other.id);
    }
}
