package plus.irbis.news.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import plus.irbis.news.dto.ErrorDto;
import plus.irbis.news.dto.NewsDto;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {
    private List<NewsDto> news;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
    private ErrorDto error;
}
