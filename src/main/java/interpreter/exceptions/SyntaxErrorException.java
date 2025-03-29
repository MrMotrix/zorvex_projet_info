package interpreter.exceptions;

public class SyntaxErrorException extends Exception {
    protected int lineNumber;

  public SyntaxErrorException(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public String toString() {
    return "Syntax error on line " + lineNumber;
  }
}
