package mystars.commands.admin;

import mystars.data.course.CourseList;
import mystars.data.exception.MyStarsException;
import mystars.data.user.UserList;
import mystars.ui.AdminUi;
import mystars.ui.Ui;

public class PrintListByIndexCommand extends AdminCommand {

    public static final String COMMAND_WORD = "5";

    private final CourseList courses;
    private final UserList users;

    public PrintListByIndexCommand(Ui ui, CourseList courses, UserList users) {
        super((AdminUi) ui);
        this.courses = courses;
        this.users = users;
    }

    /**
     * Executes command.
     *
     * @throws MyStarsException If there is issue executing command.
     */
    @Override
    public void execute() throws MyStarsException {
        String indexNumber = ui.getIndexNumber();
        courses.checkIndexNoInList(indexNumber);
        ui.showStudentListByIndex(users, indexNumber);
    }
}
