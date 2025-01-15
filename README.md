## Student Expense Tracker
This project is aimed at developing an expense tracking software with the final goal of being able to determine various statistics about student expenditures across various schools. Through those statistics, I was hoping to determine meaningful information such as which school could potentially be more affordable.

### About
>Authors:
> 
>Cellou Mamadou (mamadou.diallo@ucalgary.ca)
> 
> Ahmed Azmaine (ahmed.alvee1@ucalgary.ca)
> 
> Ibukun Ige (ibukun.ige@ucalgary.ca)
> 
> Version: 3.0
> 
> Year: 2024

## Run This Program
Use jar file or build source code
Uses Java 22 JDK, JavaFx 22 SDK

java --module-path "C:\Users\cello\Documents\Development Tools\javafx-sdk-23.0.1\lib" --enable-preview --add-modules javafx.controls,javafx.fxml -jar groupProjectGUI.jar

To run the jar file, replace the directory("C:\Users\Cellou\Documents\Winter 2024\CPSC 233\IdE\javafx-sdk-22\lib") of my module path with the one where you downloaded your sdk and then keep the other commands above.

## Tracker Features
The UI has a main screen and two popup screens.
The container for the program is BorderPane. The MenuBar consists of files (load, save, Quit), edit (add student, add school) and help menuitems.
Inorder to add students and schools, a secondary screen will be setup to inputs as well as save the data.
After each input, textfields are cleared.
There are also prompts textx to help users know what information to input.
