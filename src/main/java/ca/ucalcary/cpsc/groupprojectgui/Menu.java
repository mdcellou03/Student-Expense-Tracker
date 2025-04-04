/**
 * This project tracks spending of students across different schools
 */
package ca.ucalcary.cpsc.groupprojectgui;
import ca.ucalcary.cpsc.groupprojectgui.compartors.SchoolNameComparator;
import ca.ucalcary.cpsc.groupprojectgui.compartors.StudentNameIdCompartor;
import ca.ucalcary.cpsc.groupprojectgui.objects.ExpenseType;
import ca.ucalcary.cpsc.groupprojectgui.objects.School;
import ca.ucalcary.cpsc.groupprojectgui.objects.Student;
import ca.ucalcary.cpsc.groupprojectgui.util.FileLoader;
import ca.ucalcary.cpsc.groupprojectgui.util.Filesaver;

import java.io.File;
import java.util.*;

public class Menu {
    private  static Data data = new Data();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> menuOptions = new ArrayList<>();

    static {
        menuOptions.add("Exit");
        menuOptions.add("Add a school");
        menuOptions.add("Add a student with a name, email, ID");
        menuOptions.add("Add a student to a school");
        menuOptions.add("Add a Grocery expense");
        menuOptions.add("Add a tuition expense");
        menuOptions.add("Add a rent expense");
        menuOptions.add("Display aLL students");
        menuOptions.add("Display the highest spender");
        menuOptions.add("What is the average spending of students in each school");
        menuOptions.add("Which is the cheapest school");
        menuOptions.add("Print specific student's information!");
        menuOptions.add("Load");
        menuOptions.add("Save");
        menuOptions.add("Print All Schools");
        menuOptions.add("List students by school");


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Static declarations
    //this message will be displayed at the top of our menu
    private static String optMessage = """
            Store and access student expenditures.
            \t Menu Options
            """;

    static {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(optMessage);
        // After adding the message to stringBuilder, this for loop will add the corresponding index of our options(for example: 0. exit)
        for (int i = 0; i < menuOptions.size(); i++) {
            strBuilder.append(String.format("\t %d. %s\n", i, menuOptions.get(i)));
        }
        // The correct format display will then be stored in our options message as a string that can be printed to show our menu in a proper way
        optMessage = strBuilder.toString();
    }

    // final variables for menuPrintAllStudents
    private static final String STUD_FORMAT = "%n%-20s %-8s %-20s%n";
    private static final String STUDENT_HEADER = String.format(STUD_FORMAT, "Name", "ID", "EMAIL");
    private static final String SCHOOL_FORMAT = "%n%-30s %-4s %-20s%n";
    private static final String SCHOOL_HEADER = String.format(SCHOOL_FORMAT, "Name", "ID", "PROVINCE");
    private static String STUD_SEPARATOR = "";
    private static String SCHOOL_SEPARATOR = "";
    static {
        // Separator will be added for the length of our header in order to separate different students
        for (int i = 0; i < SCHOOL_HEADER.length(); i++) {
            SCHOOL_SEPARATOR += "-";
        }
    }

    static {
        // Separator will be added for the length of our header in order to separate different students
        for (int i = 0; i < STUDENT_HEADER.length(); i++) {
            STUD_SEPARATOR += "-";
        }
    }/////////////////////////////////////////////////////////////////////////////////////////////////

    // This function handles empty inputs from the user
    private static String getUserOption() {
        String userOption;
        do {
            userOption = scanner.nextLine().trim();
        } while (userOption.isEmpty()); // Ask for user input until they enter a nonempty input.
        try {// try/catch to make sure program does not crash on wrong input
            Integer.parseInt(userOption);

        }catch (NumberFormatException e){
            System.out.println("Wrong input. Try again");
            userOption = scanner.nextLine().trim();
        }
        return userOption;

    }

    public static void menuLoop(File file) {
        if (file !=null){
            load(file);
        }

        System.out.println(optMessage);
        // Get choice input from user
        String choice = getUserOption();
        int userOption=Integer.parseInt(choice);
        // Loop to get user input until they enter 0 which would mean that they have no further request
        while (userOption != 0 && !choice.isEmpty()) {
            // This is to display the option the user chose.
            if (userOption > 0 && userOption < menuOptions.size()) {
                System.out.printf("Selected option %d. %s%n", userOption, menuOptions.get(userOption));
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
            }
            switch (userOption) {
                case 1 -> menuAddNewSchool();
                case 2 -> menuAddNewStudent();
                case 3 -> menuAddStudentToSchool();
                case 4 -> menuAddGroceryExpense();
                case 5 -> menuAddTuitionExpense();
                case 6 -> menuAddRentExpense();
                case 7 -> menuPrintAllStudents();
                case 8 -> menuHighestSpender();
                case 9 -> menuAverageSpendingBySchool();
                case 10 -> menuMostAffordable_School();
                case 11 -> menuGetStudentInfo();
                case 12 -> load();
                case 13 -> save();
                case 14-> menuPrintAllSchools();
                case 15 -> studentsBySchool();
                default -> System.out.printf("The option %d is invalid!", userOption);
            }
            // pause before printing the menu again

            System.out.println("Press Enter to view the menu again...");
            scanner.nextLine();
            System.out.println(optMessage);
            choice = getUserOption();

            if (!choice.isEmpty()){
                userOption = Integer.parseInt(choice);
            }else {
                System.out.println("Choice cannot be empty. Please try again! ");
                choice=scanner.nextLine();
            }
        }
        System.out.printf("Thanks for using this program. %n Bye!%n");
    }

    private static void save() {
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename");
                filename= scanner.nextLine().trim();
            }while (filename.isEmpty());
            file= new File(filename);
        }while (file.exists()&& !file.canWrite());
        if (Filesaver.save(file,data)){
            System.out.printf("Saved to file %s%n",file);
        }else {
            System.err.printf("Failed to save to file %s%n",file);
        }

    }


    private static void load() {
        String filename;
        File file;
        do {
            do {
                System.out.println("Enter a filename");
                filename= scanner.nextLine().trim();
            }while (filename.isEmpty());
            file= new File(filename);
        }while (!file.exists()|| !file.canRead());
        load(file);
    }
    private static void load(File file){
        Data data= FileLoader.load(file);
        if (data==null){
            System.err.println("Failed to load data from file");
        }else {
            System.out.printf("Loaded data successfully from file %s%n",file);
            Menu.data=data;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
/// These are the adding data functions
    private static void menuAddNewSchool() {
        boolean success = false;
        while (!success) {
            String name = getSchoolName();
            int id = getSchoolId();

            System.out.println("Enter the province for the new school:");
            String province = scanner.nextLine().trim();

            if (!province.isEmpty()) {
                School newSchool = new School(id, name, province);
                success = data.storeNewSchool(newSchool);
            } else {
                System.out.println("Invalid province! Please try again.");
                // Don't set success to true to allow another attempt
            }
        }
    }

    private static void menuAddNewStudent() {
        // we will add a student using a loop control which verifies whether the student given by the user has already been added
        boolean success = false; // this checks if the student has successfully been added(i.e. does not already exist in our data storage.)
        do {
            String name = getName();
            int id = getId();
            String email = getEmail();
            success = data.storeNewStudent(name, id, email);//storeNewStudent is a function that will be implemented in data.java, and it returns a boolean
            if (!success) {
                System.out.println("Student already exists (id or email has already been recorded)! \n Please retry");
            }
        } while (!success);
        System.out.println("Stored a new student!");
    }

    // Method to add grocery expense to a student
    private static void menuAddGroceryExpense() {
        System.out.println("Enter the student's ID:");
        int id = getId();

        // Prompt the user until a valid double value is entered
        double amount = 0.0;
        double discount = 0.0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Enter the amount of the grocery expense:");
                amount = scanner.nextDouble();
                System.out.println("Enter the seasonal discount (0 for none):");
                discount = scanner.nextDouble();
                validInput = true; // Mark the input as valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
        data.addExpense(id,amount,ExpenseType.GROCERY,discount);
        scanner.nextLine(); // Clear the invalid input
    }

    // Method to add tuition expense to a student
    private static void menuAddTuitionExpense() {
        System.out.println("Enter the student's ID:");
        int id = getId();

        // Prompt the user until a valid double value is entered
        double amount = 0.0;
        double scholarshipAmount = 0.0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Enter the amount of the tuition expense:");
                amount = scanner.nextDouble();
                System.out.println("Enter scholarshipAmount(O for none):");
                scholarshipAmount= scanner.nextDouble();
                validInput = true; // Mark the input as valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Add the tuition expense to the specified student
        data.addExpense(id, amount, ExpenseType.TUITION,scholarshipAmount);
        scanner.nextLine(); // Clear the invalid input
    }

    // Method to add rent expense to a student
    private static void menuAddRentExpense() {
        int id = getId();

        // Prompt the user until a valid double value is entered
        double amount = 0.0;
        double utilities= 0.0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Enter the amount of the rent expense:");
                amount = scanner.nextDouble();
                System.out.println("Enter the cost of utilities:");
                utilities= scanner.nextDouble();
                validInput = true; // Mark the input as valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        // Add the rent expense to the specified student
        data.addExpense(id, amount,ExpenseType.RENT,utilities);
        scanner.nextLine(); // Clear the invalid input
    }

    // Method to add a student to a specific school
    private static void menuAddStudentToSchool() {
        System.out.println("Enter the name of the school:");
        String schoolName = scanner.nextLine().trim();

        // Get the student ID from the user
        System.out.println("Enter the student's ID:");
        int id = getId();

        // Retrieve the student information based on the ID
        Student student = data.getStudent(id);
        if (student != null) {
            // Add the student to the specified school
            data.addStudentToSchool(schoolName, student);
        } else {
            System.out.println("Student with ID " + id + " not found!");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// These are the output functions
    // Method to find and print the most affordable school
    private static void menuMostAffordable_School() {
        HashMap<String, Double> averageSpendingBySchool = data.calculateAverageSpendingBySchool();

        if (averageSpendingBySchool.isEmpty()) {
            System.out.println("No schools are currently registered in the system.");
            return;
        }

        // Initialize variables to track the most affordable school
        String mostAffordableSchool = null;
        double minAverageSpending = Double.MAX_VALUE;

        // Iterate through each school to find the one with the lowest average spending
        for (Map.Entry<String, Double> entry : averageSpendingBySchool.entrySet()) {
            String schoolName = entry.getKey();
            double averageSpending = entry.getValue();
            if (averageSpending < minAverageSpending) {
                minAverageSpending = averageSpending;
                mostAffordableSchool = schoolName;
            }
        }

        // Check if a most affordable school was found
        if (mostAffordableSchool != null) {
            System.out.println("The most affordable school is: " + mostAffordableSchool +
                    " with an average spending of: $" + String.format("%.2f", minAverageSpending));
        } else {
            System.out.println("Unable to determine the most affordable school.");
        }
    }
    // Method to print average spending for each school
    private static void menuAverageSpendingBySchool() {
        HashMap<String, Double> averageSpendingBySchool = data.calculateAverageSpendingBySchool();

        System.out.println("Average spending by school:");
        for (String schoolName : averageSpendingBySchool.keySet()) {
            double averageSpending = averageSpendingBySchool.get(schoolName);
            System.out.println("School: " + schoolName + ", Average spending per student: $" + averageSpending);
        }
    }

    private static void menuHighestSpender() {
        int highestSpenderId;//gets the ID of the highest spender;
        highestSpenderId = data.HighestSpender();
        printSingleStudent(highestSpenderId);
    }

    /**
     * Method to print a single student's info
     * @param highestSpenderId;
     */
    private static void printSingleStudent(int highestSpenderId) {
        Student highestSpender = data.getStudent(highestSpenderId);
        if (highestSpender != null) { // This is to hande the exception whereby an invalid id  is entered(id that is not stored)
            System.out.print(STUDENT_HEADER);
            System.out.println(STUD_SEPARATOR);
            System.out.println(highestSpender);
        } else {
            System.out.printf("We do not have a student with id %d", highestSpenderId);
        }
    }

    private static void menuGetStudentInfo() {
        int id = getId();
        printSingleStudent(id);

    }

    private static void menuPrintAllStudents() {

        System.out.print(STUDENT_HEADER);
        ArrayList<Student>students= data.getAllStudents();
        students.sort(new StudentNameIdCompartor()); // sort by name and id
        // getAllStudents() is an arraylist which will store all our students[] (with their information)
        // so We will loop through it to access each student[] using an enhanced for loop. Note that in each index of the student [], we have information stored(name,id,email)
        for (Student student :students) {
            System.out.println(STUD_SEPARATOR);
            System.out.println(student);
        }

    }
    private static void menuPrintAllSchools() {
        System.out.println("List of all schools:");
        System.out.print(SCHOOL_HEADER);

        ArrayList<School> schools = data.schoolList();
        if (schools.isEmpty()) {
            System.out.println("No schools are currently registered.");
            return;
        }
        // Sort the list using the SchoolNameComparator
        schools.sort(new SchoolNameComparator());

        // Now, iterate through the sorted list of schools and print each
        for (School school : schools) {
            System.out.println(SCHOOL_SEPARATOR); // Separator for visual separation in output
            System.out.println(school); // Assumes School has an appropriate toString method
        }
    }
    public static void studentsBySchool() {
        System.out.println("Enter the name of the school:");
        String schoolName = scanner.nextLine().trim();

        School school = data.getSchoolByName(schoolName);
        if (school == null) {
            System.out.println("School \"" + schoolName + "\" not found.");
            return;
        }

        ArrayList<Student> students = school.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students are currently registered in \"" + schoolName + "\".");
        } else {
            System.out.println("List of all students in \"" + schoolName + "\":");
            System.out.print(STUDENT_HEADER);
            for (Student student : students) {
                System.out.println(STUD_SEPARATOR);
                System.out.println(student); // This uses Student's toString method
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
// these are the custom functions to get the data(getName,getID,...)
    public static String getName() {
        System.out.println("Enter a name:");
        String name;
        //this is to make sure we do not get an empty input for name
        do {
            name = scanner.nextLine().trim();
        } while (name.isEmpty());
        return name;
    }

    public static int getId() {
        System.out.println("Enter an ID ");
        String studentId = scanner.nextLine().trim();
        // We will ensure that the ID is of length 8
        while (studentId.length() != 8) {
            System.out.println("ID should be of length = 8!");
            studentId = scanner.nextLine().trim();
        }
        return Integer.parseInt(studentId);
    }

    public static String getEmail() {
        String email = "";
        while (email.isEmpty()){ // ask for email until a valid string is entered
            System.out.println("Enter a student email.");
            email = scanner.nextLine().toLowerCase(); // emails must be lowercase to avoid typo errors
        }

        return email;
    }
    private static String getSchoolName() {
        String name;
        do {
            System.out.println("Enter the name of the new school:");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("School name cannot be empty. Please try again.");
            }
        } while (name.isEmpty());
        return name;
    }

    private static int getSchoolId() {
        int id = -1;
        do {
            System.out.println("Enter the unique ID for the new school (4 digits):");
            String idInput = scanner.nextLine().trim();
            try {
                id = Integer.parseInt(idInput);
                if (idInput.length() != 4) {
                    System.out.println("School ID must be exactly 4 digits. Please try again.");
                    id = -1; // Reset ID to force loop continuation
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter a numeric value with exactly 4 digits.");
            }
        } while (id == -1);
        return id;
    }

}
