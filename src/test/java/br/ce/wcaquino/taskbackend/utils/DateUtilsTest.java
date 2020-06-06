package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {

	LocalDate date = LocalDate.now();
	
	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		assertTrue("Data: " + date.plusDays(1).toString() + " inválida!", DateUtils.isEqualOrFutureDate(date.plusDays(1)));
	}

	@Test
	public void deveRetornarFalseParaDatasPassadas() {
		assertFalse("Data: " + date.minusDays(1).toString() + " inválida!", DateUtils.isEqualOrFutureDate(date.minusDays(1)));
	}

	@Test
	public void deveRetornarTrueParaDataAtual() {
		assertTrue("Data: " + date.toString() + " inválida!", DateUtils.isEqualOrFutureDate(date));
	}
}
