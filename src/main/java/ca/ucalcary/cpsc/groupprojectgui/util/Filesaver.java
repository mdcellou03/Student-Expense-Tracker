package ca.ucalcary.cpsc.groupprojectgui.util;

import ca.ucalcary.cpsc.groupprojectgui.Data;
import ca.ucalcary.cpsc.groupprojectgui.objects.School;
import ca.ucalcary.cpsc.groupprojectgui.objects.Student;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Filesaver {

    /**
     * Saves the students and schools data to a specified file.
     * This method first writes a list of students, sorted by their natural ordering,
     * to the file with their name, ID, and email. After writing all the students, it writes
     * a list of schools, also sorted by their natural ordering, with their ID, name, and province.
     * Each record is written in a new line, and sections are separated by headers "Students" and "Schools".
     *
     * @param file the file to which the data will be saved
     * @param data the Data object containing the lists of Students and Schools
     * @return true if the data was successfully saved to the file, false otherwise
     */
    public static boolean save(File file, Data data) {
        try (FileWriter fw = new FileWriter(file)){

            // Write the header for the student section
            fw.write("Students\n");

            // Retrieve and sort the list of students
            ArrayList<Student> students =data.getAllStudents();
            Collections.sort(students);

            // Write each student's data to the file
            for (Student student:students){
                fw.write(String.format("%s,%s,%s\n",student.getName(),student.getId(),student.getEmail()));
            }
            // Ensure all student data is written to the file before proceeding
            fw.flush();

            // Write the header for the school section
            fw.write("Schools\n");

            // Retrieve and sort the list of schools
            ArrayList<School> schools = data.schoolList();
            Collections.sort(schools);

            // Write each school's data to the file
            for (School school:schools){
                fw.write(String.format("%s,%S,%s\n",school.getId(),school.getName(),school.getProvince()));
            }
            // Ensure all school data is written to the file
            fw.flush();

            // success
            return true;
        }catch (IOException e){
            // failure
            return false;
        }
    }
}
