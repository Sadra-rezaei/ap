package ap.projects.finalproject.ui;

/**
 * Renders the console menus of the Library System.
 * Each method prints exactly one menu; the caller is responsible for
 * reading and dispatching the chosen option.
 */
public class Menu {

    /** Main menu shown at startup: choose the role to continue as. */
    public void showLibraryMenu(){
        System.out.println("\n--- Library System ---");
        System.out.println("1) Guest");
        System.out.println("2) Student register/login");
        System.out.println("3) Employee login");
        System.out.println("4) Admin login");
        System.out.println("5) Exit");
        System.out.print("> ");
    }

    /** Sub-menu before the student session: register a new account or log in. */
    public void showStudentLoginMenu(){
        System.out.print("""
    1-register\s
    2-login:\s
    99- ===Back===
    >
                        """);
    }

    /** Sub-menu for a logged-in student: browse, search/request loans, return. */
    public void showStudentMenu(){
        System.out.print("""
    1-show books\s
    2-search book\s
    3-return book\s
    99- ===Back===
    >
        """);
    }

    /** Sub-menu for a guest: browse and search only (no loans). */
    public void showGuestMenu(){
        System.out.print("""
    1-show books\s
    2-search book\s
    99- ===Back===
    >
        """);
    }

    /** Sub-menu for the admin: manage employees and view statistics. */
    public void showAdminMenu(){
        System.out.println("""
    1.add Employee
    2.report Employee
    3.loan Stats
    4.10 student with most delays Stats
    5. === BACK ===
    >
                                """);
    }

    /** Sub-menu for an employee: manage books and process loans. */
    public void showEmployeeMenu(){
        System.out.println("""
    1-add book
    2-approve loan\s
    3-receive\s
    4-student report
    5- === BACK ===
                               \s""");
    }
}
