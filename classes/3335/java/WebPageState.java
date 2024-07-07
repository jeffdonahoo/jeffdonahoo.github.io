import java.util.Scanner;

/*
 * State Diagram
 * 
 * State        Transition     Next
 * MAIN         login          LOGIN
 * MAIN         register       REGISTER
 * LOGIN        bad            LOGIN
 * LOGIN        success        APP
 * REGISTER     exists         REGISTER
 * REGISTER     new            LOGIN
 * APP          logout         MAIN
 */
public enum WebPageState {
    MAIN("main.html") {
        public WebPageState next(String in) {
            return switch (in) {
            case "login" -> LOGIN;
            case "register" -> REGISTER;
            default -> MAIN;
            };
        }
    },
    LOGIN("login.html") {
        public WebPageState next(String in) {
            return switch (in) {
            case "success" -> APP;
            default -> LOGIN;
            };
        }
    },
    REGISTER("register.html") {
        public WebPageState next(String in) {
            return switch (in) {
            case "new" -> LOGIN;
            default -> MAIN;
            };
        }
    },
    APP("app.html") {
        public WebPageState next(String in) {
            return switch (in) {
            case "logout" -> MAIN;
            default -> APP;
            };
        }
    };

    private String html;

    WebPageState(String html) {
        this.html = html;
    }

    public String getHTML() {
        return html;
    }

    public abstract WebPageState next(String in);

    public static void main(String[] args) {
        WebPageState page = MAIN;
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.println(page.getHTML());
                System.out.print("next> ");
                page = page.next(in.next());
            }
        }
    }
}
