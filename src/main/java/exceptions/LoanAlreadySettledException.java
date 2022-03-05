package exceptions;

public class LoanAlreadySettledException extends Exception{
    public LoanAlreadySettledException(String message) {
        super(message);
    }
}
