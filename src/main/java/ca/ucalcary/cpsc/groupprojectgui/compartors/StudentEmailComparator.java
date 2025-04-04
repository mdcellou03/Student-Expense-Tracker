package ca.ucalcary.cpsc.groupprojectgui.compartors;

import ca.ucalcary.cpsc.groupprojectgui.objects.Student;

import java.util.Comparator;

public class StudentEmailComparator implements Comparator<Student> {

    /**
     * Compares two Student objects by their email addresses.
     * The method uses the getEmail() method of the Student class to retrieve the email addresses
     * of the two students being compared. It then delegates the comparison to the compareTo method
     * of the {@code String} class, which compares the email addresses lexicographically.
     *
     * @param o1 the first student to be compared.
     * @param o2 the second student to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument's email
     *         is less than, equal to, or greater than the second's email.
     */
    @Override
    public int compare(Student o1, Student o2) {
        // Compare the two students' email addresses
        return o1.getEmail().compareTo(o2.getEmail());
    }
}
