package ca.ucalcary.cpsc.groupprojectgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class StudentController {

    @FXML
    private TextField email;

    @FXML
    private TextField name;

    @FXML
    private TextField stu_Id;
    private Data data;
    private MainController mc;
    public void setData(Data data, MainController mc){
        this.data = data;
        this.mc = mc;
    }

    /**
     * Handles the action of adding a new student from the GUI form inputs.
     * Validates user input, creates a new student entry in the data model, and updates
     * the UI to reflect the addition. It provides feedback to the user through the status label.
     *
     * @param event The ActionEvent triggered by the 'Add Student' button click.
     */
    @FXML
    void addStudent(ActionEvent event) {
        try{
            // Retrieve student details from form inputs
            String stName = name.getText();
            int stID =Integer.parseInt(stu_Id.getText()) ;
            String stEmail = email.getText();
            // Attempt to store the new student in the data model
            boolean success = data.storeNewStudent(stName,stID,stEmail);
            if (success) {
                // Update UI to reflect the new student addition
                mc.viewStudents();
                mc.viewSchools();
                mc.setStatusLabel().setTextFill(Color.GREEN);
                mc.setStatusLabel().setText("Student added successfully");
                clearFields();
            }else {
                mc.setStatusLabel().setTextFill(Color.RED);
                mc.setStatusLabel().setText("Unable to add school. Please enter a different ID/Name");
            }

            clearFields();
        }catch(NumberFormatException nfe){
            mc.setStatusLabel().setTextFill(Color.RED);
            mc.setStatusLabel().setText("Invalid format input");
        }


    }
    private void clearFields() {
        name.clear();
        email.clear();
        stu_Id.clear();
    }

}
