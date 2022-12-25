package plus.irbis.news.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plus.irbis.news.dto.NewsDto;
import plus.irbis.news.entity.News;

@Mapper
public interface NewsMapper {

    NewsMapper MAPPER = Mappers.getMapper(NewsMapper.class);

    News convert(NewsDto newsDto);

    NewsDto convert(News news);
}
