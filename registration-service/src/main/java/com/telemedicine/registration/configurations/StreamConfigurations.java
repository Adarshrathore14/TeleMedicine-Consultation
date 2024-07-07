package com.telemedicine.registration.configurations;
import com.telemedicine.registration.customserde.AuthRequestSerde;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@AllArgsConstructor
public class StreamConfigurations {
    private StreamConfigurationProperties properties;
    @Bean
    public StreamsConfig streamsConfig(KafkaProperties properties){
        return new StreamsConfig(properties.buildStreamsProperties(null));
    }

    @Bean
    public StreamsBuilder streamsBuilder(){
        return new StreamsBuilder();
    }
    @Bean
    public KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder){
        return new KafkaStreams(streamsBuilder.build(),kafkaStreamsProperties());
    }
    @Bean
    public ApplicationRunner kafkaStreamsRunner(KafkaStreams kafkaStreams) {
        return args -> kafkaStreams.start();
    }
    private Properties kafkaStreamsProperties(){
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,properties.getBootstrapServers());
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG,properties.getApplicationId());
        streamProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AuthRequestSerde.class);
        return streamProperties;
    }
}
