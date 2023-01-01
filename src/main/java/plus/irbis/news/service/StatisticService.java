package plus.irbis.news.service;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import plus.irbis.news.dto.NewsDto;
import plus.irbis.news.dto.SourceDto;
import plus.irbis.news.dto.TopicDto;
import plus.irbis.news.exceptions.CustomException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final SourceService sourceService;
    private final TopicService topicService;
    private final NewsService newsService;

    @Scheduled(cron = "@hourly")
    @Async
    public void writeAll() {
        List<SourceDto> allSource = sourceService.getSource();
        if (allSource.isEmpty()) {
            return;
        }
        allSource.parallelStream().forEach(element -> {
            try {
                getTopicList(element);
            } catch (Exception e) {
                throw new CustomException(e);
            }
        });
    }

    private void getTopicList(SourceDto source) throws IOException {
        String fileName = "csv/" + source.getTitle() + ".csv";
        List<TopicDto> allTopic = topicService.getAllTopic(source.getId());
        if (allTopic.isEmpty()) {
            return;
        }

        Map<String, Integer> topicAmountNews = new HashMap<>();
        allTopic.parallelStream().forEach(element -> {
            List<NewsDto> news = newsService.getNews(element.getId());
            topicAmountNews.put(element.getTitle(), news.size() + 1);
        });

        List<String[]> lines = topicAmountNews.entrySet()
                .stream()
                .map(e -> new String[]{e.getKey(), e.getValue().toString()})
                .toList();

        writeToCsv(lines, fileName);
    }

    private void writeToCsv(List<String[]> lines, String fileName) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeAll(lines);
        }
    }
}
