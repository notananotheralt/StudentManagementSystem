public class Course {
    private String courseName;
    private String instructorName;
    private String location;
    private String courseTime;

    // constructer to initialize tho course object with below info
    public Course(String courseName, String instructorName, String location, String courseTime) {
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.location = location;
        this.courseTime = courseTime;
    }

    // setter and getter for course name
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // setter and getter for instructor name
    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    // setter and getter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // setter and getter for course times
    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    // print out relevant info about the course
    @Override
    public String toString() {
        return "Course: " + courseName + ", Instructor: " + instructorName + ", Location: " + location + ", Time: "
                + courseTime;
    }
}
