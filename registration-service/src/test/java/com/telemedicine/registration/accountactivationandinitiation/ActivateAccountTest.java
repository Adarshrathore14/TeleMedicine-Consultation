package com.telemedicine.registration.accountactivationandinitiation;
import com.telemedicine.registration.configurations.AccountActivationConfiguration;
import com.telemedicine.registration.configurations.HospitalMessageConfiguration;
import com.telemedicine.registration.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
class ActivateAccountTest {
    @Mock
    private HospitalMessageConfiguration hospitalMessageConfiguration;
    @Mock
    private AccountActivationConfiguration accountActivationConfiguration;
    @InjectMocks
    private ActivateAccount activateAccount;
    @Test
    void generateAccountActivationToken() {
        PatientEntity patient=new PatientEntity("123","userName1",null,null,null,
                null,null,null,null,null,null,null);
        String jwtSecret="secretKeyForTestingPurpose123456789013I+/+/+jvcnvcchbcvb+/+p1234";
        Mockito.when(accountActivationConfiguration.getSecretKey()).thenReturn(jwtSecret);
        assertInstanceOf(String.class,activateAccount.generateAccountActivationToken("patient",patient));
        assertNotNull(activateAccount.generateAccountActivationToken("patient",patient));
    }
}