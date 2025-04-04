package ca.ucalgary.cpsc.groupprojectgui;

import ca.ucalcary.cpsc.groupprojectgui.Data;
import ca.ucalcary.cpsc.groupprojectgui.objects.ExpenseType;
import ca.ucalcary.cpsc.groupprojectgui.objects.School;
import ca.ucalcary.cpsc.groupprojectgui.objects.Student;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private Data data;
    @BeforeEach
    void cleanUp() {
        data = new Data();
    }
    @org.junit.jupiter.api.Test
    void storeNewStudent1() {
        String name = "Ahmed";
        int id = 12345678;
        String email= "alvee@ucalgary.ca";
        boolean success = data.storeNewStudent(name,id,email);
        assertTrue(success);

    }
    @org.junit.jupiter.api.Test
    void storeNewStudent2() {
        String name = "Ahmed";
        int id = 12345678;
        String email= "alvee@ucalgary.ca";
        data.storeNewStudent(name,id,email);
        boolean success = data.storeNewStudent(name,id,email);
        assertFalse(success);

    }


    @org.junit.jupiter.api.Test
    void checkExistStudent() {
        String name = "Ahmed";
        int id = 12345678;
        String email= "alvee@ucalgary.ca";
        int wrongID = 12345;
        String wrongEmail= "alveee@cvd";
        data.storeNewStudent(name,wrongID  ,wrongEmail);
        boolean success = data.checkExistStudent(id,email);
        assertFalse(success);
    }
    @org.junit.jupiter.api.Test
    void checkExistStudent2() {
        String name = "Ahmed";
        int id = 12345678;
        String email= "alvee@ucalgary.ca";
        data.storeNewStudent(name,id  ,email);
        boolean expected= true;
        boolean actual = data.checkExistStudent(id,email);
        assertEquals(expected,actual);
    }

    @org.junit.jupiter.api.Test
    void studentExists() {
        String name = "Cellou";
        int id = 12345678;
        String email= "mamadou@ucalgary.ca";
        data.storeNewStudent(name,id  ,email);
        boolean expected= true;
        boolean actual = data.studentExists(id);
        assertEquals(expected,actual);
    }
    @org.junit.jupiter.api.Test
    void studentExists2() {
        String name = "Ahmed";
        int id = 12345678;
        String email= "mamadou@ucalgary.ca";
        data.storeNewStudent(name,id  ,email);
        boolean expected= true;
        boolean actual = data.studentExists(1234);// gave it the wrong id
        assertNotEquals(expected,actual);
    }
    @org.junit.jupiter.api.Test
    void getAllStudents() {
        int id = 12345678;
        String name = "Cellou";
        String email = "cellou@uofc";
        ArrayList<Student> expectedStudents = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            data.storeNewStudent(name, id, email);
            name = "Ahmed";
            id += 1;
            email = "ahmed@uofc";
        }
        Student student1 = new Student("Cellou", 12345678, "cellou@uofc");
        Student student2 = new Student("Ahmed", 12345679, "ahmed@uofc");
        expectedStudents.add(student1);
        expectedStudents.add(student2);

        ArrayList<Student> actualStudents = data.getAllStudents();

        assertArrayEquals(expectedStudents.toArray(), actualStudents.toArray());
    }
    @org.junit.jupiter.api.Test
    void getAllStudents2() {
        // Store some students in the GroupProject.Data class
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");
        data.storeNewStudent("Ahmed", 12345679, "ahmed@uofc");

        // Expected list of students
        ArrayList<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Cellou", 12345678, "cellou@uofc"));
        expectedStudents.add(new Student("Ibukun", 12345679, "ibk@uofc"));// Changed the name, so this test should not be equal

        // Retrieve all students from the GroupProject.Data class
        ArrayList<Student> actualStudents = data.getAllStudents();

        // Compare expected and actual lists of students
        assertNotEquals(expectedStudents.toArray(), actualStudents.toArray());
    }
    @org.junit.jupiter.api.Test
    void getStudent() {
        // Store a student in the GroupProject.Data class
        data.storeNewStudent("Ibukun", 12345678, "Ibk@uofc");

        // Retrieve the stored student
        Student expectedStudent = new Student("Ibukun", 12345678, "Ibk@uofc");
        Student actualStudent = data.getStudent(12345678);

        // Compare expected and actual student data
        assertEquals(expectedStudent.getId(), actualStudent.getId());
    }

    @org.junit.jupiter.api.Test
    void getStudent2() {
        // Try to retrieve a student that does not exist
        Student actualStudent = data.getStudent(12345679);

        // Ensure that the retrieved student is null
        assertNull(actualStudent);
    }


    @org.junit.jupiter.api.Test
    void storeNewSchool() {
        // Store a new school in the GroupProject.Data class
        School school = new School(2034,"GroupProject.objects.School of Engineering","Manitoba");
        data.storeNewSchool(school);

        // Retrieve all schools from the GroupProject.Data class
        ArrayList<String> actualSchools = new ArrayList<>(data.schoolsByName.keySet());


        // Check if the new school is in the list of schools
        assertTrue(actualSchools.contains("GroupProject.objects.School of Engineering"));
    }

    @org.junit.jupiter.api.Test
    void storeNewSchool2() { // we cannot have the same school name multiple times
        // Store a new school in the GroupProject.Data class
        School school = new School(2034,"GroupProject.objects.School of Engineering","Manitoba");
        data.storeNewSchool(school);


        // Try to store the same school again
        boolean success = data.storeNewSchool(school);

        // Ensure that the duplicate school is not stored
        assertFalse(success);
    }


    @org.junit.jupiter.api.Test
    void addStudentToSchool() {
        // Store a student in the GroupProject.Data class
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");
        Student student = data.getStudent(12345678);
        School school = new School(2034,"GroupProject.objects.School of Engineering","Manitoba");
        data.storeNewSchool(school);
        // Add the student to a school
        boolean success = data.addStudentToSchool(school.getName(), student);

        // Ensure that the student is added to the school successfully
        assertTrue(success);
    }

    @org.junit.jupiter.api.Test
    void addStudentToSchool_InvalidStudent() {
        // Try to add an inexistant student to a school
        boolean success = data.addStudentToSchool("UOFC",data.getStudent(12345678));

        // Ensure that the student is not added to the school
        assertFalse(success);
    }

    @Test
    void addGroceryExpense() {
        // Store a student in the GroupProject.Data class
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");

        // Add grocery expenses for the student
        boolean success = data.addExpense(12345678, 50.0, ExpenseType.GROCERY,0);

        // Ensure that the grocery expense is added successfully
        assertTrue(success);
    }

    @Test
    void addGroceryExpense_InvalidStudent() {
        // Try to add grocery expense for an invalid student
        boolean success = data.addExpense(12345678, 50.0,ExpenseType.GROCERY,0);

        // Ensure that the grocery expense is not added
        assertFalse(success);
    }

    @org.junit.jupiter.api.Test
    void addTuitionExpense() {
        // Store a student in the GroupProject.Data class
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");

        // Add tuition expenses for the student
        boolean success = data.addExpense(12345678, 500.0,ExpenseType.TUITION,0);

        // Ensure that the tuition expense is added successfully
        assertTrue(success);
    }

    @org.junit.jupiter.api.Test
    void addTuitionExpense_InvalidStudent() {
        // Try to add tuition expense for an invalid student
        boolean success = data.addExpense(12345679, 500.0,ExpenseType.TUITION,0);

        // Ensure that the tuition expense is not added
        assertFalse(success);
    }

    @org.junit.jupiter.api.Test
    void addRentExpense() {
        // Store a student in the GroupProject.Data class
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");

        // Add rent expenses for the student
        boolean success = data.addExpense(12345678, 1000.0,ExpenseType.RENT,0);

        // Ensure that the rent expense is added successfully
        assertTrue(success);
    }

    @org.junit.jupiter.api.Test
    void addRentExpense_InvalidStudent() {
        // Try to add rent expense for an invalid student
        boolean success = data.addExpense(12345679, 1000.0,ExpenseType.RENT,0);

        // Ensure that the rent expense is not added
        assertFalse(success);
    }

    @Test
    void calculateTotalExpenses() {
        // Test Case 1: Calculate total expenses for a valid student with multiple expenses
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");
        Student student = data.getStudent(12345678);
        data.addExpense(12345678, 50.0,ExpenseType.GROCERY,0);
        data.addExpense(12345678, 500.0,ExpenseType.TUITION,0);
        data.addExpense(12345678, 1000.0,ExpenseType.RENT,0);
        double totalExpenses1 = student.getTotalExpenses();
        assertEquals(1550.0, totalExpenses1);
    }
    @org.junit.jupiter.api.Test
    void calculateTotalExpenses2_For_Inexistant_Student(){
        Student student = new Student("Cellou", 12345678, "cellou@uofc");
        // Test Case 2: Calculate total expenses for an invalid student without any expenses stored to his ID
        double totalExpenses2 = student.getGroceryTotal(); // Invalid student ID
        assertEquals(0.0, totalExpenses2); // We expect 0 since no expense has been stored
    }

    @org.junit.jupiter.api.Test
    void highestSpender() {
        // Test Case 1: Find highest spender among multiple students
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");
        data.storeNewStudent("Ahmed", 12345679, "ahmed@uofc");
        data.addExpense(12345678, 50.0,ExpenseType.GROCERY,0);
        data.addExpense(12345678, 500.0,ExpenseType.RENT,0);
        data.addExpense(12345678, 1000.0,ExpenseType.TUITION,0);
        data.addExpense(12345679, 700.0,ExpenseType.GROCERY,0);
        data.addExpense(12345679, 1200.0, ExpenseType.TUITION,0);
        int highestSpender1 = data.HighestSpender();///
        assertEquals(12345679, highestSpender1);
    }
    @org.junit.jupiter.api.Test
    void highestSpender2(){
        // Test Case 2: Find highest spender among no students
        int highestSpender2 = data.HighestSpender(); // No students exist
        assertEquals(-1, highestSpender2);// -1 is the default id number of the highest spender
    }

    @org.junit.jupiter.api.Test
    void getStudentsBySchool() {
        // Test Case 1: Retrieve students from a valid school
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");

        // Store a new school
        School newSchool= new School(2034,"UCalgary","Alberta");
        data.storeNewSchool(newSchool);

        data.addStudentToSchool( newSchool.getName(),data.getStudent(12345678) );
        // We then use an array list of object[] in which we get the students tracked with the key schoolName "UCalgary" in our HashMap
        ArrayList<Student> students1 = data.getStudentsBySchool("UCalgary");
        assertEquals(1, students1.size());
    }@org.junit.jupiter.api.Test
    void getStudentsBySchool2() {
        // Test Case 2: We have more than one student in our school
        data.storeNewStudent("Ibukun", 12345678, "ibk@uofc");
        data.storeNewStudent("Cellou", 11111111, "cellou@uofc");
        data.storeNewStudent("Ahmed", 22222222, "alvee@uofc");

        // Store a new school
        School school = new School(2345,"UCalgary","Alberta") ;
        data.storeNewSchool(school);

        data.addStudentToSchool( school.getName(),data.getStudent(12345678) );
        data.addStudentToSchool( school.getName(),data.getStudent(11111111) );
        data.addStudentToSchool( school.getName(),data.getStudent(22222222) );
        // We then use an array list of object[] in which we get the students tracked with the key schoolName "UCalgary" in our HashMap
        ArrayList<Student> students1 = data.getStudentsBySchool("UCalgary");
        assertEquals(3, students1.size());
    }

    @org.junit.jupiter.api.Test
    void calculateTotalSpendingBySchool() {
        // Test Case 1: Calculate total spending for a valid school with multiple students
        // Store the school
        School school = new School(2567,"UCalgary","Alberta");
        data.storeNewSchool(school);
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");
        data.storeNewStudent("Ahmed", 12345679, "ahmed@uofc");
        data.addStudentToSchool(school.getName(), data.getStudent(12345678));
        data.addStudentToSchool(school.getName(), data.getStudent(12345679));
        data.addExpense(12345678, 50.0,ExpenseType.GROCERY,0);
        data.addExpense(12345678, 500.0,ExpenseType.TUITION,0);
        data.addExpense(12345678, 1000.0,ExpenseType.RENT,0);
        data.addExpense(12345679, 700.0,ExpenseType.TUITION,0);
        data.addExpense(12345679, 1200.0 ,ExpenseType.RENT,0);
        Double totalSpending1 = data.calculateTotalSpendingBySchool().get("UCalgary"); // ammount is in the value of HashMap so we use the key to  get it
        assertEquals(3450.0, totalSpending1);
    }

    @org.junit.jupiter.api.Test
    void calculateTotalSpendingBySchool2_For_School_thatIsNotStored() {
        //Calculate total spending for an untracked school
        String school = "UCalgary";
        data.storeNewStudent("Cellou", 12345678, "cellou@uofc");
        data.storeNewStudent("Ahmed", 12345679, "ahmed@uofc");
        data.addStudentToSchool(school, data.getStudent(12345678));
        data.addStudentToSchool(school, data.getStudent(12345679));
        data.addExpense(12345678, 50.0,ExpenseType.GROCERY,0);
        data.addExpense(12345678, 500.0,ExpenseType.TUITION,0);
        data.addExpense(12345678, 1000.0,ExpenseType.RENT,0);
        data.addExpense(12345679, 700.0,ExpenseType.TUITION,0);
        data.addExpense(12345679, 1200.0,ExpenseType.RENT,0);
        Double totalSpending2 =data.calculateTotalSpendingBySchool().get(school);
        assertNull(totalSpending2); // Students have not been added to the school so there is no amount linked to its value
    }

    @org.junit.jupiter.api.Test
    void calculateAverageSpendingBySchool() {
        // Test Case 1: Calculate average spending for a valid school with multiple students
        School school = new School(1123,"UCalgary","Alberta");
        data.storeNewSchool(school); // store the school*
        data.storeNewStudent("Ibukun", 12395678, "ibk@uofc");
        data.storeNewStudent("Muhammad", 12345079, "muhammad@uofc");
        data.addStudentToSchool(school.getName(), data.getStudent(12395678));
        data.addStudentToSchool(school.getName(), data.getStudent(12345079));
        data.addExpense(12395678, 50.0,ExpenseType.GROCERY,0);
        data.addExpense(12395678, 500.0,ExpenseType.TUITION,0);
        data.addExpense(12395678, 1000.0,ExpenseType.RENT,0);
        data.addExpense(12345079, 700.0,ExpenseType.TUITION,0);
        data.addExpense(12345079, 1200.0,ExpenseType.RENT,0);
        Double averageSpending1 = data.calculateAverageSpendingBySchool().get(school.getName());
        assertEquals(1725.0, averageSpending1);
    }

    @org.junit.jupiter.api.Test
    void calculateAverageSpendingOfUntrackedSchool() {
        String school = "UCalgary";
        data.storeNewStudent("Ibukun", 12395678, "ibk@uofc");
        data.storeNewStudent("Muhammad", 12345079, "muhammad@uofc");
        data.addStudentToSchool(school, data.getStudent(12395678));
        data.addStudentToSchool(school, data.getStudent(12345079));
        data.addExpense(12395678, 50.0,ExpenseType.GROCERY,0);
        data.addExpense(12395678, 500.0,ExpenseType.TUITION,0);
        data.addExpense(12395678, 1000.0,ExpenseType.RENT,0);
        data.addExpense(12345079, 700.0,ExpenseType.TUITION,0);
        data.addExpense(12345079, 1200.0,ExpenseType.RENT,0);
        Double averageSpending1 = data.calculateAverageSpendingBySchool().get(school);
        assertNull(averageSpending1);
    }
}