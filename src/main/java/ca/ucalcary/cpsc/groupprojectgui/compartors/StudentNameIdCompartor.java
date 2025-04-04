package ca.ucalcary.cpsc.groupprojectgui.compartors;

import ca.ucalcary.cpsc.groupprojectgui.objects.Student;

import java.util.Comparator;

public class StudentNameIdCompartor implements Comparator<Student> {


    /**
     * Compares two Student objects by their names and, if their names are identical,
     * by their natural ordering, which typically involves comparing their IDs.
     *
     * @param o1 the first student to be compared.
     * @param o2 the second student to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *         equal to, or greater than the second. Comparison is primarily based on names in
     *         lexicographical order, and secondarily on natural ordering if names are identical.
     */
    @Override
    public int compare(Student o1, Student o2) {
        // compare the names
        int comp = o1.getName().compareTo(o2.getName());

        // If the names are not identical, return the result of the name comparison
        if (comp !=0){
            return comp;
        }
        // If the names are identical, defer to the natural ordering of the students
        return o1.compareTo(o2);
    }
}
