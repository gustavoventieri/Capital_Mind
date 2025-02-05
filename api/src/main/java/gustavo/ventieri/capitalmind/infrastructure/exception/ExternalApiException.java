package gustavo.ventieri.capitalmind.infrastructure.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }
}

