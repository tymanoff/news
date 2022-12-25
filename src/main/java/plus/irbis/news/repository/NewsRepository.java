package plus.irbis.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plus.irbis.news.entity.News;
import plus.irbis.news.entity.Source;
import plus.irbis.news.entity.Topic;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findNewsByTitle(String title);

    Page<News> findAllBySource(Source source, Pageable paging);

    Page<News> findAllNewsByTopic(Topic topic, Pageable paging);

    Page<News> findALLBySourceAndTopic(Source source, Topic topic, Pageable paging);

    List<News> findAllNewsByTopicId(Long topicId);
}
