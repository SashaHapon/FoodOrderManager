package org.kitchen.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("kitchen")
public class KitchenProperties {
    private Kafka kafka = new Kafka();

    @Getter
    @Setter
    public static class Kafka {
        private Topic topic = new Topic();

        @Getter
        @Setter
        public static class Topic {
            private String in;
            private String out;
        }
    }
}
