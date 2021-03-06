package mystars.commands.student;

import mystars.data.course.Course;
import mystars.data.course.CourseList;
import mystars.data.exception.MyStarsException;
import mystars.data.user.Student;
import mystars.storage.Storage;
import mystars.ui.StudentUi;

/**
 * Adds course for student.
 */
public class AddCourseCommand extends StudentCommand {

    /**
     * Command word to trigger this command.
     */
    public static final String COMMAND_WORD = "1";

    /**
     * List of courses.
     */
    private final CourseList courses;

    /**
     * Storage handler.
     */
    private final Storage storage;

    /**
     * Initializes command for execution.
     *
     * @param ui      Ui object.
     * @param courses List of courses.
     * @param storage Storage handler.
     */
    public AddCourseCommand(StudentUi ui, CourseList courses, Storage storage) {
        super(ui);
        this.courses = courses;
        this.storage = storage;
    }

    /**
     * Prompts user for course to add and saves to file.
     *
     * @throws MyStarsException If there is issue executing command.
     */
    @Override
    public void execute() throws MyStarsException {
        String indexNumber = ui.getIndexNumber();
        courses.checkIndexNoInList(indexNumber);

        Course course = courses.getCourseByIndex(indexNumber);
        Student student = (Student) getUser();

        if (course.isVacancy()) {
            student.addCourseToRegistered(course);
            course.addRegisteredStudent(student);
            ui.showAddedCourse(course, REGISTERED);
        } else {
            student.addCourseToWaitlisted(course);
            course.addWaitlistedStudent(student);
            ui.showAddedCourse(course, WAITLISTED);
        }

        storage.saveCourses(courses);
    }
}
