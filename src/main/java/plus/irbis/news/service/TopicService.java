package plus.irbis.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.irbis.news.dto.TopicDto;
import plus.irbis.news.entity.Source;
import plus.irbis.news.entity.Topic;
import plus.irbis.news.exceptions.SourceNotFoundException;
import plus.irbis.news.exceptions.TopicNotFoundException;
import plus.irbis.news.mapper.TopicMapper;
import plus.irbis.news.repository.SourceRepository;
import plus.irbis.news.repository.TopicRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final SourceRepository sourceRepository;

    public TopicDto createTopic(String title, Long sourceId) {
        TopicDto topicDto = new TopicDto();
        topicDto.setTitle(title);
        Source source = sourceRepository.findById(sourceId)
                .orElseThrow(() -> new SourceNotFoundException("Source not found id: " + sourceId));

        Topic topic = TopicMapper.MAPPER.convert(topicDto);
        Optional<Topic> topicFromDB = topicRepository.findTopicByTitle(topic.getTitle());
        if (topicFromDB.isEmpty()) {
            topic.setSource(source);
            Topic saveTopic = topicRepository.save(topic);
            return TopicMapper.MAPPER.convert(saveTopic);
        }
        return TopicMapper.MAPPER.convert(topicFromDB.get());
    }

    public List<TopicDto> getTopic() {
        List<Topic> topicList = topicRepository.findAll();
        return topicList
                .stream()
                .map(TopicMapper.MAPPER::convert)
                .toList();
    }


    public List<TopicDto> getAllTopic(Long sourceId) {
        List<Topic> topicList = topicRepository.findAllTopicBySourceId(sourceId);
        return topicList
                .stream()
                .map(TopicMapper.MAPPER::convert)
                .toList();
    }

    public Topic getTopic(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found id: " + topicId));
    }
}
