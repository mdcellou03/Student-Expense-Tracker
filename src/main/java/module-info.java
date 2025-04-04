module ca.ucalcary.cpsc.groupprojectgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.ucalcary.cpsc.groupprojectgui to javafx.fxml;
    exports ca.ucalcary.cpsc.groupprojectgui;
}