package org.opt4j.core.config.annotations;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.config.annotations.Citation.PublicationMonth;

public class CitationTest {
	@Test
	public void toMonth() {
		Assert.assertEquals(Month.JANUARY, PublicationMonth.JANUARY.toMonth());
		Assert.assertEquals(Month.FEBRUARY, PublicationMonth.FEBRUARY.toMonth());
		Assert.assertEquals(Month.MARCH, PublicationMonth.MARCH.toMonth());
		Assert.assertEquals(Month.APRIL, PublicationMonth.APRIL.toMonth());
		Assert.assertEquals(Month.MAY, PublicationMonth.MAY.toMonth());
		Assert.assertEquals(Month.JUNE, PublicationMonth.JUNE.toMonth());
		Assert.assertEquals(Month.JULY, PublicationMonth.JULY.toMonth());
		Assert.assertEquals(Month.AUGUST, PublicationMonth.AUGUST.toMonth());
		Assert.assertEquals(Month.SEPTEMBER, PublicationMonth.SEPTEMBER.toMonth());
		Assert.assertEquals(Month.OCTOBER, PublicationMonth.OCTOBER.toMonth());
		Assert.assertEquals(Month.NOVEMBER, PublicationMonth.NOVEMBER.toMonth());
		Assert.assertEquals(Month.DECEMBER, PublicationMonth.DECEMBER.toMonth());
	}

	@Test(expected = DateTimeException.class)
	public void toMonthUnkown() {
		PublicationMonth.UNKNOWN.toMonth();
	}

	@Test
	public void valueOf() {
		Assert.assertEquals(PublicationMonth.JANUARY, PublicationMonth.valueOf("JANUARY"));
	}
}
