package ca.ucalcary.cpsc.groupprojectgui;

import ca.ucalcary.cpsc.groupprojectgui.compartors.SchoolNameComparator;
import ca.ucalcary.cpsc.groupprojectgui.compartors.StudentNameIdCompartor;
import ca.ucalcary.cpsc.groupprojectgui.objects.ExpenseType;
import ca.ucalcary.cpsc.groupprojectgui.objects.School;
import ca.ucalcary.cpsc.groupprojectgui.objects.Student;
import ca.ucalcary.cpsc.groupprojectgui.util.FileLoader;
import ca.ucalcary.cpsc.groupprojectgui.util.Filesaver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.*;
/**
 * MainController handles the GUI logic for the Student Expense Tracker application.
 * It manages user interactions, including adding expenses, students, and schools,
 * as well as loading and saving data.
 *
 * Authors: Cellou Mamadou, Ahmed Alvee, Ibukun Ige
 * Date: 06/04/2024
 * T01
 */

public class MainController {
    private  static Data data = new Data();
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
    }
    @FXML
    private MenuItem addSchool;

    @FXML
    private TextField discount;

    @FXML
    private TextField groceryAmount;

    @FXML
    private TextField groceryId;

    @FXML
    private TextField info_StudentId;

    @FXML
    private TextField rentAmount;

    @FXML
    private TextField rentId;

    @FXML
    private TextField scholarship;

    @FXML
    private TextArea schoolData;

    @FXML
    private TextField schoolName;

    @FXML
    private TextField schoolToShow_Name;

    @FXML
    private TextField studentId;

    @FXML
    private TextArea studentsLabel;

    @FXML
    private TextField tuitionAmount;

    @FXML
    private TextField tuitionId;

    @FXML
    private TextField utilitiesAmount;
    @FXML
    private Label status_Label;

    /**
     * Clears all input fields in the GUI.
     */
    private void clearFields() {
        groceryId.clear();
        groceryAmount.clear();
        discount.clear();
        rentId.clear();
        rentAmount.clear();
        utilitiesAmount.clear();
        tuitionId.clear();
        tuitionAmount.clear();
        scholarship.clear();
        schoolName.clear();
        studentId.clear();
    }

    /**
     * Adds a grocery expense based on user input.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void addGroceryExpense(ActionEvent event) {
        // Call the general method for adding expenses with specific parameters for grocery expenses
        addExpense(groceryId, groceryAmount, discount, ExpenseType.GROCERY);
        // Call the general method for adding expenses with specific parameters for grocery expenses
        clearFields();
    }

    /**
     * Adds an expense for a specific student or school.
     *
     * @param idField The TextField containing the ID related to the expense.
     * @param amountField The TextField containing the amount of the expense.
     * @param special_Field A TextField that contains special information related to the expense (e.g., discount).
     * @param type The type of expense to add.
     */
    private void addExpense(TextField idField, TextField amountField, TextField special_Field, ExpenseType type) {
        try {

            // Parse inputs from text fields
            int id = Integer.parseInt(idField.getText());
            double amount = Double.parseDouble(amountField.getText());
            double discount = special_Field.isVisible() ? Double.parseDouble(special_Field.getText()) : 0.0;

            // Attempt to add the expense to the data model
            boolean result = data.addExpense(id, amount, type, discount);

            // Update status label based on the result
            if (result) {
                status_Label.setText(type + " expense added successfully.");
                status_Label.setTextFill(Color.GREEN);
            } else {
                status_Label.setText("Failed to add " + type + " expense.");
                status_Label.setTextFill(Color.RED);
            }
        } catch (NumberFormatException nfe) {
            // Handle invalid input formats
            status_Label.setTextFill(Color.RED);
            status_Label.setText("Invalid input. Please ensure IDs and amounts are correct.");
        }
    }

    /**
     * Triggers the addition of a new rent expense from the GUI.
     * Gathers input from the rent-related fields and passes them to a general method for handling expenses.
     * Clears the input fields after the operation.
     *
     * @param event The event triggered by clicking the 'Add Rent Expense' button.
     */
    @FXML
    void addRentExpense(ActionEvent event) {
        addExpense(rentId, rentAmount, utilitiesAmount, ExpenseType.RENT);
        clearFields();
    }

    /**
     * Handles the action of opening a new window to add a student.
     * It loads the Student form, sets up the necessary data, and displays the form to the user.
     * Waits for the operation to complete before returning focus to the main window.
     *
     * @param event The event triggered by clicking the 'Add Student' button.
     */
    @FXML
    void addStudent(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("Student.fxml"));
        Scene scene =null;
        try{
            scene = new Scene(fxmlLoader.load(), 400, 400);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        StudentController cont = fxmlLoader.getController();
        cont.setData(data,this);
        Stage stage=new Stage();
        stage.setTitle("Add New Student");
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Opens a new window for adding a new school.
     * Similar to addStudent, it loads the School form, prepares it with the necessary data,
     * and presents it to the user, awaiting completion.
     *
     * @param event The event triggered by clicking the 'Add School' button.
     */
    @FXML
    void addSchool(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("School.fxml"));
        Scene scene =null;
        try{
            scene = new Scene(fxmlLoader.load(), 400, 400);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        SchoolController cont = fxmlLoader.getController();
        cont.setData(data,this);
        Stage stage=new Stage();
        stage.setTitle("Add New School");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void addStudentToSchool(ActionEvent event) {
        try {
            String schoolname = schoolName.getText(); // Assuming this is the school ID.
            int id = Integer.parseInt(studentId.getText());
            Student student = data.getStudent(id);
            // Method to add student to school in the data model.
            boolean result = data.addStudentToSchool(schoolname,student);

            if (result) {
                status_Label.setText(String.format("Student %s added to school %s successfully.",student.getName(),schoolname));
                status_Label.setTextFill(Color.GREEN);
                clearFields();
            } else {
                status_Label.setText("Failed to add student to school.");
                status_Label.setTextFill(Color.RED);
            }
        }catch (NumberFormatException nfe){
            status_Label.setTextFill(Color.RED);
            status_Label.setText("Cannot parse into integer");
        }

    }

    @FXML
    void addTuitionExpense(ActionEvent event) {
        addExpense(tuitionId, tuitionAmount, scholarship, ExpenseType.TUITION);
        clearFields();
    }
    @FXML
    void studentInfo(ActionEvent event) {
        try{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student Info");
            Student student = data.getStudent(Integer.parseInt(info_StudentId.getText()));
            alert.setHeaderText("Student:"+student.getId());
            alert.setContentText(String.format("Name:%s%nID:%d%nEmail:%s%n",student.getName(),student.getId(),student.getEmail()));
            alert.showAndWait();
        }catch (NumberFormatException|NullPointerException e){
            status_Label.setTextFill(Color.RED);
            status_Label.setText("Invalid input");
        }

    }
    @FXML
    void averageSpending(ActionEvent event) {
        HashMap<String, Double> averageSpendingBySchool = data.calculateAverageSpendingBySchool();
        StringBuilder sb =new StringBuilder();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Average Spending");
        alert.setHeaderText("Average amount spent in each school:");
        if (averageSpendingBySchool.isEmpty()){
            alert.setContentText("No records found");
            status_Label.setTextFill(Color.RED);
            status_Label.setText("No records found");
        }else {
            for (String schoolName : averageSpendingBySchool.keySet()) {
                double averageSpending;
                if (averageSpendingBySchool.get(schoolName).isNaN()){
                    averageSpending = 0.0;
                }else averageSpending = averageSpendingBySchool.get(schoolName);
                sb.append(String.format("School:%s  \t Average spending per student: %.2f $",schoolName,averageSpending));
                sb.append("\n");

            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
        }

    }

    @FXML
    void cheapestSchool(ActionEvent event) {
        HashMap<String, Double> averageSpendingBySchool = data.calculateAverageSpendingBySchool();

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
        School school = data.getSchoolByName(mostAffordableSchool); //use already defined method to get the school object
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cheapest School");

        if (school != null) {
            alert.setHeaderText("Cheapest School Information");
            alert.setContentText(String.format("Name: %s\nID: %d\nProvince: %s",
                    school.getName(), school.getId(), school.getProvince()));
        } else {
            alert.setHeaderText("No Schools Found");
            alert.setContentText("There are no schools registered.");
        }

        alert.showAndWait();
    }
    @FXML
    void highestSpender(ActionEvent event) {
        int id = data.HighestSpender();
        Student student = data.getStudent(id);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Highest Spender");

        if (student != null) {
            alert.setHeaderText("Highest Spender Student Information");
            alert.setContentText(String.format("Name: %s\nID: %d\nTotal Spending: $%.2f",
                    student.getName(), student.getId(), student.getTotalExpenses()));
        } else {
            alert.setHeaderText("No Student Found");
            alert.setContentText("There is no data");
        }

        alert.showAndWait();

    }
    @FXML
    void studentsBySchool(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        StringBuilder sb = new StringBuilder();
        sb.append(STUDENT_HEADER);
        String name_School = schoolToShow_Name.getText();
        School school = data.getSchoolByName(name_School);
        alert.setTitle(String.format("List of all the students in %s",name_School));
        alert.setHeaderText("School \"" + schoolName);
        if (school == null){
            status_Label.setTextFill(Color.RED);
            status_Label.setText("School not found");
        }else {
            ArrayList<Student>students = school.getStudents();
            if (students.isEmpty()){
                status_Label.setTextFill(Color.RED);
                status_Label.setText(String.format("No students currently enrolled in %s",name_School));
                alert.setContentText("No students to show");
            }
            else {
                for (Student student: students){
                    sb.append(STUD_SEPARATOR);
                    sb.append(student);
                    sb.append("\n");
                }
            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
        }
    }
    @FXML
    void exit(ActionEvent event) {Platform.exit();}



    @FXML
    void load(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load a file");
        fc.setInitialDirectory(new File("."));
        fc.setInitialFileName("data.csv");
        File file = fc.showOpenDialog(new Stage());
        load(file);
        viewStudents();
        viewSchools();

    }
    public void viewSchools() {
        StringBuilder sb = new StringBuilder();
        sb.append(SCHOOL_HEADER);
        ArrayList<School> schools = data.schoolList();
        if (schools.isEmpty()) {
            schoolData.setText("No schools are currently registered.");
            return;
        }
        // Sort the list using the SchoolNameComparator
        schools.sort(new SchoolNameComparator());

        // Now, iterate through the sorted list of schools and print each
        for (School school : schools) {
            sb.append(SCHOOL_SEPARATOR); // Separator for visual separation in output
            sb.append(school); // Assumes School has an appropriate toString method
            schoolData.setText(sb.toString());
        }
    }
    public void viewStudents() {
        StringBuilder sb = new StringBuilder();
        sb.append(STUDENT_HEADER);
        ArrayList<Student> students= data.getAllStudents();


        sb.append("\n");

        students.sort(new StudentNameIdCompartor()); // sort by name and id
        // getAllStudents() is an arraylist which will store all our students[] (with their information)
        // so We will loop through it to access each student[] using an enhanced for loop. Note that in each index of the student [], we have information stored(name,id,email)
        for (Student student :students) {
            sb.append(STUD_SEPARATOR);
            sb.append(student.toString());
            studentsLabel.setText(sb.toString());
        }



    }public void load(File file){
        try{
            status_Label.setTextFill(Color.BLACK);
            status_Label.setText("");
            Data data= FileLoader.load(file);
            if (data==null){
                status_Label.setTextFill(Color.RED);
                status_Label.setText(String.format("Failed to load data from file %s%n",file));
            }else {
                status_Label.setTextFill(Color.GREEN);
                status_Label.setText(String.format("Loaded data successfully from file %s%n",file));
                MainController.data=data;
            }
        }catch (NullPointerException e){
            status_Label.setTextFill(Color.RED);
            status_Label.setText("Null pointer");
        }
        viewSchools();
        viewStudents();

    }

    @FXML
    void save(ActionEvent event) {
        try{
            FileChooser fc = new FileChooser();
            fc.setTitle("Load a file");
            fc.setInitialDirectory(new File("."));
            fc.setInitialFileName("data.csv");
            File file = fc.showSaveDialog(new Stage());
            if (Filesaver.save(file,data)){
                System.out.printf("Saved to file %s%n",file);
            }else {
                System.err.printf("Failed to save to file %s%n",file);
            }
        }catch (NullPointerException e){
            status_Label.setTextFill(Color.RED);
            status_Label.setText("Null");
        }

    }
    @FXML
    void about_Info(ActionEvent event){
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Expense Tracker Program");
        alert.setHeaderText("Information");
        String s = "";
        s+= String.format("Authors: Cellou, Ibukun, Ahmed%n");
        s+= String.format("Tutorial: T01%n");
        s+= String.format("TA: Javaad%n");
        s+= String.format("Version:3.0%n");
        s+= String.format("There are %d students and %d schools%n",data.getAllStudents().size(),data.schoolList().size());
        s+= String.format("This program tracks expenditures within a school to determine affordable schools%n");
        alert.setContentText(s);
        alert.showAndWait();

    }
    public Label setStatusLabel(){
        return this.status_Label;
    }

}