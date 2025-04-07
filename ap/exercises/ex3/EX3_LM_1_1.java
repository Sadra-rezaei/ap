package ap.exercises.ex3;


    class Book{
        private String name;
        private String author;
        private int pages;
        private int year;

        public Book(String name, String author, int pages, int year) {
            this.name = name;
            this.author = author;
            this.pages = pages;
            this.year = year;
        }
    }

    class Student {
        private String firstName;
        private String lastName;
        private int studentNumber;
        private String course;

        public Student(String firstName, String lastName, int studentNumber, String course) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.studentNumber = studentNumber;
            this.course = course;
            System.out.println(firstName + " " + lastName + " added!");
        }
    }

public class EX3_LM_1_1 {
    public static void main(String[] args) {
        Student s1 = new Student("Ali", "Mahdavinia", 403463149, "Computer Engineering");
        Student s2 = new Student("Sadra" , "Mir Mohammad Rezaei" , 403463152, "Computer Engineering");
        Book b1 = new Book("Neruda", "Ali Mahdavinia", 3000, 1390);
        Book b2 = new Book("Clean Code", "Robert C. Martin", 1 ,16);
    }
}


