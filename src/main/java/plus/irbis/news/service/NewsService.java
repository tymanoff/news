package plus.irbis.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import plus.irbis.news.dto.NewsDto;
import plus.irbis.news.entity.News;
import plus.irbis.news.entity.Source;
import plus.irbis.news.entity.Topic;
import plus.irbis.news.mapper.NewsMapper;
import plus.irbis.news.repository.NewsRepository;
import plus.irbis.news.web.response.NewsResponse;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final TopicService topicService;
    private final SourceService sourceService;

    public NewsDto createNews(String title, Long sourceId, Long topicId) {
        NewsDto newsDto = new NewsDto();
        newsDto.setTitle(title);

        Source source = sourceService.getSource(sourceId);
        Topic topic = topicService.getTopic(topicId);

        News news = NewsMapper.MAPPER.convert(newsDto);
        Optional<News> newsFromDB = newsRepository.findNewsByTitle(news.getTitle());
        if (newsFromDB.isEmpty()) {
            news.setSource(source);
            news.setTopic(topic);
            News saveNews = newsRepository.save(news);
            return NewsMapper.MAPPER.convert(saveNews);
        }
        return NewsMapper.MAPPER.convert(newsFromDB.get());
    }

    public NewsResponse getNews(Long sourceId, Long topicId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<News> pageTuts;
        if (sourceId == null && topicId == null) {
            pageTuts = newsRepository.findAll(paging);
        } else if (sourceId != null && topicId == null) {
            Source source = sourceService.getSource(sourceId);
            pageTuts = newsRepository.findAllBySource(source, paging);
        } else if (sourceId == null && topicId != null) {
            Topic topic = topicService.getTopic(topicId);
            pageTuts = newsRepository.findAllNewsByTopic(topic, paging);
        } else {
            Source source = sourceService.getSource(sourceId);
            Topic topic = topicService.getTopic(topicId);
            pageTuts = newsRepository.findALLBySourceAndTopic(source, topic, paging);
        }
        List<NewsDto> newsList = pageTuts.getContent().stream()
                .map(NewsMapper.MAPPER::convert)
                .toList();

        NewsResponse response = new NewsResponse();
        response.setNews(newsList);
        response.setCurrentPage(pageTuts.getNumber());
        response.setTotalItems(pageTuts.getTotalElements());
        response.setTotalPages(pageTuts.getTotalPages());
        return response;
    }

    public List<NewsDto> getNews(Long topicId) {
        List<News> news = newsRepository.findAllNewsByTopicId(topicId);
        return news
                .stream()
                .map(NewsMapper.MAPPER::convert)
                .toList();
    }
}
