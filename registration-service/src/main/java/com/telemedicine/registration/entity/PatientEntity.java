package com.telemedicine.registration.entity;
import com.telemedicine.registration.customannotations.AgeValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "patientId")
    @GenericGenerator(name="patientId",type = PatientIdGenerator.class)
    private String patientId;
    @NotBlank(message = "patient name is required")
    private String patientName;
    @NotBlank(message = "userName is required")
    @Column(unique = true)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5}$",message = "enter a valid user name")
    private String userName;
    @Email(regexp = "[a-z0-9_.e+%#]+@[a-z]+.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE,message = "enter a valid emailId")
    @NotBlank(message = "email is required")
    private String email;
    @AgeValidator(message = "Your age must be atLeast 18 years")
    private LocalDate dateOfBirth;
    @NotBlank(message = "mobile number is required")
    @Pattern(regexp = "[0-9]{10}",message = "mobileNumber is required")
    private String mobileNumber;
    @NotBlank(message = "password is required")
    private String password;
    private String role;
    private String accountStatus;
    @Transient
    @NotBlank(message = "activation code required")
    private String activationCode;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
