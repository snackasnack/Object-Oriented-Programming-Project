package mystars.storage;

import mystars.MyStars;
import mystars.data.course.Course;
import mystars.data.course.CourseList;
import mystars.data.exception.MyStarsException;
import mystars.data.sender.Sender;
import mystars.data.user.Admin;
import mystars.data.user.Student;
import mystars.data.user.User;
import mystars.data.user.UserList;
import mystars.parser.Parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Storage handler.
 */
public class Storage {

    /**
     * File not found error message.
     */
    private static final String FILE_NOT_FOUND_ERROR = "I am unable to find file. Things may not work as expected. "
            + "Please ensure db folder is present with the respective text files.";

    /**
     * Reading error message.
     */
    private static final String READ_ERROR = "I am unable to read file. Things may not work as expected. "
            + "Please ensure db folder is present with the respective text files.";

    /**
     * Directory error message.
     */
    private static final String DIRECTORY_ERROR = "I am unable to create directory. Things may not work as expected. "
            + "Please ensure db folder is accessible to program.";

    /**
     * Write error message.
     */
    private static final String WRITE_ERROR = "I am unable to write file. Things may not work as expected. "
            + "Please ensure db folder is accessible to program.";

    /**
     * Vacancy error message.
     */
    private static final String VACANCY_ERROR = "No more vacancy to put student! Things may not work as expected. "
            + "Please ensure enough vacancy is assigned to course.";

    /**
     * Invalid AUs error message.
     */
    private static final String INVALID_AU_ERROR = "Same course code have different number of AUs! "
            + "Things may not work as expected. Please ensure same course code have the same number of AUs.";

    /**
     * Settings format string.
     */
    private static final String SETTINGS_FORMAT = "format: start datetime|end datetime";

    /**
     * Courses format string.
     */
    private static final String COURSES_FORMAT = "format: course code|school|index number|vacancy|number of AUs|"
            + "Lesson1*Lesson2*... (refer to Lesson.java for format)";

    /**
     * Registered format string.
     */
    private static final String REGISTERED_FORMAT = "format: index number|student1 matric no.|student2...";

    /**
     * Waitlisted format string.
     */
    private static final String WAITLISTED_FORMAT = REGISTERED_FORMAT;

    /**
     * Folder path.
     */
    private static final String FOLDER = "db";

    /**
     * User file.
     */
    private static final String USERS_FILE = "users.txt";

    /**
     * Student file.
     */
    private static final String STUDENTS_FILE = "students.txt";

    /**
     * Admin file.
     */
    private static final String ADMINS_FILE = "admins.txt";

    /**
     * Course file.
     */
    private static final String COURSES_FILE = "courses.txt";

    /**
     * Setting file.
     */
    private static final String SETTINGS_FILE = "settings.txt";

    /**
     * Registered file.
     */
    private static final String REGISTERED_FILE = "registered.txt";

    /**
     * Waitlisted file.
     */
    private static final String WAITLISTED_FILE = "waitlist.txt";

    /**
     * Parser object.
     */
    private final Parser parser;

    /**
     * Initializes storage handler.
     *
     * @param parser Parser object.
     */
    public Storage(Parser parser) {
        this.parser = parser;
    }

    /**
     * Loads users, stores them into ArrayList and returns the ArrayList.
     *
     * @return ArrayList of users.
     * @throws MyStarsException If there is problem reading file.
     */
    public ArrayList<User> loadUsers() throws MyStarsException {
        Path path = Paths.get(FOLDER, USERS_FILE);
        ArrayList<User> users = new ArrayList<>();

        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }

