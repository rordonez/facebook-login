package rafael.ordonez.facebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by rafa on 17/8/15.
 */
@SpringBootApplication
public class FacebookLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacebookLoginApplication.class, args);
    }


    @Configuration
    public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {
        /**
         *
         * Configuration for Content Negotiation based on suffix in URI. Json is the default representation for resources.
         *
         * @param configurer
         */
        @Override
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.favorPathExtension(true).
                    ignoreAcceptHeader(false).
                    useJaf(false).
                    defaultContentType(MediaType.APPLICATION_JSON).
                    mediaType("xml", MediaType.APPLICATION_XML).
                    mediaType("html", MediaType.TEXT_HTML).
                    mediaType("json", MediaType.APPLICATION_JSON);
        }

        /**
         *
         * Configure the MessageConverters to use Jackson2ObjectMapperBuilder. This class also provides an API to
         * configure Jackson maintaining the default Spring MVC configuration. This also creates instances of an
         * ObjectMapper and a XMLMapper in the same instance.
         *
         * @param converters
         */
        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                    .indentOutput(true)
                    .failOnEmptyBeans(false);
            converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
            converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
        }

    }
}
