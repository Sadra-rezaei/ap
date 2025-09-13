package ap.projects.finalproject.UI;

public class Menu {

    public void showLibraryMenu(){
        System.out.println("\n--- Library System ---");
        System.out.println("1) Guest");
        System.out.println("2) Student register/login");
        System.out.println("3) Employee login");
        System.out.println("4) Admin login");
        System.out.println("5) Exit");
        System.out.print("> ");
    }

    public void showStudentLoginMenu(){
        System.out.print("""
                            1-register\s
                            2-login:\s
                            99- ===Back===
                            >""");
    }

    public void showStudentMenu(){
        System.out.print("""
                            1-search book\s
                            2-show books\s
                            99- ===Back===
                            >
        """);
    }

    public void showAdminMenu(){
        System.out.println("""
                                1.add Employee
                                2.report Employee
                                3.loan Stats
                                4.10 student with most delays Stats
                                5. === BACK ===
                                """);
    }

    public void showEmployeeMenu(){
        System.out.println("""
                                1-add book
                                2-approve loan\s
                                3-receive\s
                                4-return\s
                                5-student report
                                6- === BACK ===
                               \s""");
    }




}
