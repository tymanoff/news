package plus.irbis.news.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "news-service")
public class NewsServiceProperties {
    private String productToken;
}
