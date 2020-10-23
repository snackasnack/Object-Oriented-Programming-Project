package mystars.ui;

import mystars.data.user.Student;
import mystars.parser.Parser;

public class StudentUi extends Ui {

    public StudentUi(Parser parser) {
        super(parser);
    }

    public StudentUi() {

    }

    @Override
    public void showMenu() {
        printNicely("1. Add Course");
        printNicely("2. Drop Course");
        printNicely("3. Check/Print Courses Registered");
        printNicely("4. Check Vacancies Available");
        printNicely("5. Change Index Number of Course");
        printNicely("6. Swop Index Number with Another Student");
        printNicely("7. Logout");
        printNicely("Please select an item:");
    }

    @Override
    public void greetUser() {
        printNicely("Hello student!");
    }

    public void printRegCourses(Student student) {
        printNicely("Here are your registered courses:");
        printNicely(student.getRegisteredCourses().toString());
    }

    public void showAddCourse() {
        printNicely("adding...");
    }
}
