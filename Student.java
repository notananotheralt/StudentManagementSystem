import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentID;
    private List<Course> enrolledCourses;
    private String firstName;
    private String lastName;
    private int age;

    /// constructor to initialize the student object with info down below
    public Student(String studentID, String firstName, String lastName, Integer age) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.enrolledCourses = new ArrayList<>();
    }

    // getter and setter methods

    // student id
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // courses they are in
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addEnrolledCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void removeEnrolledCourse(Course course) {
        enrolledCourses.remove(course);
    }

    // their first name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // their last name
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // their age
    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
