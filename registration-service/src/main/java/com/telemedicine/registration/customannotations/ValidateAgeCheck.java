package com.telemedicine.registration.customannotations;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class ValidateAgeCheck implements ConstraintValidator<AgeValidator, LocalDate> {
    @Override
    public void initialize(AgeValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(value!=null){
            return Period.between(value,LocalDate.now()).getYears()>=18;
        }
        return false;
    }

}
