public class StudentTest {
    public static void main(String[] args) {
        Course sdev = new Course("Software Development - Java, ", "Prof. Eckstein", "Room 101", "8:00 PM - 9:30 PM");
        Course adev = new Course("Algorithims - C++, ", "Prof. Einstein", "Room 711", "8:00 AM - 9:30 AM");

        Student dyang = new Student("727WYSI", "Derek", "Yang", 15);

        dyang.addEnrolledCourse(sdev);
        dyang.addEnrolledCourse(adev);

        System.out.println("Student Info:");
        System.out.println("Student Name: " + dyang.getFirstName() + " " + dyang.getLastName());
        System.out.println("Student Age:" + dyang.getAge());
        System.out.println("STUDENT ID: " + dyang.getStudentID());
        System.out.println("Enrolled Courses:");

        for (Course course : dyang.getEnrolledCourses()) {
            System.out.println(course);
        }
    }
}