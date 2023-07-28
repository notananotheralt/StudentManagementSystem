public class Person {
    private String firstName;
    private String lastName;
    private int age;

    // constructer to initialize the object
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // getter and setter for first name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // setter and getter for last
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // setter and getter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // print out the info needed about the person
    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + ", Age: " + age;
    }
}