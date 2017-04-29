/**
 * A class to represent a PhoneBook
 *
 * @author Cormac Dacker
 * @since 1/31/2017
 */
public class Phonebook<T> {

    private Person[] phoneBook;

    public Phonebook(Person[] book) {
        this.phoneBook = bubbleSoart(book);
    }

    public int findPerson(String name) {
        for (int index = 0; index < getPhoneBook().length - 1; index++) {
            if (name.equals(getPhoneBook()[index].getName())) {
                return getPhoneBook()[index].getPhoneNumber();
            }
        }
        return -1;
    }

    public int findPerson(int ssn) {
        int x = 0;
        int y = getPhoneBook().length - 1;
        while (x <= y) {
            int mid = (int) ((x + y) / 2);
            if (ssn == getPhoneBook()[mid].getSsn()) {
                return getPhoneBook()[mid].getPhoneNumber();
            } else if (ssn > getPhoneBook()[mid].getSsn()) {
                x = mid + 1;
            } else {
                y = mid - 1;
            }
        }
        return -1;
    }

    public void addPerson(Person tom) {
        if (getPhoneBook()[0].getHasName() == tom.getHasName()) {
            Person[] newBook = new Person[getPhoneBook().length];
            for (int index = 0; index < newBook.length - 1; index++) {
                newBook[index] = getPhoneBook()[index];
            }
            newBook[newBook.length - 1] = tom;
            setPhoneBook(newBook);
            bubbleSoart(getPhoneBook());
        } else {
            System.out.println("Incomparable person type");
        }
    }

    public void deletePerson(Person tom) {
        for (int index = 0; index < getPhoneBook().length - 1; index++) {
            if (tom.getSsn() != -1) {
                if (getPhoneBook()[index].getSsn() == tom.getSsn()) {
                    getPhoneBook()[index] = null;
                }
            } else if (tom.getName() != null) {
                if (getPhoneBook()[index].getName() == tom.getName()) {
                    getPhoneBook()[index] = null;
                }
            }
        }
    }

    public Person[] bubbleSoart(Person[] book) {
        for (int index = 0; index < book.length - 1; index++) {
            for (int xedni = index + 1; xedni < book.length; xedni++) {
                if (book[index].getPhoneNumber() > book[xedni].getPhoneNumber()) {
                    int temp = book[xedni].getPhoneNumber();
                    book[xedni].setPhoneNumber(book[index].getPhoneNumber());
                    book[index].setPhoneNumber(temp);
                }
            }
        }
        return book;
    }

    public Person[] getPhoneBook() {
        return phoneBook;
    }

    public void setPhoneBook(Person[] phoneBook) {
        this.phoneBook = phoneBook;
    }


    public static void main(String[] args) {/* the first main method for this assignment */
        Person hawking= new Person(123456789, 987654321);
        Person curie = new Person("Marie C.", 123123123);
        Person kurzweil = new Person(654654654, 456456456);
        Person tesla = new Person("Nikola T.", 789789789);
        Person[] people = {hawking};
        Person[] people2 = {curie};
        Phonebook bookOne = new Phonebook(people);
        Phonebook bookTwo = new Phonebook(people2);
        bookOne.addPerson(kurzweil);
        bookTwo.addPerson(tesla);
        System.out.print(bookOne.findPerson(123456789));
        System.out.print(bookTwo.findPerson("Marie C."));
        for (int index = 0; index < bookOne.getPhoneBook().length; index++){
            System.out.print(bookOne.getPhoneBook()[index].getName());
        }

/* the second main method for this assignment */
//        Person p1 = new Person (123456789, "3828975463");
//        Person p2 = new Person ("Frederick Crawford", "6583527553");
//        Person[] people = {p1};
//        Phonebook bookOne = new Phonebook (people);
//        bookOne.addPerson(p2);

    }
}
