package interpreter.exceptions;

public class RuntimeError extends Exception {
    private String message;

    public RuntimeError(String message) {
        this.message = message;
    }
  
    public String toString() {
      return message;
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
