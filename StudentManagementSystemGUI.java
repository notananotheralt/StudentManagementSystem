import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystemGUI extends Application {
    private List<Student> students;
    private List<Course> courses;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        students = new ArrayList<>();
        courses = new ArrayList<>();

        primaryStage.setTitle("Student Management System");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        // Labels
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label ageLabel = new Label("Age:");
        Label courseLabel = new Label("Courses:");
        Label idLabel = new Label("Student's ID");

        // Text fields
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField ageField = new TextField();
        TextField idField = new TextField();
        ComboBox<Course> courseComboBox = new ComboBox<>();
        courseComboBox.setItems(FXCollections.observableArrayList(courses));

        // Buttons
        Button saveButton = new Button("Save Data");
        Button loadButton = new Button("Load Data");
        Button addStudentButton = new Button("Add Student");
        Button addCourseButton = new Button("Add Course");
        Button viewStudentButton = new Button("View Students");
        Button viewCourseButton = new Button("View Courses");
        Button enrollStudentButton = new Button("Enroll Student");
        Button viewStudentInfoButton = new Button("Student Information");

        // Events

        // saves data
        saveButton.setOnAction(e -> {
            saveStudentsToFile();
            saveCoursesToFile();
        });

        // loads data
        loadButton.setOnAction(e -> {
            loadCoursesFromFile();
            loadStudentsFromFile();
        });

        // view student info
        viewStudentInfoButton.setOnAction(e -> {
            String sID = idField.getText();
            String fName = "";
            String lName = "";
            StringBuilder sCourses = new StringBuilder();
            // matches up inputted id to students to grab attributes
            for (Student student : students) {
                if (student.getStudentID().equals(sID)) {
                    fName = student.getFirstName();
                    lName = student.getLastName();
                    List<Course> c = student.getEnrolledCourses();
                    for (Course course : c) {
                        sCourses.append(course).append("\n");
                    }
                    // output info
                    if (sID.isEmpty()) {
                        showAlert("Error", "Please enter valid information.");
                    } else {
                        showAlert("Student Info", "Student ID: " + sID + "\n" + "Student Name: " + fName + " " + lName
                                + "\n" + "Student Courses: \n" + sCourses);
                    }
                    break;
                }
                showAlert("Error", "Please enter valid information.");
                break;
            }
        });

        // adds a student
        addStudentButton.setOnAction(e -> {
            String fname = firstNameField.getText();
            String lname = lastNameField.getText();
            String ageStr = ageField.getText();

            if (fname.isEmpty() || lname.isEmpty() || ageStr.isEmpty()) {
                showAlert("Error", "Please enter valid information.");
                return;
            }

            try {
                int age = Integer.parseInt(ageStr);
                Student student = new Student(generateStudentID(), fname, lname, age);
                students.add(student);
                showAlert("Success", "Student added successfully.");
                firstNameField.clear();
                lastNameField.clear();
                ageField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid age format. Please enter a number.");
            }
        });

        // adds course
        addCourseButton.setOnAction(e -> {
            Stage courseStage = new Stage();
            courseStage.setTitle("Add Course");

            VBox courseLayout = new VBox(10);
            courseLayout.setPadding(new Insets(10));

            Label courseNameLabel = new Label("Course Name:");
            Label instructorLabel = new Label("Instructor:");
            Label locationLabel = new Label("Location:");
            Label courseTimeLabel = new Label("Course Time:");

            TextField courseNameField = new TextField();
            TextField instructorField = new TextField();
            TextField locationField = new TextField();
            TextField courseTimeField = new TextField();

            Button saveCourseButton = new Button("Save Course");
            saveCourseButton.setOnAction(event -> {
                String courseName = courseNameField.getText();
                String instructor = instructorField.getText();
                String location = locationField.getText();
                String courseTime = courseTimeField.getText();

                if (courseName.isEmpty() || instructor.isEmpty() || location.isEmpty() || courseTime.isEmpty()) {
                    showAlert("Error", "Please enter valid course information.");
                    return;
                }

                Course course = new Course(courseName, instructor, location, courseTime);
                courses.add(course);
                courseComboBox.setItems(FXCollections.observableArrayList(courses));
                showAlert("Success", "Course added successfully.");
                courseStage.close();
            });

            courseLayout.getChildren().addAll(
                    courseNameLabel, courseNameField,
                    instructorLabel, instructorField,
                    locationLabel, locationField,
                    courseTimeLabel, courseTimeField,
                    saveCourseButton);

            Scene courseScene = new Scene(courseLayout, 350, 300);
            courseStage.setScene(courseScene);
            courseStage.show();
        });

        // check what students exist
        viewStudentButton.setOnAction(e -> {
            if (students.isEmpty()) {
                showAlert("Information", "No students added yet.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Student student : students) {
                    sb.append(student.getStudentID()).append("\n");
                    System.out.println(student.getStudentID());
                }
                showAlert("Students", sb.toString());
            }
        });

        // check what courses exist
        viewCourseButton.setOnAction(e -> {
            if (courses.isEmpty()) {
                showAlert("Information", "No courses added yet.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Course course : courses) {
                    sb.append(course).append("\n");
                }
                showAlert("Courses", sb.toString());
            }
        });

        // adds a new course
        enrollStudentButton.setOnAction(e -> {
            Student selectedStudent = getSelectedStudent(idField.getText());
            Course selectedCourse = courseComboBox.getValue();

            if (selectedStudent == null || selectedCourse == null) {
                showAlert("Error", "Please select a student and a course.");
            } else {
                selectedStudent.addEnrolledCourse(selectedCourse);
                showAlert("Success", "Student enrolled in the course successfully.");
            }
        });

        // Layout
        HBox buttonBox = new HBox(10, addStudentButton, addCourseButton, viewStudentButton, viewCourseButton,
                viewStudentInfoButton);
        HBox enrollBox = new HBox(10, courseLabel, courseComboBox, enrollStudentButton);
        HBox saveBox = new HBox(10, saveButton, loadButton);
        grid.add(firstNameLabel, 0, 0);
        grid.add(lastNameLabel, 0, 1);

        grid.add(firstNameField, 1, 0);
        grid.add(lastNameField, 1, 1);

        grid.add(ageLabel, 0, 2);
        grid.add(ageField, 1, 2);

        grid.add(buttonBox, 0, 3, 2, 1);
        grid.add(idLabel, 0, 4);
        grid.add(idField, 1, 4);
        grid.add(enrollBox, 0, 5, 2, 1);
        grid.add(saveBox, 0, 6, 2, 1);

        Scene scene = new Scene(grid, 650, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // creates id based off time
    private String generateStudentID() {
        return "S" + System.currentTimeMillis();
    }

    // saves current data of students such as classes, name, etc
    private void saveStudentsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("students.csv"))) {
            for (Student student : students) {
                StringBuilder enrolledCourses = new StringBuilder();
                for (Course course : student.getEnrolledCourses()) {
                    enrolledCourses.append(course.getCourseName()).append(",");
                }
                // Remove the last comma from the enrolled courses list
                if (enrolledCourses.length() > 0) {
                    enrolledCourses.setLength(enrolledCourses.length() - 1);
                }

                pw.println(student.getStudentID() + "," + student.getFirstName() + "," +
                        student.getLastName() + "," + student.getAge() + "," + enrolledCourses.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save students data.");
        }
    }

    // loads that data
    private void loadStudentsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String id = data[0];
                    String firstName = data[1];
                    String lastName = data[2];
                    int age = Integer.parseInt(data[3]);
                    String[] enrolledCourses = data[4].split(",");

                    Student student = new Student(id, firstName, lastName, age);
                    for (String courseName : enrolledCourses) {
                        // Find the Course object from the courses list using the courseName
                        for (Course course : courses) {
                            if (course.getCourseName().equals(courseName)) {
                                student.addEnrolledCourse(course);
                                break;
                            }
                        }
                    }
                    students.add(student);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load students data.");
        }
    }

    // saves info about courses like teacher and time etc
    private void saveCoursesToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("courses.csv"))) {
            for (Course course : courses) {
                pw.println(course.getCourseName() + "," + course.getInstructorName() + "," + course.getLocation() + ","
                        + course.getCourseTime());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save courses data.");
        }
    }

    // loads saved data
    private void loadCoursesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("courses.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String courseName = data[0];
                    String instructorName = data[1];
                    String location = data[2];
                    String courseTime = data[3];
                    courses.add(new Course(courseName, instructorName, location, courseTime));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load courses data.");
        }
    }

    // custom alert function
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // using ID get the object of the student
    private Student getSelectedStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null; // Student with the specified ID not found
    }

}
