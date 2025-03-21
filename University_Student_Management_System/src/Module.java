//This class represents a module,marks and grade.
public class Module {
    private int marks;//The marks obtained in the module
    private String grade;//grade associated with the marks

    //Method to set the marks for the module and calculate the corresponding grade
    public void setMarks(int marks) {
        this.marks = marks;//assign the provided marks to mark field
        calculateGrade();//calculate the grade based on new marks
    }
    //Method to get marks of module
    public int getMarks() {
        return marks;//return the current marks
    }
    //Method to get grade of module
    public String getGrade() {
        return grade;//return current grade
    }
    //Private method to calculate the grade based on marks
    public void calculateGrade(){
        if (marks >= 80) grade ="Distinction";
        else if (marks >= 70) grade = "Merit";
        else if (marks >= 40) grade = "Pass";
        else grade = "Fail";

    }
}
