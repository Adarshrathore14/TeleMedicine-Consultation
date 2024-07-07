package com.telemedicine.registration.entity;
import com.telemedicine.registration.configurations.PatientConfiguration;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class PatientIdGenerator implements IdentifierGenerator {
    @Autowired
    private PatientConfiguration patientConfiguration;
    @Autowired
    private Random random;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        StringBuilder productId = new StringBuilder(patientConfiguration.getIdLength());
        List<Integer> randomNumbers = new ArrayList<>();
        for(int randomNumber =0; randomNumber<=4;randomNumber++){
            randomNumbers.add(randomNumber);
        }
        randomNumbers.subList(0, randomNumbers.size()).forEach(randomNumber->
                productId.append(random.nextInt(10)));
        return patientConfiguration.getCode()+productId.toString();
    }
}
