package ap.projects.Library.util;

public class Menu {


    public void showUserSelection() {


        System.out.println(
                """
                        1. Student
                        
                        2. Operator
                        
                        3. Admin
                        
                        99. Exit
                        """
        );
    }

    public void showLoginPage() {

        System.out.println(
                """
                        1. Sign Up\
                        
                        2. Sign In\
                        
                        99. Exit"""
        );
    }

    public void showStudentMenu() {
        System.out.println(
                """
                        1. Show Books\
                        
                        2. Search Book\
                        
                        3. Borrow Book\

                        4. Return Book\
                        
                        5. Show borrowed books
                       
                        99. Logout"""
        );
    }

    public void showAdminMenu() {
        System.out.println(

                """
                        1. Add a new operator\
                        
                        2. List of borrowed Books\

                        3. List of late returned Books\

                        4. Taken Books from operators\

                        5. Most borrowed Book in this year\
                        
                        99. Logout"""
        );
    }

    public void showOperatorMenu() {
        System.out.println(
                """
                        1. Show Books\
                        
                        2. Edit information\
                        
                        3. Add a new Book\
                        
                        4. Approve borrow requests\
                        
                        5. Approve return requests\
                        
                        99. Logout
                        """
        );
    }
}
