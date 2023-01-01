package plus.irbis.news.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plus.irbis.news.dto.TopicDto;
import plus.irbis.news.entity.Topic;

@Mapper
public interface TopicMapper {

    TopicMapper MAPPER = Mappers.getMapper(TopicMapper.class);

    Topic convert(TopicDto topicDto);

    TopicDto convert(Topic topic);
}
