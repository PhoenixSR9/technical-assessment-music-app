package com.example.musicdemo.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import com.example.musicdemo.model.request.UpdateSongRequestModel;

public class UpdateSongRequestModelTest
{
private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	void shouldValidateNullValues()
	{
		Set<ConstraintViolation<UpdateSongRequestModel>> violations = validator.validate(new UpdateSongRequestModel());
		
		assertAll(
				() -> assertEquals(3, violations.size()),
				() -> assertEquals(true, validateField(violations, "title")),
				() -> assertEquals(true, validateField(violations, "artist")),	
				() -> assertEquals(true, validateField(violations, "musicianModels")),	
				() -> assertEquals(true, violations.stream().map(ConstraintViolation::getMessage).allMatch("must not be empty"::equals)));
		
	}
	
	private boolean validateField(Set<ConstraintViolation<UpdateSongRequestModel>> violations, String fieldName)
	{
		return violations.stream()
				.map(ConstraintViolation::getPropertyPath)
				.map(Path::toString)
				.anyMatch(fieldName::equals);
	}
}
