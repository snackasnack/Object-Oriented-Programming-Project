package mystars.commands.student;

import mystars.MyStars;
import mystars.data.course.Course;
import mystars.data.course.CourseList;
import mystars.data.exception.MyStarsException;
import mystars.data.sender.Sender;
import mystars.data.user.Student;
import mystars.storage.Storage;
import mystars.ui.StudentUi;

import java.util.logging.Level;


/**
 * Drops course for student.
 */
public class DropCourseCommand extends StudentCommand {

    /**
     * Command word to trigger this command.
     */
    public static final String COMMAND_WORD = "2";

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
    public DropCourseCommand(StudentUi ui, CourseList courses, Storage storage) {
        super(ui);
        this.courses = courses;
        this.storage = storage;
    }

    /**
     * Prompts student for course to drop, and saves changes to file.
     *
     * @throws MyStarsException If there is issue executing command.
     */
    @Override
    public void execute() throws MyStarsException {
        String indexNumber = ui.getIndexNumber();
        courses.checkIndexNoInList(indexNumber);

        Course course = courses.getCourseByIndex(indexNumber);
        Student student = (Student) getUser();

        if (student.isCourseInRegistered(course)) {
            student.dropRegisteredCourse(course);
            course.dropRegisteredStudent(student);
            ui.showDroppedCourse(course, REGISTERED);
        } else if (student.isCourseInWaitlisted(course)) {
            student.dropWaitlistedCourse(course);
            course.dropWaitlistedStudent(student);
            ui.showDroppedCourse(course, WAITLISTED);
        } else {
            throw new MyStarsException(COURSE_NOT_FOUND_ERROR);
        }

        if (course.checkWaitlist()) {
            MyStars.logger.log(Level.INFO, Sender.SEND_MESSAGE);
            ui.showEmailSent();
        }

        storage.saveCourses(courses);
    }
}
