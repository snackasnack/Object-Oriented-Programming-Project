package mystars.commands.shared;

import mystars.commands.Command;
import mystars.data.exception.MyStarsException;
import mystars.data.user.UserList;
import mystars.ui.Ui;

import java.time.LocalDateTime;

/**
 * Parent shared command class.
 */
public abstract class SharedCommand extends Command {

    static final String ERROR_MESSAGE = "Invalid username/password!";

    /**
     * Executes command.
     *
     * @param accessDateTime Access period.
     * @param users          UserList object.
     * @param ui             Ui object.
     * @throws MyStarsException If there is issue executing command.
     */
    public abstract void execute(LocalDateTime[] accessDateTime, UserList users, Ui ui)
            throws MyStarsException;

}
