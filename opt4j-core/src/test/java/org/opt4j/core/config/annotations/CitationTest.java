package org.opt4j.core.config.annotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.config.annotations.Citation.PublicationMonth;

public class CitationTest {
	@Test
	public void toMonth() {
		Assertions.assertEquals(Month.JANUARY, PublicationMonth.JANUARY.toMonth());
		Assertions.assertEquals(Month.FEBRUARY, PublicationMonth.FEBRUARY.toMonth());
		Assertions.assertEquals(Month.MARCH, PublicationMonth.MARCH.toMonth());
		Assertions.assertEquals(Month.APRIL, PublicationMonth.APRIL.toMonth());
		Assertions.assertEquals(Month.MAY, PublicationMonth.MAY.toMonth());
		Assertions.assertEquals(Month.JUNE, PublicationMonth.JUNE.toMonth());
		Assertions.assertEquals(Month.JULY, PublicationMonth.JULY.toMonth());
		Assertions.assertEquals(Month.AUGUST, PublicationMonth.AUGUST.toMonth());
		Assertions.assertEquals(Month.SEPTEMBER, PublicationMonth.SEPTEMBER.toMonth());
		Assertions.assertEquals(Month.OCTOBER, PublicationMonth.OCTOBER.toMonth());
		Assertions.assertEquals(Month.NOVEMBER, PublicationMonth.NOVEMBER.toMonth());
		Assertions.assertEquals(Month.DECEMBER, PublicationMonth.DECEMBER.toMonth());
	}

	@Test
	public void toMonthUnkown() {
		assertThrows(DateTimeException.class, () -> {
			PublicationMonth.UNKNOWN.toMonth();
		});
	}

	@Test
	public void valueOf() {
		Assertions.assertEquals(PublicationMonth.JANUARY, PublicationMonth.valueOf("JANUARY"));
	}
}
