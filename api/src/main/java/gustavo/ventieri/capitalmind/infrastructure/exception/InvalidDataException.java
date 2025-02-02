package gustavo.ventieri.capitalmind.infrastructure.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}