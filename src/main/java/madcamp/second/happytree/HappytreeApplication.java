package madcamp.second.happytree;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "madcamp.second.controller", "madcamp.second.mapper", "madcamp.second.model",
"madcamp.second.security", "madcamp.second.service", "madcamp.second.serviceImp"})
@MapperScan(basePackages = { "madcamp.second.mapper"})
public class HappytreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappytreeApplication.class, args);
    }

}
