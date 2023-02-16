package moonrise.pjt1.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.redis")  // 설정 값을 불러올 때 prefix 값을 지정할 수 있다.
@Configuration
public class RedisInfo {
    private String host;
    private int port;
}