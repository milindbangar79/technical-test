package com.technical.test.trades;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;

import com.technical.test.exception.DailyTradingCustomException;
import com.technical.test.trades.DailyTradingController;

public class DailyTradingControllerTest extends DailyTradingController {

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testMainClass() throws DailyTradingCustomException {
		instance = new InnerDailyTradingClass();
		String[] args = { "tradedata.txt", "tradedataanother.txt" };
		exit.expectSystemExitWithStatus(0);
		main(args);
	}

	@Test
	public void testThrowsExceptionInMainClassWithWrongFileName() throws DailyTradingCustomException {
		instance = new InnerDailyTradingClass();
		String[] args = { "tradedataanother.txt", "tradedata.txt" };
		thrown.expect(DailyTradingCustomException.class);
		thrown.expectMessage(startsWith("Got exception"));
		exit.expectSystemExitWithStatus(0);
		main(args);
	}
	
	@Test
	public void testThrowsExceptionInMainClassWithNoArgument() throws DailyTradingCustomException {
		instance = new InnerDailyTradingClass();
		String[] args = {};
		thrown.expect(DailyTradingCustomException.class);
		thrown.expectMessage(startsWith("No argument(s) provided"));
		exit.expectSystemExitWithStatus(0);
		main(args);
	}
	
	@Test
	public void testThrowsExceptionIfDataFileArgumentisNull() throws DailyTradingCustomException{
		
		DailyTradingController mockDailyTradingController;
		
		mockDailyTradingController = mock(DailyTradingController.class);
		
		doThrow(DailyTradingCustomException.class).when(mockDailyTradingController).createDailyTrading(null);
		
		assertThrows(DailyTradingCustomException.class, () -> mockDailyTradingController.createDailyTrading(null));
	}

	private static class InnerDailyTradingClass extends DailyTradingController {
		@Override
		public File getFile(String string) throws DailyTradingCustomException {
			throw new DailyTradingCustomException();
		}
	}
}
