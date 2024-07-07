package com.telemedicine.registration.serviceimplementation;
import com.telemedicine.registration.configurations.PatientConfiguration;
import com.telemedicine.registration.customserde.AuthRequestSerde;
import com.telemedicine.registration.dto.AuthRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;
@Component
@AllArgsConstructor
public class RecordProcessingService {
    private final StreamsBuilder streamsBuilder;
    private final AuthRequestSerde authRequestSerde;
    private final PatientConfiguration patientConfiguration;
    @PostConstruct
    private void buildTopology(){
        KStream<String, AuthRequestDto> inputTopicRecord = streamsBuilder.stream("registration-topic",
                Consumed.with(Serdes.String(),authRequestSerde));
        inputTopicRecord.filter(((key,details)-> details!=null &&details.getRole().equals(patientConfiguration.getRole()))).
                to("patient-topic", Produced.with(Serdes.String(),authRequestSerde));
    }
}