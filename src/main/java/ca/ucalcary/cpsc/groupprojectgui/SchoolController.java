package ca.ucalcary.cpsc.groupprojectgui;

import ca.ucalcary.cpsc.groupprojectgui.objects.School;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SchoolController {

    @FXML private TextField schoolID;
    @FXML private TextField schoolName;
    @FXML private TextField province;

    private Data data;
    private MainController mc;

    /**
     * Sets the main data model and main controller references for this controller.
     * This method allows the controller to interact with the application's central data model
     * and update the main UI based on actions taken in the school view.
     *
     * @param data The main data model instance containing all application data.
     * @param mc The main controller instance for updating the application UI.
     */
    public void setData(Data data, MainController mc){
        this.data = data;
        this.mc = mc;
    }


    /**
     * Handles the action of adding a new school based on user inputs.
     * It extracts the school ID, name, and province from the input fields,
     * creates a new School, and attempts to store it in the data model.
     * Upon success or failure, updates the main application's status label with the outcome.
     *
     * @param event The ActionEvent triggered by clicking the 'Add School' button.
     */
    @FXML
    protected void addSchool(ActionEvent event) {
        try{
            // Extract input values from form fields
            String schoolNameText = schoolName.getText();
            int schoolIDText =Integer.parseInt(schoolID.getText()) ;
            String provinceText = province.getText();
            // Create a new School object with the provided details
            School school = new School(schoolIDText,schoolNameText,provinceText);
            // Attempt to store the new school in the data model
            boolean success = data.storeNewSchool(school);
            if (success){
                //if school has been successfully added, i.e no other school has same ID, view it
                mc.viewStudents();
                mc.viewSchools();
                mc.setStatusLabel().setTextFill(Color.GREEN);
                mc.setStatusLabel().setText("School added successfully");
                clearFields();
            }else {
                mc.setStatusLabel().setTextFill(Color.RED);
                mc.setStatusLabel().setText("Unable to add school. Please enter a different ID/Name");

            }

        }catch(NumberFormatException nfe){
            mc.setStatusLabel().setTextFill(Color.RED);
            mc.setStatusLabel().setText("Invalid format input");
        }
    }

    // Utility method to clear input fields
    private void clearFields() {
        schoolName.clear();
        province.clear();
        schoolID.clear();
    }
}
