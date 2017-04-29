/**
 * ClassData represents the class, and all useful information that can aid in determining
 * @author Cormac
 * @since 4/25/17
 */
public class CourseData {

    // FIELDS
    private String name;        // only used when printing the results
    private String course;      // only used when printing the results
    private String professor;   // only used when printing the results
    private int credits;
    private int availability;
    private int desirability;
    private String[] dates;
    private int[] times;
    private CourseData[] preReqs = null;

    // CONSTRUCTOR

    /**
     * Constructor for ClassData
     *
     * @param className    the name of the course (eg "Calculus II for engineers")
     * @param course       the department and the course number(eg "MATH 122")
     * @param professor    the course professor (eg "Chris Butler")
     * @param credits      the number of credit hours the class is worth (eg 3)
     * @param availability the course semester availability (eg 1 for spring, 0 for fall, 2 for both)
     * @param desirability the course desirability (eg 1-10)
     * @param dates        the dates the course is on (eg ["mo", "tu", "we", "th", "fr"])
     * @param times        the times the course is at. Flip flop between stat times and stop times beginning with
     *                     start time. Each time start/stop pair corresponds to the date array at the subsequent
     *                     location(eg [0830,0920,0830,0920,0830,0920])
     * @param preReqs      the pre requisites to this class
     */
    public CourseData(String className, String course, String professor, int credits, int availability, int desirability,
                      String[] dates, int[] times, CourseData[] preReqs) {
        this.name = className;
        this.course = course;
        this.professor = professor;
        this.credits = credits;
        this.availability = availability;
        this.desirability = desirability;
        this.dates = dates;
        this.times = times;
        if (dates.length / times.length != .5){
            System.out.println("Dates and Times do not correspond correctly!");
        }
        if (preReqs.length != 0){ // if there are no pre requirements
            this.preReqs = preReqs;
        }
    }

    // WORKING METHODS

    /**
     * Prints out a string of the course info
     * @return "Math 122: Calculus for Engineers   |   Chris Butler   |   []"
     */
    @Override
    public String toString(){
        String out;
        out = getCourse() + ": " + getName() + "\t|\t" + getProfessor() + "\t|\t";
        for (int index = 0, pair = 0; index < this.getDates().length -1 ; index++, pair +=2){
            out = out + "[" + this.getDates()[index] +  "@ " + getTimes()[pair] + " - " + getTimes()[pair] + "]\n";
        }
        return out;
    }

    /**
     * Checks to see if the inputed course matches this course
     * @param courseData The course you want to compare to
     * @return boolean of if it's equal
     */
    private boolean equals(CourseData courseData) {
        /* if the courses have the same name, professor, date, and time*/
        return courseData.getCourse().equals(this.getCourse()) &&
                courseData.getProfessor().equals(this.getProfessor()) &&
                (equalDays(courseData) && equalTimes(courseData));
    }

    /**
     * Helper to equals to check if it's on the same day
     * @param courseData The course you want to compare to
     * @return boolean of if it's equal
     */
    private boolean equalDays(CourseData courseData){
        if (courseData.getDates().length == this.getDates().length) {
            /* the goal of the loop is to determine whether the days are equal */
            for (int index = 0; index < this.getDates().length -1 ; index++){
                if (!courseData.getDates()[index].equals(this.getDates()[index])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     *  Helper to equals to check if the times are the same
     * @param courseData The course you want to compare to
     * @return boolean of if it's equal
     */
    private boolean equalTimes(CourseData courseData){
        if (courseData.getTimes().length == this.getTimes().length) {
            /* the goal of the loop is to determine wether the times are equal */
            for (int index = 0; index < this.getTimes().length -1 ; index++){
                if (courseData.getTimes()[index] != this.getTimes()[index]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // GETTERS / SETTERS
    private String getName() {
        return name;
    }

    private String getCourse() {
        return course;
    }

    private String getProfessor() {
        return professor;
    }

    private int getCredits() {
        return credits;
    }

    private int getAvailability() {
        return availability;
    }

    private int getDesirability() {
        return desirability;
    }

    private String[] getDates() {
        return dates;
    }

    private int[] getTimes() {
        return times;
    }

    private CourseData[] getPreReqs(){return preReqs;}

    private void setDesirability(int desirability) {
        this.desirability = desirability;
    }
}