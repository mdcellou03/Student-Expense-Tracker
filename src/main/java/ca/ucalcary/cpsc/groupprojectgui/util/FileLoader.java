package ca.ucalcary.cpsc.groupprojectgui.util;

import ca.ucalcary.cpsc.groupprojectgui.Data;
import ca.ucalcary.cpsc.groupprojectgui.objects.School;
import ca.ucalcary.cpsc.groupprojectgui.objects.Student;

import java.io.File;

import java.io.IOException;
import java.util.Scanner;

public class FileLoader {

    /**
     * Loads student and school data from the specified file into a Data object.
     * The specified file consist of a "Students" section followed by a "Schools" section.
     * Each student entry should be in the format: Name,ID,Email
     * Each school entry should be in the format: ID,Name,Email
     *
     * @param file The file to load the data from.
     * @return A Data object populated with the loaded data, or null if an error occurs.
     *
     */
    public static Data load(File file) {
        Data data = new Data();
        try(Scanner scanner = new Scanner(file)){
            String line = scanner.nextLine();

            // Check for the Students section header
            if (!line.equals("Students")){
                System.err.println("could not find students header!");
                return null;
            }
            boolean foundSchools= false;
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                if (line.equals("Schools")){
                    foundSchools = true;
                    break;
                }
                String[] parts = line.split(",");
                if (parts.length!=3){
                    System.err.println("Wrong student entry length!(might be missing or having more parameters of student!)");
                    return null;
                }
                // Create and store new Student object
                String name = parts[0];
                int id = Integer.parseInt(parts[1]);
                String email = parts[2];
                Student student = new Student(name,id,email);
                data.storeNewStudent(student.getName(),student.getId(),student.getEmail());
            }
            // Validate presence of Schools section
            if (!foundSchools){
                System.err.println("could not find schools header!");
                return null;
            }
            // Validate presence of Schools section
            while (scanner.hasNextLine()){
                line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length!=3){
                    System.err.print("Wrong school entry length!(might be missing or having more parameters of school!)");
                    return null;
                }
                // Create and store new School object
                String name = parts[1];
                int id = Integer.parseInt(parts[0]);
                String email = parts[2];
                School school = new School(id,name,email);
                data.storeNewSchool(school);

            }
        }catch (IOException ioe){
            System.err.println("Exception occured while reading file");
            return null;
        }return data;

    }
}
