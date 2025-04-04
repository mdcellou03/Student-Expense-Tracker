package ca.ucalcary.cpsc.groupprojectgui;

import ca.ucalcary.cpsc.groupprojectgui.objects.*;

import java.util.*;

public class Data {
    /**
     * Arraylist of arrays which will store all students
     */
    private final ArrayList<Student> students;
    private final ArrayList<School> schools;

    // Add a new school with students
    // HashMap to store GroupProject.objects.School objects by their name
    public  HashMap<String, School> schoolsByName;
    // HashMap to store Expenses by student. Now we will store all the different expenses of each student in a single hashmap
    private final HashMap<Integer, ArrayList<Expense>> expensesById;
    // Hashset of student emails and ids, since those are supposed to be unique
    private final HashSet<String> emails;
    // use hashMap for ids to make lookup easier
    private  final HashMap<Integer, Student> ids = new HashMap<>();
    // constructor
    public Data(){
        students = new ArrayList<>();
        schoolsByName = new HashMap<>();
        expensesById = new HashMap<>();
        emails = new HashSet<>();
        schools= new ArrayList<>();
    }

    public  boolean storeNewStudent(String name, int id, String email) {
        if (!checkExistStudent(id, email)) {
            Student student= new Student(name,id,email);
            students.add(student);
            emails.add(email);
            ids.put(id, student);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a student exist by looking up its id or its email
     * @param id;
     * @param email;
     * @return true if id or email have already been stored
     */
    public  boolean checkExistStudent(int id, String email) {
        // we use our hashset to see if they contain the email or the id
        return studentExists(id) || emails.contains(email);
    }

    // Check if student exists just by id
    public  boolean studentExists(int id) {
        return ids.containsKey(id);
    }

    //Returns all students
    public  ArrayList<Student> getAllStudents() {
        return students;
    }
    /**
     * gets the value of the key in our HashMap of ids in which we have stored the students and their information
     * @param id;
     * @return Object[] student
     */
    public  Student getStudent(int id) {
        return ids.get(id);
    }

    /**
     * Method to stroe a new school by its name.
     * @param newSchool;
     * @return true if the student was not stored previously by id or email. Otherwise false
     */
    public  boolean storeNewSchool(School newSchool) {
        // Check if a school with the same name or ID already exists
        if (schoolsByName.containsKey(newSchool.getName())) {
            System.out.println("A school with the name \"" + newSchool.getName() + "\" already exists!");
            return false;
        } else if (schoolsByName.values().stream().anyMatch(school -> school.getId() == newSchool.getId())) {
            System.out.println("A school with the ID \"" + newSchool.getId() + "\" already exists!");
            return false;
        }

        schoolsByName.put(newSchool.getName(), newSchool);
        schoolList().add(newSchool);
        System.out.println("New school \"" + newSchool.getName() + "\" added successfully!");
        return true;
    }



    /**
     * Method to add a student to a specific school after storing him in our global system.
     * @param schoolName;
     * @param student;
     * @return true if the school exists or false if it does not.
     */
    public  boolean addStudentToSchool(String schoolName, Student student) {
        School school = schoolsByName.get(schoolName);
        if (school != null) {
            school.addStudent(student);
            System.out.println("GroupProject.objects.Student \"" + student.getName() + "\" added to the school \"" + school.getName() + "\" successfully!");
            return true;
        } else {
            System.out.println("GroupProject.objects.School \"" + schoolName + "\" not found!");
            return false;
        }
    }
    /**
     * Method to add an expense  (Tuition/Grocery/Rent to a specific student. additionalInfo is either discount or extra fees depending on the expense type
     * @param studentId;
     * @param amount;
     * @param type;
     * @param additionalInfo;
     * @return true if GroupProject.objects.Expense has been added successfully to an existing student or False if we do not have an instance of the student with the given id.
     */
    public  boolean addExpense(int studentId, double amount, ExpenseType type, double additionalInfo) {
        Student student = getStudent(studentId);
        if (student != null) {
            // if the student exists, we will add an expense to it depending on the provided type
            Expense expense = switch (type) {
                case GROCERY -> new GroceryExpense(amount,additionalInfo);
                case TUITION -> new TuitionExpense(amount,additionalInfo);
                case RENT -> new RentExpense(amount,additionalInfo);
            };
            expensesById.computeIfAbsent(studentId, k -> new ArrayList<>()).add(expense);// lambda method checks if the id is already stored. if not, stores with the corresponding expense
            student.addExpenses(expense);
            return true;
        } else {
            System.out.println("GroupProject.objects.Student with ID " + studentId + " not found!%n");
            return false;
        }
    }

    /**
     * loops through all our ids and gets the totalexpenses of that id and the compares it to the expenses of other ids to return the maximum
     * @return int HighestSpender
     */
    public  int HighestSpender() {
        int highestSpenderId = -1;
        double maxExpenses = 0.0;

        // Iterate over each student in the system
        for (Map.Entry<Integer, Student> entry : ids.entrySet()) {
            int studentId = entry.getKey();
            Student student = entry.getValue();

            // Retrieve the total expenses for the current student
            double totalExpenses = student.getTotalExpenses();

            // Check if the current student's total expenses are higher than the current maximum
            if (totalExpenses > maxExpenses) {
                maxExpenses = totalExpenses;
                highestSpenderId = studentId;
            }
        }

        // After iterating through all students, return the ID of the student with the highest total expenses
        return highestSpenderId;
    }

    //
    /**
     * Method to retrieve students by school name. it gets a school name which is the key value of our hashmap schoolsByName. it then returns the valyue
     * of that index(school). the value is an Arraylist where we are storing all the students
     * @param schoolName;
     * @return Arraylist<Object[]> which is the list of all the students in a specific school
     */
    public  ArrayList<Student> getStudentsBySchool(String schoolName) {
        School school = schoolsByName.get(schoolName);
        if (school != null) {
            return school.getStudents();
        } else {
            System.out.println("GroupProject.objects.School \"" + schoolName + "\" not found!");
            return new ArrayList<>(); // Return an empty list if the school is not found
        }
    }

    /**
     * Method to calculate the total spending of each student by school
     * @return Hashmap<schoolname,totalSpendings of students in that school
     */
    public  HashMap<String, Double> calculateTotalSpendingBySchool() {
        HashMap<String, Double> totalSpendingBySchool = new HashMap<>();

        // Iterate through each school stored by name
        for (Map.Entry<String, School> entry : schoolsByName.entrySet()) {
            String schoolName = entry.getKey();
            School school = entry.getValue();

            double totalSpending = 0.0;

            // Calculate total spending for students in the current school
            for (Student student : school.getStudents()) {
                totalSpending += student.getTotalExpenses(); // Use GroupProject.objects.Student's getTotalExpenses
            }

            // Store total spending for the current school
            totalSpendingBySchool.put(schoolName, totalSpending);
        }

        return totalSpendingBySchool;
    }
    /**
     * Method to calculate and return average spending for each school
     * @return Hashmap<schoolName,averageSpending>
     */
    public  HashMap<String, Double> calculateAverageSpendingBySchool() {
        HashMap<String, Double> averageSpendingBySchool = new HashMap<>();
        HashMap<String, Double> totalSpendingBySchool = calculateTotalSpendingBySchool();

        for (String schoolName : totalSpendingBySchool.keySet()) {
            ArrayList<Student> students = getStudentsBySchool(schoolName);
            int studentCount = students.size();
            double totalSpending = totalSpendingBySchool.get(schoolName);
            double averageSpending = totalSpending / studentCount;
            averageSpendingBySchool.put(schoolName, averageSpending);
        }

        return averageSpendingBySchool;
    }
    public  Map<String, School> getSchools() {
        return this.schoolsByName; //  this is the map of school names to School objects
    }
    public  School getSchoolByName(String name) {
        return this.schoolsByName.get(name);
    }
    public ArrayList<School> schoolList(){
        return this.schools;
    }

}
