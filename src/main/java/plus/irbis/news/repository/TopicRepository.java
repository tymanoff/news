package plus.irbis.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plus.irbis.news.entity.Topic;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findTopicByTitle(String title);

    List<Topic> findAllTopicBySourceId(Long sourceId);
}
