package com.example.musicdemo.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.musicdemo.model.request.AddSongRequestModel;

public class AddSongRequestModelTest
{
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	public void shouldValidateNullValues()
	{
		Set<ConstraintViolation<AddSongRequestModel>> violations = validator.validate(new AddSongRequestModel());

		assertAll(
				() -> assertEquals(5, violations.size()),
				() -> assertEquals(true, validateField(violations, "artist")),
				() -> assertEquals(true, validateField(violations, "title")),	
				() -> assertEquals(true, validateField(violations, "musicianModels")),	
				() -> assertEquals(true, validateField(violations, "registrantCode")),
				() -> assertEquals(true, validateField(violations, "designationCode")),	
				() -> assertEquals(true, violations.stream().map(ConstraintViolation::getMessage).allMatch("must not be empty"::equals)));
	}
	
	
	private boolean validateField(Set<ConstraintViolation<AddSongRequestModel>> violations, String fieldName)
	{
		return violations.stream()
				.map(ConstraintViolation::getPropertyPath)
				.map(Path::toString)
				.anyMatch(fieldName::equals);
	}

	@ParameterizedTest
	@ValueSource(strings = {"00000", "28192", "88839", "31243", "09283"})
	public void shouldSuccessfullyValidateDesignationCodePattern(String designationCode)
	{
		AddSongRequestModel dto = new AddSongRequestModel();
		dto.setArtist("Test Artist");
		dto.setTitle("Test Title");
		dto.setRegistrantCode("AAA");
		dto.setDesignationCode(designationCode);
		
		MusicianModel musicianModel = new MusicianModel();
		musicianModel.setName("Test Name");
		musicianModel.setContribution("Test Contribution");
		dto.setMusicianModels(Collections.singletonList(musicianModel));
		
		Set<ConstraintViolation<AddSongRequestModel>> violations = validator.validate(dto);
		
		assertEquals(0, violations.size());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"5", "489932", "3ac821", "9j_7#"})
	public void shouldFailToValidateDesignationCodePattern(String designationCode)
	{
		AddSongRequestModel addSongRequestModel = new AddSongRequestModel();
		addSongRequestModel.setArtist("Test Artist");
		addSongRequestModel.setTitle("Test Title");
		addSongRequestModel.setRegistrantCode("AAA");
		addSongRequestModel.setDesignationCode(designationCode);
		
		MusicianModel musicianModel = new MusicianModel();
		musicianModel.setName("Test Name");
		musicianModel.setContribution("Test Contribution");
		addSongRequestModel.setMusicianModels(Collections.singletonList(musicianModel));
		
		Set<ConstraintViolation<AddSongRequestModel>> violations = validator.validate(addSongRequestModel);
		
		assertAll(
				() -> assertEquals(1, violations.size()),
				() -> assertEquals(true, violations.stream().map(ConstraintViolation::getInvalidValue).anyMatch(designationCode::equals)));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"RK3", "MB2", "123", "AAA", "z3r", "yU7"})
	public void shouldSuccessfullyToValidateRegistrantCodePattern(String registrantCode)
	{
		AddSongRequestModel addSongRequestModel = new AddSongRequestModel();
		addSongRequestModel.setArtist("Test Artist");
		addSongRequestModel.setTitle("Test Title");
		addSongRequestModel.setRegistrantCode(registrantCode);
		addSongRequestModel.setDesignationCode("00000");
		
		MusicianModel musicianModel = new MusicianModel();
		musicianModel.setName("Test Name");
		musicianModel.setContribution("Test Contribution");
		addSongRequestModel.setMusicianModels(Collections.singletonList(musicianModel));
		
		Set<ConstraintViolation<AddSongRequestModel>> violations = validator.validate(addSongRequestModel);
		assertEquals(0, violations.size());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"WQNF", "99", "-J.", "22/", "10~"})
	public void shouldFailToValidateRegistrantCodePattern(String registrantCode)
	{
		AddSongRequestModel addSongRequestModel = new AddSongRequestModel();
		addSongRequestModel.setArtist("Test Artist");
		addSongRequestModel.setTitle("Test Title");
		addSongRequestModel.setRegistrantCode(registrantCode);
		addSongRequestModel.setDesignationCode("00000");
		
		MusicianModel musicianModel = new MusicianModel();
		musicianModel.setName("Test Name");
		musicianModel.setContribution("Test Contribution");
		addSongRequestModel.setMusicianModels(Collections.singletonList(musicianModel));
		
		Set<ConstraintViolation<AddSongRequestModel>> violations = validator.validate(addSongRequestModel);
		
		assertAll(
				() -> assertEquals(1, violations.size()),
				() -> assertEquals(true, violations.stream().map(ConstraintViolation::getInvalidValue).anyMatch(registrantCode::equals)));
	}
}
