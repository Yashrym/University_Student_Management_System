//This class represent a student and their details
public class Student {
    private final String id;//The student's ID
    private final String name;//The student's name
    private final Module[] modules;//array of modules taken by the student
    //initialize the student with an ID and name
    Student(String id, String name){
        this.id=id;//assign the provided ID to the student's ID field
        this.name = name;//assign the provided name to the student's name field
        this.modules = new Module[3];//initial the modules array with a size of 3

        //loop to initialize each element of the modules array with a new module object
        for (int i = 0; i< 3; i++){
            this.modules[i] = new Module();
        }
    }
    //getter method for the student's ID
    public String getId() {
        return id;
    }
    //getter method for the student's name
    public String getName() {
        return name;
    }
    //getter method for the modules array
    public Module[] getModules() {
        return modules;
    }
    //method to set the marks for a specific module
    public void setModuleMarks(int moduleIndex, int marks){
        //Ensure the module index is within the valid range
        if (moduleIndex >=0 && moduleIndex < 3){
            modules[moduleIndex].setMarks(marks);//set the marks for the specified module
        }else {
            //Throw an exception if the module index is invalid
            throw new IllegalArgumentException("Invalid module index");
        }
    }
}