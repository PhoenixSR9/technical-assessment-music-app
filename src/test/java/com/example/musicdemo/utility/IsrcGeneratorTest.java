package com.example.musicdemo.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IsrcGeneratorTest
{
	@Test
	void shouldGenerateCorrectUSIsrc()
	{
		String isrc = IsrcGenerator.generateIsrc("FKE", "12345", "US");
		assertEquals("QMFKE2312345", isrc);
	}
	
	@Test
	void shouldGenerateIsrcCode()
	{
		String isrc = IsrcGenerator.generateIsrc("KEA", "24421", "CA");
		assertEquals("CAKEA2324421", isrc);
	}
}
