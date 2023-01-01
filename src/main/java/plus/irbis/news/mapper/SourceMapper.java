package plus.irbis.news.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plus.irbis.news.dto.SourceDto;
import plus.irbis.news.entity.Source;

@Mapper
public interface SourceMapper {

    SourceMapper MAPPER = Mappers.getMapper(SourceMapper.class);

    Source convert(SourceDto sourceDto);

    SourceDto convert(Source source);
}