                    User user = parser.readUser(line);
                    users.add(user);
                }
            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }

        return users;
    }

    /**
     * Loads students, stores them into ArrayList and returns the ArrayList.
     *
     * @return ArrayList of students.
     * @throws MyStarsException If there is problem reading file.
     */
    public ArrayList<User> loadStudents() throws MyStarsException {
        Path path = Paths.get(FOLDER, STUDENTS_FILE);
        ArrayList<User> students = new ArrayList<>();

        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }

                    Student student = parser.readStudent(line);
                    students.add(student);
                }
            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }

        return students;
    }

    /**
     * Loads admins, stores them into ArrayList and returns the ArrayList.
     *
     * @return ArrayList of admins.
     * @throws MyStarsException If there is problem reading file.
     */
    public ArrayList<User> loadAdmins() throws MyStarsException {
        Path path = Paths.get(FOLDER, ADMINS_FILE);
        ArrayList<User> admins = new ArrayList<>();

        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }

                    Admin admin = parser.readAdmin(line);
                    admins.add(admin);
                }
            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }

        return admins;
    }

    /**
     * Loads registered students, and updates course and student about the registration.
     *
     * @param courses List of courses.
     * @param users   List of users.
     * @throws MyStarsException If there is issue loading file.
     */
    public void loadCourseRegisteredStudents(CourseList courses, UserList users) throws MyStarsException {
        Path path = Paths.get(FOLDER, REGISTERED_FILE);

        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    String courseIndex = parser.readCourseIndex(line);
                    Course course = courses.getCourseByIndex(courseIndex);
                    ArrayList<Student> students = parser.readStudentList(line, users);

                    if (course.isVacancy()) {
                        for (Student student : students) {
                            student.addCourseToRegistered(course);
                        }
                        course.setRegisteredStudents(students);

                    } else {
                        throw new MyStarsException(VACANCY_ERROR);
                    }
                }
            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }
    }

    /**
     * Loads waitlisted students, and updates course and student about the waitlist.
     *
     * @param courses List of courses.
     * @param users   List of users.
     * @throws MyStarsException If there is issue loading file.
     */
    public void loadCourseWaitlistStudents(CourseList courses, UserList users) throws MyStarsException {
        Path path = Paths.get(FOLDER, WAITLISTED_FILE);

        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    String courseIndex = parser.readCourseIndex(line);
                    Course course = courses.getCourseByIndex(courseIndex);
                    ArrayList<Student> students = parser.readStudentList(line, users);

                    for (Student student : students) {
                        student.addCourseToWaitlisted(course);
                    }
                    course.setWaitlistedStudents(students);

                    if (course.checkWaitlist()) {
                        MyStars.logger.log(Level.INFO, Sender.SEND_MESSAGE);
                        System.out.println(Sender.SEND_MESSAGE);
                    }

                    saveCourses(courses);
                }
            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }
    }

    /**
     * Loads courses, stores them into ArrayList and returns the ArrayList.
     *
     * @return ArrayList of courses.
     * @throws MyStarsException If there is problem reading file.
     */
    public ArrayList<Course> loadCourses() throws MyStarsException {
        Path path = Paths.get(FOLDER, COURSES_FILE);
        ArrayList<Course> courses = new ArrayList<>();

        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }

                    Course course = parser.readCourse(line);
                    if (!courses.stream().allMatch(course::isValidNumOfAUs)) {
                        throw new MyStarsException(INVALID_AU_ERROR);
                    }
                    courses.add(course);
                }
            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }

        return courses;
    }

    /**
     * Loads access period, stores them into an array and returns the array.
     *
     * @return Access period, null if file does not exist.
     * @throws MyStarsException If there is problem reading file.
     */
    public LocalDateTime[] loadAccessPeriod() throws MyStarsException {
        Path path = Paths.get(FOLDER, SETTINGS_FILE);
        if (Files.exists(path)) {
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                bufferedReader.readLine();

                String line = bufferedReader.readLine();

                return parser.readStudentAccessPeriod(line);

            } catch (IOException e) {
                throw new MyStarsException(READ_ERROR);
            }
        } else {
            throw new MyStarsException(FILE_NOT_FOUND_ERROR);
        }
    }

    /**
     * Saves courses to file.
     *
     * @param courses Courses to save.
     * @throws MyStarsException If there is issue saving to file.
     */
    public void saveCourses(CourseList courses) throws MyStarsException {
        String coursesString = courses.getCourses().stream().map(Course::getStorageString)
                .collect(Collectors.joining(System.lineSeparator()));

        String coursesFileContent = COURSES_FORMAT + System.lineSeparator() + coursesString;
        writeToFile(coursesFileContent, COURSES_FILE);

        String registeredString = courses.getCourses().stream().filter(Course::isThereRegisteredStudents)
                .map(Course::getRegisteredFormattedString).collect(Collectors.joining(System.lineSeparator()));

        String registeredFileContent = REGISTERED_FORMAT + System.lineSeparator() + registeredString;
        writeToFile(registeredFileContent, REGISTERED_FILE);

        String waitlistedString = courses.getCourses().stream().filter(Course::isThereWaitlistedStudents)
                .map(Course::getWaitlistedFormattedString).collect(Collectors.joining(System.lineSeparator()));

        String waitlistedFileContent = WAITLISTED_FORMAT + System.lineSeparator() + waitlistedString;
        writeToFile(waitlistedFileContent, WAITLISTED_FILE);
    }

    /**
     * Saves access period to file.
     *
     * @param accessPeriod Access period.
     * @throws MyStarsException If there is issue saving file.
     */
    public void saveAccessPeriod(LocalDateTime[] accessPeriod) throws MyStarsException {
        String accessPeriodString = SETTINGS_FORMAT + System.lineSeparator() + accessPeriod[0]
                + Parser.LINE_SEPARATOR + accessPeriod[1];
        writeToFile(accessPeriodString, SETTINGS_FILE);
    }

    /**
     * Saves new student to file.
     *
     * @param newStudent Student to append to file.
     * @throws MyStarsException If there is issue saving file.
     */
    public void saveStudent(Student newStudent) throws MyStarsException {
        appendToFile(newStudent.getFormattedString(), STUDENTS_FILE);
        appendToFile(newStudent.getFormattedUserInfo(), USERS_FILE);
    }

    /**
     * Writes content to file.
     *
     * @param fileContent String content to write.
     * @param file        Filename to write to.
     * @throws MyStarsException If there is problem writing files.
     */
    private void writeToFile(String fileContent, String file) throws MyStarsException {
        Path folderPath = Paths.get(FOLDER);
        if (!Files.exists(folderPath) && !new File(FOLDER).mkdir()) {
            throw new MyStarsException(DIRECTORY_ERROR);
        }

        Path filePath = Paths.get(FOLDER, file);
        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath);
            bufferedWriter.write(fileContent + System.lineSeparator());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new MyStarsException(WRITE_ERROR);
        }
    }

    /**
     * Appends content to file.
     *
     * @param fileContent String content to write.
     * @param file        Filename to write to.
     * @throws MyStarsException If there is problem writing files.
     */
    private void appendToFile(String fileContent, String file) throws MyStarsException {
        Path folderPath = Paths.get(FOLDER);
        Path filePath = Paths.get(FOLDER, file);

        if (!(Files.exists(folderPath) && Files.exists(filePath))) {
            writeToFile(fileContent, file);
        } else {
            try {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND);
                bufferedWriter.write(fileContent + System.lineSeparator());
                bufferedWriter.close();
            } catch (IOException e) {
                throw new MyStarsException(WRITE_ERROR);
            }
        }
    }
}
