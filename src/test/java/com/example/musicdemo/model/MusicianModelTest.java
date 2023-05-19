package com.example.musicdemo.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

public class MusicianModelTest
{
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	void shouldValidateNullValues()
	{
		Set<ConstraintViolation<MusicianModel>> violations = validator.validate(new MusicianModel());
		
		assertAll(
				() -> assertEquals(2, violations.size()),
				() -> assertEquals(true, validateField(violations, "name")),
				() -> assertEquals(true, validateField(violations, "contribution")),	
				() -> assertEquals(true, violations.stream().map(ConstraintViolation::getMessage).allMatch("must not be empty"::equals)));
		
	}
	
	private boolean validateField(Set<ConstraintViolation<MusicianModel>> violations, String fieldName)
	{
		return violations.stream()
				.map(ConstraintViolation::getPropertyPath)
				.map(Path::toString)
				.anyMatch(fieldName::equals);
	}
}
