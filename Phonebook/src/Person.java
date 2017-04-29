/**
 * A class to represent a person
 *
 * @author Cormac Dacker
 * @since 1/31/2017
 */
public class Person {

    private int phoneNumber;
    private String name;
    private int ssn;
    private int hasName = 0;

    /**
     * Creates a person ... that's right it gives birth to a human being !!!! code is weird *shrugs*
     *
     * @param name the name of the person
     * @param phoneNum the phone number of the person
     */
    public Person(String name, int phoneNum) {
        this.hasName = 1;
        this.name = name;
        this.phoneNumber = phoneNum;
    }

    /**
     * See above
     *
     * @param ssn         the social security number
     * @param phoneNumber the phone number of the person
     */
    public Person(int ssn, int phoneNumber) {
        this.ssn = ssn;
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getSsn() {
        return ssn;
    }

    public int getHasName() {
        return hasName;
    }
}
