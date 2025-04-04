package ca.ucalcary.cpsc.groupprojectgui.compartors;

import ca.ucalcary.cpsc.groupprojectgui.objects.School;

import java.util.Comparator;

public class SchoolNameComparator implements Comparator<School> {

    /**
     * Compares two School objects by their names.
     *
     * @param o1 the first School object to be compared.
     * @param o2 the second School object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(School o1, School o2) {
        // Compare the two schools by their names.
        int comp = o1.getName().compareTo(o2.getName());

        // If the names are not equal, return the comparison result
        if (comp !=0){
            return comp;
        }
        // If the names are equal, compare the schools by their natural ordering.
        return o1.compareTo(o2);
    }
}
