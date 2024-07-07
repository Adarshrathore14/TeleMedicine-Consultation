package com.telemedicine.portal.entity;
import com.telemedicine.portal.configurations.TicketNumberConfiguration;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class TicketNumberGenerator implements IdentifierGenerator {
    @Autowired
    private TicketNumberConfiguration ticketNumberConfiguration;
    @Autowired
    private Random random;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        StringBuilder productId = new StringBuilder(ticketNumberConfiguration.getLength());
        List<Integer> randomNumbers = new ArrayList<>();
        for(int randomNumber =0; randomNumber<=4;randomNumber++){
            randomNumbers.add(randomNumber);
        }
        randomNumbers.subList(0, randomNumbers.size()).forEach(randomNumber->
                productId.append(random.nextInt(10)));
        return ticketNumberConfiguration.getCode()+productId.toString();
    }
}
