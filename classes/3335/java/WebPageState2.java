import java.util.Map;
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
public enum WebPageState2 {
    MAIN("main.html"),
    LOGIN("login.html"),
    REGISTER("register.html"),
    APP("app.html");

    private String html;
    private static Map<WebPageState2, Map<String, WebPageState2>> transitions;
    
    static {
        transitions = Map.of(
        MAIN, Map.of("login", LOGIN, "register", REGISTER, "", MAIN),
        LOGIN, Map.of("success", APP, "", LOGIN),
        REGISTER, Map.of("new", LOGIN, "", MAIN),
        APP, Map.of("logout", MAIN, "", APP)
        );
    }

    WebPageState2(String html) {
        this.html = html;
    }

    public String getHTML() {
        return html;
    }

    public WebPageState2 next(String in) {
        WebPageState2 t = transitions.get(this).get(in);
        return (t != null) ? t : transitions.get(this).get("");
    }

    public static void main(String[] args) {
        WebPageState2 page = MAIN;
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.println(page.getHTML());
                System.out.print("next> ");
                page = page.next(in.next());
            }
        }
    }
}
