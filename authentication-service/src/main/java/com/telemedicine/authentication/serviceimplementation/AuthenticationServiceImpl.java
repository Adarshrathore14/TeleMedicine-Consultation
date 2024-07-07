package com.telemedicine.authentication.serviceimplementation;
import com.telemedicine.authentication.dto.LoginDetails;
import com.telemedicine.authentication.entity.AuthenticationEntity;
import com.telemedicine.authentication.repository.AuthenticationRepository;
import com.telemedicine.authentication.service.AuthenticationService;
import com.telemedicine.authentication.util.GenerateJwtToken;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.security.auth.login.AccountNotFoundException;
@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final GenerateJwtToken generateJwtToken;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String authenticate(LoginDetails loginDetails) throws AccountNotFoundException {
        String userName = loginDetails.getEmail()!=null? loginDetails.getEmail() : loginDetails.getUserName();
        String password = loginDetails.getPassword();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName,password));
        if(!authentication.isAuthenticated()) {
            throw new AccountNotFoundException("Invalid Credentials");
        }
        return generateJwtToken.generateToken(userName);
    }
    @PostConstruct
    private void initialize(){
        AuthenticationEntity adminEntity = new AuthenticationEntity();
        adminEntity.setUserId("admin");
        adminEntity.setEmail("admin@gmail.com");
        adminEntity.setUserName("admin");
        adminEntity.setRole("Admin");
        adminEntity.setPassword(passwordEncoder.encode("Admin"));
        if(!authenticationRepository.existsByUserId(adminEntity.getUserId())){
            authenticationRepository.save(adminEntity);
        }
        AuthenticationEntity doctorEntity = new AuthenticationEntity();
        doctorEntity.setUserId("doctor");
        doctorEntity.setEmail("doctor@gmail.com");
        doctorEntity.setUserName("doctor");
        doctorEntity.setRole("Doctor");
        doctorEntity.setPassword(passwordEncoder.encode("doctor"));
        if(!authenticationRepository.existsByUserId(doctorEntity.getUserId())){
            authenticationRepository.save(doctorEntity);
        }
        AuthenticationEntity iTTeamEntity = new AuthenticationEntity();
        iTTeamEntity.setUserId("IT");
        iTTeamEntity.setEmail("ITTeam@gmail.com");
        iTTeamEntity.setUserName("IT");
        iTTeamEntity.setRole("IT");
        iTTeamEntity.setPassword(passwordEncoder.encode("ITTeam"));
        if(!authenticationRepository.existsByUserId(iTTeamEntity.getUserId())){
            authenticationRepository.save(iTTeamEntity);
        }
    }

    @KafkaListener(topics = "patient-topic",groupId = "authentication")
    private void consume(AuthenticationEntity authenticationEntity) {
        if (authenticationEntity != null) {
            if (!authenticationRepository.existsByUserId(authenticationEntity.getUserId())) {
                authenticationRepository.save(authenticationEntity);
            }
            AuthenticationEntity oldDetails = authenticationRepository.findByUserId(authenticationEntity.getUserId())
                    .orElse(new AuthenticationEntity());
            oldDetails.setEmail(authenticationEntity.getEmail());
            oldDetails.setPassword(authenticationEntity.getPassword());
            oldDetails.setUserName(authenticationEntity.getUserName());
            authenticationRepository.save(oldDetails);
        }
    }
}