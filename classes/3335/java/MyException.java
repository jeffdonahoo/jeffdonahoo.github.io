public class MyException extends Exception {
    String message;
    String extra;
    Throwable cause;
    
    MyException(String message, String extra) {
        if (extra == null) {
            throw new NullPointerException();
        }
        this.message = message;
        this.extra = extra;
    }
   
    MyException(String message, String extra, Throwable cause) {
        if (extra == null) {
            throw new NullPointerException();
        }
        this.message = message;
        this.extra = extra;
    }
    
    public String getMessage() {
        return message;
    }
    
    String getExtra() {
        return extra;
    }
    
    public Throwable getCause() {
        return cause;
    }
}
