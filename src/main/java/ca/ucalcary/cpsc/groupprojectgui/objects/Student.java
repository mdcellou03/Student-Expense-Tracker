package ca.ucalcary.cpsc.groupprojectgui.objects;

import java.util.*;

/**
 * Represents a student, including personal information and a detailed record of expenses.
 * Students can be compared to one another based on their ID for sorting purposes.
 *
 */
public class Student implements Comparable<Student> {

    // Format string for toString method
    private static final String STUD_FORMAT = "%n%-20s %-8s %-20s%n";

    // Public variables to hold totals for different categories of expenses
    private double groceryTotal = 0.0;
    private double tuitionTotal = 0.0;
    private double rentTotal = 0.0;

    // Personal information
    private String name, email;
    private int id;
    private final ArrayList<Expense>expenses; // list of the student's expenses

    /**
     * Constructs a new Student with the specified name, ID, and email. Initializes an empty list of expenses.
     *
     * @param name  the student's name
     * @param id    the student's ID
     * @param email the student's email address
     *
     */
    public Student(String name, int id, String email){
        this.name=name;
        this.id=id;
        this.email=email;
        this.expenses= new ArrayList<>(); // expenses initialization
    }
    /**
     * Returns a string representation of the student, formatted according to STUD_FORMAT.
     *
     * @return a formatted string containing the student's name, ID, and email
     *
     */
    @Override
    public String toString() {
        // let us utilise the format string to construct the student's information string
        return String.format(STUD_FORMAT, name, id, email);
    }
    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    //////////////////////////////////////////////////////////////////////
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // CompareTo method for sorting, comparing by ID as an example
    /**
     * Compares this student with another student for order. Returns a negative integer,
     * zero, or a positive integer as this student is less than, equal to, or greater than the specified student.
     *
     * @param other the student to be compared
     * @return a negative integer, zero, or a positive integer as this student
     *         is less than, equal to, or greater than the specified student
     *
     */
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }

    // Equals and hashCode based on student ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    /////////////////////////////////////////////////////////////////////
    // GroupProject.objects.Expense managing methods

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
    /// adding expenses
    /**
     * Adds an expense to the student's record and updates the total for the specific expense category.
     *
     * @param expense the expense to add
     *
     */
    public void addExpenses(Expense expense) {
        this.expenses.add(expense);
        // Update the total for the specific expense category
        if (expense instanceof GroceryExpense) {
            this.groceryTotal += expense.netAmount();
        } else if (expense instanceof TuitionExpense) {
            this.tuitionTotal += expense.netAmount();
        } else if (expense instanceof RentExpense) {
            this.rentTotal += expense.netAmount();
        }
    }
    // Getters for each expense category total
    public double getGroceryTotal() {
        return this.groceryTotal;
    }

    protected double getTuitionTotal() {
        return this.tuitionTotal;
    }

    protected double getRentTotal() {
        return this.rentTotal;
    }

    // Method to get the total of all expenses
    /**
     * Calculates the total of all expenses.
     *
     * @return the total amount of grocery, tuition, and rent expenses
     *
     */
    public double getTotalExpenses() {
        return this.groceryTotal + this.tuitionTotal + this.rentTotal;
    }

}
