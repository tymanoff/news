package plus.irbis.news.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsRequest {
    private String source;
    private String topic;
    private String news;
}
