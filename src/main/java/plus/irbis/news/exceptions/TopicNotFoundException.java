package plus.irbis.news.exceptions;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String msg) {
        super(msg);
    }
}
