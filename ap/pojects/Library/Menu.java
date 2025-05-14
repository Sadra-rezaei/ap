package ap.pojects.Library;

public class Menu {


    public void showUserSelection() {


        System.out.println(
                """
                        1. Student
                        
                        2. Operator
                        
                        3. Admin
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
                        """ +
                        
//                        3. Borrow Book\
//
//                        4. Return Book\
                       """ 
                        \n99. Exit"""
        );
    }

    public void showAdminMenu() {
        System.out.println(

//                        1. List of borrowed Books\
//
//                        2. List of late returned Books\
//
//                        3. Taken Books from operators\
//
//                        4. Most borrowed Book\
                """
                        1. Add a new operator
                        
                        99. Exit"""
        );
    }

    public void showOperatorMenu() {
        System.out.println(
                """
                        1. Show Books\
                        
                        2. Edit information\
                        
                        3. Add Book\
                        
                        99. Exit
                        """
        );
    }
}
