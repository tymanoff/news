package plus.irbis.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import plus.irbis.news.config.NewsServiceProperties;

@SpringBootApplication
@EnableConfigurationProperties(NewsServiceProperties.class)
public class NewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}

}
