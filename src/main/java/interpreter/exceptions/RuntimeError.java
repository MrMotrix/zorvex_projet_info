package interpreter.exceptions;

public class RuntimeError extends Exception {
    protected int lineNumber;
    private String message;

    public RuntimeError(int lineNumber) {
      this.lineNumber = lineNumber;
    }

    public RuntimeError(int lineNumber, String message) {
        this.lineNumber = lineNumber;
        this.message = message;
    }
  
    public void setLineNumber(int lineNumber) {
      this.lineNumber = lineNumber;
    }
  
    public int getLineNumber() {
      return lineNumber;
    }
  
    public String toString() {
      return "Runtime error on line " + lineNumber + " : " + message;
    }
}
