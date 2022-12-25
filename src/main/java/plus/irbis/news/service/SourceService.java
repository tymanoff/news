package plus.irbis.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.irbis.news.dto.SourceDto;
import plus.irbis.news.entity.Source;
import plus.irbis.news.exceptions.SourceNotFoundException;
import plus.irbis.news.mapper.SourceMapper;
import plus.irbis.news.repository.SourceRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SourceService {

    private final SourceRepository sourceRepository;

    public SourceDto createSource(String title) {
        SourceDto sourceDto = new SourceDto();
        sourceDto.setTitle(title);

        Source source = SourceMapper.MAPPER.convert(sourceDto);
        Optional<Source> sourceFromDB = sourceRepository.findSourceByTitle(source.getTitle());
        if (sourceFromDB.isEmpty()) {
            Source saveSource = sourceRepository.save(source);
            return SourceMapper.MAPPER.convert(saveSource);
        }
        return SourceMapper.MAPPER.convert(sourceFromDB.get());
    }

    public List<SourceDto> getSource() {
        List<Source> sourceList = sourceRepository.findAll();
        return sourceList
                .stream()
                .map(SourceMapper.MAPPER::convert)
                .toList();
    }

    public Source getSource(Long sourceId) {
        return sourceRepository.findById(sourceId)
                .orElseThrow(() -> new SourceNotFoundException("Source not found id: " + sourceId));
    }
}
