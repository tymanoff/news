package plus.irbis.news.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plus.irbis.news.dto.NewsDto;
import plus.irbis.news.dto.SourceDto;
import plus.irbis.news.dto.TopicDto;
import plus.irbis.news.exceptions.CustomException;
import plus.irbis.news.service.NewsService;
import plus.irbis.news.service.SourceService;
import plus.irbis.news.service.TopicService;
import plus.irbis.news.web.constant.WebConstant;
import plus.irbis.news.web.request.NewsRequestList;
import plus.irbis.news.web.response.NewsResponse;

import java.util.List;

@RestController
@RequestMapping(value = WebConstant.VERSION_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WebController {

    private final NewsService newsService;
    private final SourceService sourceService;
    private final TopicService topicService;

    @GetMapping("/news/filter")
    public ResponseEntity<NewsResponse> getAllNewsFilter(
            @RequestParam(required = false) Long sourceId,
            @RequestParam(required = false) Long topicId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        try {
            NewsResponse response = newsService.getNews(sourceId, topicId, page, size);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/source")
    public ResponseEntity<List<SourceDto>> getSource() {
        List<SourceDto> sourceList = sourceService.getSource();
        return new ResponseEntity<>(sourceList, HttpStatus.OK);
    }

    @GetMapping("/topic")
    public ResponseEntity<List<TopicDto>> getTopic() {
        List<TopicDto> topicList = topicService.getTopic();
        return new ResponseEntity<>(topicList, HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<NewsResponse> loadNews(@RequestBody NewsRequestList request) {
        request.getRequest().forEach(element -> {
            SourceDto source = sourceService.createSource(element.getSource());
            TopicDto topic = topicService.createTopic(element.getTopic(), source.getId());
            NewsDto news = newsService.createNews(element.getNews(), source.getId(), topic.getId());
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
