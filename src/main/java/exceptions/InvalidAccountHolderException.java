package exceptions;

public class InvalidAccountHolderException extends Exception{
    public InvalidAccountHolderException(String message) {
        super(message);
    }
}
