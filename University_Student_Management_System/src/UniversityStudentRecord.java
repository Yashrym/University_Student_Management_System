import java.io.BufferedReader; //for reading text data  efficiently
import java.io.FileReader; //read character from file
import java.io.FileWriter;//write characters to a file
import java.io.IOException;//handle potential i/o exceptions
import java.util.*;//import all classes from java.util package

//Main class for managing university student records
public class UniversityStudentRecord{
    //Scanner for user input
    private static Scanner input = new Scanner(System.in);
    //create 2D array for store student registration details
    private static String[][] REGISTRATION = new String[100][2];
    //counting registered students
    private static int countStudent = 0;
    //Array to store student objects
    private static Student[] students = new Student[100];
    //counting students who are with marks
    private static int countStudentMarks = 0;

    //Main method where program starts execution when it is run
    public static void main(String[] args) {
        //loading existing student details at the start
        studentDetailsLoad();
        int choice = 0;
        //loop the menu
        while (choice !=9){
            //display menu options
            menuDisplay();
            try {
                //check input is an integer
                if (input.hasNextInt()) {
                    choice = input.nextInt();
                    //handling user menu options
                    switch (choice) {
                        case 1:
                            availableSeats();
                            break;
                        case 2:
                            studentRegister();
                            break;
                        case 3:
                            studentDelete();
                            break;
                        case 4:
                            studentFind();
                            break;
                        case 5:
                            studentSave();
                            break;
                        case 6:
                            studentDetailsLoad();
                            break;
                        case 7:
                            studentDetailSortView();
                            break;
                        case 8:
                            showResultMenu();
                            break;
                        case 9:
                            System.out.println("Exit from menu.Have a nice day!");
                            break;
                        default: //handling if user enter invalid integer
                            System.out.println("Invalid choice.Please enter number between 1 to 9");
                    }
                }else {
                    //handling if user don't enter an integer
                    System.out.println("Invalid Input Please Enter a Number! ");
                    input.nextLine(); //clear invalid input
                    choice=0;//reset choice to 0 to loop again
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid Input Please Enter a Number!");
                input.nextLine(); //clear invalid input
                choice=0; //reset choice to 0 to loop again
            }
        }
    }
        //This method use to display user options in the menu with the correct input user should use
        private static void menuDisplay(){
            System.out.println("______Enter What You Want______");
            System.out.println("1 for check available seats");
            System.out.println("2 for register student");
            System.out.println("3 for delete details of student");
            System.out.println("4 for find student details");
            System.out.println("5 for save details in to file");
            System.out.println("6 for load student details from the file");
            System.out.println("7 for  view student list");
            System.out.println("8 for result showing menu");
            System.out.println("9 for Exit");
            System.out.print("Enter your choice:");
        }

        //This method check and display how many available seats in the current situation
        private static void availableSeats(){
            //calculate current available seats
            int currentSeats = 100 - countStudent;
            System.out.println("Current Available Seats:" + currentSeats);
            //checking if the seats are full
            if (countStudent >= 100){
                System.out.println("Now Seats Unavailable!!");
                return;
            }
        }
        /*This method use to register new student
        * In  this part user can register a student,but only if user want to save the
        student to the file system user should input 5,if not it will not save*/
        private static void studentRegister(){
            availableSeats();//showing available seats
            //first check if the seats are available
            if (countStudent >= 100){
                System.out.println("Now Seats Unavailable!!");
                return;
            }
            try {
                String studentID;
                String studentName;
                //lopping until get valid ID
                while (true) {
                    System.out.print("Enter student ID(8 characters, e.g.w2081234) or 'q' to cancel :");
                    studentID = input.next().toLowerCase();
                    //giving user a chance to go out without registering
                    if (studentID.equals("q")){
                        System.out.println("Registration cancelled ");
                        return;
                    }
                    //checking for valid format input or not
                    if (studentID.length() != 8 || !studentID.matches("w\\d{7}")) {
                        System.out.println("Invalid format for student ID");
                        continue;
                    }
                    //checking if ID already registered
                    if (isIDDuplicate(studentID)) { //calling the method isIDDuplicate(String studentID)
                        System.out.println("Student already registered!");
                        continue;
                    }
                    break;
                }
                input.nextLine();//clear invalid inputs
                //loop to get valid student name
                while (true) {
                    System.out.print("Enter student name:");
                    studentName = input.next();
                    //check if the name is not empty
                    if (studentName.isEmpty()) {
                        System.out.println("Student name cannot be empty");
                        continue;
                    }
                    boolean validity = true;
                    //check if the user input for name is valid
                    for (char c : studentName.toCharArray()){ //check if the character is a letter
                        if (!Character.isLetter(c) && c != ' '){
                            validity = false;
                            break;
                        }
                    }
                    if (validity){
                        break;
                    }else {
                        System.out.println("Give only letters.");
                    }
                }
                //checking the space to add any other student in the REGistration array
                if (countStudent < REGISTRATION.length){
                    //checking  curent position in array in null
                    if (REGISTRATION[countStudent] == null){
                        //making a new array with current position with  2 length Strings
                        REGISTRATION[countStudent] = new String[2];
                    }
                }
                //adding student to registration array
                REGISTRATION[countStudent][0] = studentID;
                REGISTRATION[countStudent][1] = studentName;
                countStudent++;
            //give user instructions to save
                System.out.println("Registered Successfully!.*If you want to save the registration go to save menu option.");
            }catch (Exception e){
                System.out.println("Error"+e);
            }
        }
        //This method use to check if a student ID is already registered
        private static boolean isIDDuplicate(String studentID){
            //looping through existing registrations
            for (int i =0; i< countStudent; i++){
                if (REGISTRATION[i][0].equals(studentID)){
                    return true ; //if ID found it return true
                }
            }
            return false;//if ID  not found it return false
        }
        /*This method delete the student from the student information file
        and delete from result file*/
        private static void studentDelete(){
            //check if there are any students to delete
            if (countStudent == 0){
                System.out.println("No students registered yet to delete");
            }
            //Display sorted list of students by calling the function
            studentDetailSortView();
            String deleteID;
            //loop to get a valid ID
            while (true) {
                System.out.print("To delete enter student ID(or 'q' to cancel):");
                deleteID = input.next().toLowerCase();
                //check if user enter q to cancel and handle the error to work this for both Q and q
                if (deleteID.equalsIgnoreCase("q")){
                    System.out.println("Canceled the deletion.");
                    return;
                }
                //check the valid format of ID
                if (deleteID.length() != 8 || !deleteID.matches("w\\d{7}")){
                    System.out.println("Invalid ID format.Enter valid format(e.g.,w2081234)");
                    continue;
                }
                break;
            }
            //search for the id in the Registration array
            boolean found = false;
            for (int i =0; i<countStudent; i++){
                if (REGISTRATION[i][0].equals(deleteID)){
                    found = true;

                    // ask user to confirm deletion
                    System.out.print("Sure you want to delete student "+REGISTRATION[i][1]+" ID:"+deleteID+" ?(y/n): ");
                    String confirm = input.next().toLowerCase();
                    if (!confirm.equals("y")){
                        System.out.println("Canceled the deletion.");
                        return;
                    }
                    //shift the elements to fill the gap which user enters
                    for (int j = i; j< countStudent -1; j++){
                        REGISTRATION[j] = REGISTRATION[j + 1];
                    }
                    //remove last element of the array and decrement the count
                    REGISTRATION[countStudent -1] =null;
                    countStudent--;
                    //delete the marks of the student
                    deleteStudentMarks(deleteID);
                    //save and update module marks
                    moduleMarksSave();

                    System.out.println("Deleted Successfully!!");
                    //save updated list to the life
                    studentSave();
                    return;
                }
            }
            //check if the id is not found
            if (!found) {
                System.out.println("Sorry.ID not found!!!");
            }
        }
        //This Method use for search students from their ID and give the name
        private static void studentFind(){
            while (true) {
                try {
                    System.out.print("To find enter student ID(or 'q' to cancel):");
                    String findID = input.next().toLowerCase();
                    //check user want to quit without search
                    if (findID.equalsIgnoreCase("q")){
                        System.out.println("Search canceled!");
                        return;
                    }
                    //check the ID format is valid
                    if (!findID.matches("w\\d{7}")){
                        System.out.println("Invalid ID format.Please use format 'w1234567'");
                        continue;
                    }
                    boolean found = false;
                    //iterating through elements and search for student id in the registration array
                    for (int i = 0; i < countStudent; i++) {
                        if (REGISTRATION[i][0].equals(findID)) {
                            System.out.println("Found: Student ID- " + REGISTRATION[i][0] + ", Student Name- " + REGISTRATION[i][1]);
                            found = true;
                            return;
                        }
                    }
                    if (!found) {
                        System.out.println("Sorry.ID not found!Try again or enter 'q' to quit.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.Please enter a valid ID.");
                    input.nextLine();//clear invalid inputs
                } catch (Exception e) {
                    System.out.println("Error occurred!"+e);
                }
            }
        }
        /*This Method save student details to the file
        * In above "student delete method" this method is called to save the changes
        * In the "student register method" this method is not called so if user want
         to save user should input 5 in the 1st menu and c in the second menu to save */
        private static void studentSave(){
            /*use try with resources statement to make sure the 'FileWriter' is closed automatically
            *new File writer create a file if not exists*/
            try(FileWriter writer = new FileWriter("savingStudents.txt")){
                //loop through each registered student up to the count of students
                for (int i =0; i<countStudent; i++){
                    //write the student ID and name to the file separated by comma
                    writer.write(REGISTRATION[i][0] +","+REGISTRATION[i][1]+"\n");
                }
                System.out.println("Saved Successfully!!");
                //handling error when file writing
            }catch (IOException e){
                System.out.println("Error: "+ e);
            }
        }
        //This method loads the student details from the file
        private static void studentDetailsLoad(){
            moduleMarksLoad();//load module marks first
            //reset the student count to zero
            countStudent= 0;
            //use try with resources statement to make sure 'BufferReader' is closed automatically
            try(BufferedReader reader = new BufferedReader(new FileReader("savingStudents.txt"))){
                String line;
                //read each line from file until the end of file/until the student array is full
                while ((line = reader.readLine()) != null && countStudent < 100){
                    //Split the line by comma to get student ID and name
                    String[] parts = line.split(",");
                    //populate the Registration array with student ID and name
                    REGISTRATION[countStudent][0] = parts[0];//Student ID
                    REGISTRATION[countStudent][1] = parts[1];//Student Name
                    //increment the student count
                    countStudent++;
                }
                System.out.println("Student detail loaded from file!");
            }catch (IOException e){
                System.out.println("Error"+e);
            }
        }
        //This method sorted the list of students according to Acceding order
        private static void studentDetailSortView(){
            //create a copy of Registration array for sorting
            String [][] studentsSort =new String[countStudent][2];
            for (int i =0; i<countStudent; i++){
                studentsSort[i] = REGISTRATION[i].clone();
            }
            //bubble sort algorithm to sort student by name
            //outer loop iterate through the array elements up to countStudent - 1
            for (int i =0; i< countStudent-1; i++){
                //inner loop - comparison and swap between adjacent elements
                //range reduces as the largest elements to the end of the array
                for (int j=0; j<countStudent-i-1; j++){
                    //  fpr ignoring the case compare the names of adjacent students
                    if (studentsSort[j][1].compareToIgnoreCase(studentsSort[j+1][1])>0){
                        /*If the new student name is greater than the next student
                          name swap  two names to sort in ascending order*/
                        //make temporary variable to hold newest student record
                        String[] tempo = studentsSort[j];
                        //equal the next student record newest position
                        studentsSort[j] = studentsSort[j+1];
                        //equal the sorted current student record to the next position
                        studentsSort[j+1]= tempo;
                    }
                }
            }
            //displaying sorted list
            System.out.println("\nStudent Sorted List:");
            for (int i =0; i<countStudent; i++){
                System.out.println("Name:"+studentsSort[i][1]+", ID:"+studentsSort[i][0]);
            }
        }
        //This method display and handle the result menu (2nd menu)
        private static void showResultMenu(){
            char choose = ' ';
            while (choose != 'f'){
                //Display result menu options
                System.out.println("_____Show Result Menu_____");
                System.out.println("a. for Check and Adding name");
                System.out.println("b. for Adding module marks");
                System.out.println("c. for Save details in to file");
                System.out.println("d. for Result summary");
                System.out.println("e. for Whole report");
                System.out.println("f. for Main menu");
                System.out.print("Enter what you want:");

                try {
                    //get user choice and ignore the case
                    choose = input.next().toLowerCase().charAt(0);

                    switch (choose) {
                        case 'a':
                            String id = checkAndAddingName();
                            if (id != null){
                                System.out.println("ID "+id+" is now ready to enter marks");
                            }
                            break;
                        case 'b':
                            while (true) {
                                try {
                                    System.out.print("Enter ID to add marks(or 'q' to cancel): ");
                                    String studentId = input.next().toLowerCase();

                                    if (studentId.equalsIgnoreCase("q")){
                                        System.out.println("Operation Canceled");
                                        break;
                                    }
                                    if (!studentId.matches("w\\d{7}")){
                                        System.out.println("Invalid ID format.Please use format 'w1234567'.");
                                        continue;
                                    }
                                    addModuleMarks(studentId);
                                    break;
                                }catch (InputMismatchException e){
                                    System.out.println("Invalid input.Enter valid ID");
                                    input.nextLine();//clear invalid input
                                }catch (Exception e){
                                    System.out.println("Error: "+e);
                                }
                            }break;
                        case 'c':
                            studentSave();
                            break;
                        case 'd':
                            resultSummary();
                            break;
                        case 'e':
                            wholeReport();
                            break;
                        case 'f':
                            System.out.println("Returning to the Main Menu");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                }catch (InputMismatchException e){
                    System.out.println("Invalid input. Please enter again(a,b,c,d,e or f):");
                    input.nextLine(); //clear invalid input
                }
            }
        }
        /*This method checking student if the student is existing
        * If student don't existing register the student now if the user want.
        * **In this user can 1st search by ID ,it will show the name if student is registered.
        * Here user can't search by name , because name can duplicate but id can't*/
        private static String checkAndAddingName(){
            while (true) {
                try {
                    System.out.print("Enter ID to check(or 'q' to cancel): ");
                    String id = input.next().toLowerCase();
                    //allowing canceling the operation
                    if (id.equalsIgnoreCase("q")) {
                        System.out.println("Cancelled checking");
                        return null;
                    }
                    //checking ID is in valid format
                    if (!id.matches("w\\d{7}")) {
                        System.out.println("Invalid ID Format.Please use 'w1234567'");
                        continue;
                    }
                    //checking if the id exists
                    boolean existsStudent = false;
                    for (int i = 0; i < countStudent; i++) {
                        if (REGISTRATION[i][0].equals(id)) {
                            existsStudent = true;
                            System.out.println("Found the Student: " + REGISTRATION[i][1]);
                            return id;
                        }
                    }
                    //if id not exists
                    if (!existsStudent) {
                        while (true) {
                            System.out.print("Student not founded.Do you want to register now?(y/n): ");
                            String respond = input.next().toLowerCase();
                            if (respond.equals("y")) {
                                //calling this method again to register student
                                studentRegister();
                                //after registration return newly registered student's ID
                                return REGISTRATION[countStudent - 1][0];
                            } else if (respond.equals("n")) {
                                System.out.println("Cancelled adding this student");
                                return null;//exit the method ,when no student was registered
                            } else {
                                System.out.println("Invalid Input. Enter 'y' for yes and 'n' for no.");
                            }
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid invalid.");
                    input.nextLine();//clear invalid inputs
                } catch (Exception e) {
                    System.out.println("Unexpected Error" + e);
                }
            }
        }
        /*This method add module marks if student is already registered.
        * If not give instructions to user to register the student */
        private static void addModuleMarks(String id){
            //check if the provided id is empty
            if (id == null || id.trim().isEmpty()){
                System.out.println("Invalid ID");
                return;
            }

            boolean foundStudent = false;//check if the student  is found
            String name = "";//variable to store the student's name if found
            for (int i =0; i< countStudent; i++){
                if (REGISTRATION[i][0].equals(id)){
                    name = REGISTRATION[i][1];//store student's name
                    foundStudent = true;//set the check flag to found
                    break;// exit the loop as student is found
                }
            }//if not found give user the instructions
            if (!foundStudent){
                System.out.println("Student ID " + id + " not found. Register first!(go to a)");
                return;
            }
            //find or create a student object with the given ID and name
            Student student = findOrCreate(id,name);
            try {
                //loop for add marks for 3 modules
                for (int i = 0; i < 3; i++) {
                    while (true) {
                        System.out.print("Enter Module " + (i + 1) + " Marks(0-100):");
                        //checking if the marks are integers
                        if (input.hasNextDouble()) {
                            double marks = input.nextDouble();
                            if (marks >=0  && marks <= 100) {
                                student.setModuleMarks(i,(int) marks);
                                break;//Exit the inner loop to move to next module
                            }else {
                                System.out.println("Invalid marks.Add 0 to 100");
                            }
                        }else {
                            System.out.println("Invalid input.Please enter numeric values.");
                            input.next();//clear invalid inputs from the scanner
                        }
                    }
                }
                //calling saving module marks method
                moduleMarksSave();
                System.out.println("Student marks added and updated!!");
            }catch (InputMismatchException e){
                System.out.println("Invalid input.Please enter numeric values.");
                input.nextLine();
            }catch (Exception e){
                System.out.println("Error:"+ e);
            }
        }
        //This method use for finding student and create a new student if the student not found
        private static Student findOrCreate(String id , String name){
            //iterate through the student array to find a student with given ID
            for (int i =0; i<countStudentMarks; i++){
                if (students[i].getId().equals(id)){
                    return students[i];//return the founded student
                }
            }
            //If student not found create a new student object
            Student newStudent = new Student(id, name);
            students[countStudentMarks] = newStudent;//add new student to the students array
            countStudentMarks++;//increment count of students with marks
            return newStudent;//return newly created student
        }
        //This method save module marks to a separate file called "marksStudent.txt"
        private static void moduleMarksSave(){
            try(FileWriter writer = new FileWriter("marksStudent.txt")){
                //iterate through the student array
                for (int i =0; i< countStudentMarks; i++){
                    Student student = students[i]; // get student object
                    writer.write(student.getId() + "," + student.getName());//write student ID and name to the file
                    //write the marks for each module
                    for (Module module : student.getModules()){
                        writer.write(","+module.getMarks());
                    }
                    writer.write("\n");//newline to separate entries for different student
                }
                System.out.println("Marking sheet updated successfully.");
            }catch (IOException e){
                System.out.println("Error in saving:"+ e);
            }
        }
        //This method load module marks from the file
        private static void moduleMarksLoad(){
            countStudentMarks = 0;//reset the count of students with marks
            try(BufferedReader reader = new BufferedReader(new FileReader("marksStudent.txt"))) {
                String lineRead;
                //read each line from the file
                while ((lineRead = reader.readLine()) != null && countStudentMarks < 100){
                    String[] part = lineRead.split(",");//split the line into parts
                    if (part.length == 5){//ensure there are exactly 5 parts (ID, Name, and 3 marks)
                        Student student = new Student(part[0],part[1]);//create a new student object
                        //analyse and set marks for each module
                        for (int i =0; i<3; i++){
                            student.setModuleMarks(i,Integer.parseInt(part[i+2]));
                            //student.modules[i].gradeCalculate();//calculate the grade based on marks
                        }
                        students[countStudentMarks]=student;//add the student to the students array
                        countStudentMarks++;//increment the count of students with marks
                    }
                }
                System.out.println("Marks loaded successfully.");
            }catch (IOException e){
                System.out.println("Error in loading"+e);
            }
        }
        //This method delete student mark
        private static void deleteStudentMarks(String id){
            //iterate through the students array to find the student with the given ID
            for (int i = 0; i<countStudentMarks; i++){
                if (students[i].getId().equals(id)){
                    //shift the remaining students up one position to fill the gap
                    for (int j = i; j < countStudentMarks-1; j++){
                        students[j] = students[j+1];
                    }
                    students[countStudentMarks -1] =null;//set the last element to null
                    countStudentMarks--;//decrement the count of student with marks
                    break;
                }
            }
        }
        //This method display the summary of the student results
        private static void resultSummary(){
            System.out.println("---Result Summary---");
            System.out.println("Total student registered:" + countStudent);//Total number of students registered
            System.out.println("Student updated module marks:"+ countStudentMarks);//Number of students with module marks

            int[] passedCount = new int[3];//Array to count students passing each module

            //iterate through student and count those who passed each module
            for (int i =0; i< countStudentMarks; i++){
                for (int j =0; j <3; j++){
                    if (students[i].getModules()[j].getMarks() >= 40){//A mark of 40 or higher is considered passing
                        passedCount[j]++;
                    }
                }
            }
            System.out.println("Number of student scored more than 40 :");
            //print the count of students passing each module
            for (int i = 0; i < 3;i++ ){
                System.out.println("Module "+(i+1)+":  "+ passedCount[i]);
            }
        }
        //This method use to show whole report of all students
        private  static void wholeReport(){
            System.out.println("---Whole Report---");
            availableSeats();
            System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s %-10s %-15s%n",
                    "ID","Name","Module 1","Module 2","Module 3","Total","Average","Grade");
            System.out.println("_____________________________________________________________________________________________");

            sortByBubbleSort(students,countStudentMarks);//sort the students before displaying the report
            //iterate through each student and display their details

            for (int i =0; i < countStudentMarks; i++){
                Student student = students[i];
                int total = 0;
                //calculate the total marks for the student
                for (Module module : student.getModules()){
                    total += module.getMarks();
                }
                double moduleAverage = total/3.0;//calculate the average marks
                moduleAverage = Math.round(moduleAverage*10.0)/10.0;
                String grade = overallGradeCal(moduleAverage);//calculate the overall grade
                //printing the student's details in a formatted manner with fixed-width columns
                System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s %-10s %-15s%n",
                        student.getId(), student.getName(),
                        student.getModules()[0].getMarks(),student.getModules()[1].getMarks(),student.getModules()[2].getMarks(),
                        total,moduleAverage,grade);
                System.out.println("_____________________________________________________________________________________________");
            }
        }
        /*This method sort the array of students based on
        their average marks using the bubble sorting*/
        private static void sortByBubbleSort(Student[] array, int n){
            for (int i =0; i< n-1; i++){//outer loop for the number of passes
                for (int j=0; j< n-i-1; j++){//inner loop for comparing adjacent elements
                    double average1 = averageCal(array[j]);//calculate the average marks for the current student
                    double average2 = averageCal(array[j +1]);//calculate the average marks for the next student
                    if (average1 < average2){//if the current student's average is less than the next student's average
                        Student tempo = array[j];//swap the students
                        array[j] = array[j +1];
                        array[j + 1] = tempo;
                    }
                }
            }
        }
        //This method calculate the average marks for a given student
        private static double averageCal(Student student){
            int total =0;//Initialize
            for (Module module : student.getModules()){//Iterate through each module
                total+= module.getMarks();//add the module marks to the total
            }
            return total/3.0;//return the average marks
        }
        //This method calculates the overall grade based on average marks
        private static String overallGradeCal(double average){
            if (average >= 80)return "Distinction";
            else if (average>= 70) return "Merit";
            else if (average>= 40) return "Pass";
            else return "Fail";
        }
}

