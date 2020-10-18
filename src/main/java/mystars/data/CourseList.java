package mystars.data;

import mystars.data.course.Course;

import java.util.ArrayList;

public class CourseList {

    private final ArrayList<Course> courses;

    public CourseList() {
        courses = new ArrayList<>();
    }

    public CourseList(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Calculates the total number of AUs for a list of courses.
     * Used for CourseList defined in Student objects to check for total AUs restrictions.
     * @return Total number of AUs of a list of courses.
     */
    public int getTotalNoOfAUs() {
        return courses.stream().mapToInt(Course::getNumOfAUs).sum();
    }

    @Override
    public String toString() {
        StringBuilder coursesString = new StringBuilder();
        for (int i = 1; i <= courses.size(); i++) {
            coursesString.append("#").append(i).append("\n").append(courses.get(i - 1).toString());
        }
        return "Total No. of Courses Registered: " + courses.size() + "\n"
        + coursesString.toString();
    }

    public boolean isCourseInList(Course courseToCheck) {
        for (Course course: courses) {
            if (courseToCheck.isSameCourseCode(course)) {
                return false;
            }
        }
        return true;
    }

    public void addCourse(Course courseToAdd) {
        courses.add(courseToAdd);
    }
}
