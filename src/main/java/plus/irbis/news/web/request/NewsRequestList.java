package plus.irbis.news.web.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsRequestList {
    private List<NewsRequest> request;
}
