package plus.irbis.news.exceptions;

public class SourceNotFoundException extends RuntimeException {
    public SourceNotFoundException(String msg) {
        super(msg);
    }
}
