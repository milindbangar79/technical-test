package com.technical.test.trades;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.BeforeClass;
import org.junit.Test;


import com.technical.test.exception.DailyTradingCustomException;
import com.technical.test.model.DailyTradingModel;
import com.technical.test.trades.DailyTradingBuilder;

public class DailyTradingBuilderTest {
	
	private static DailyTradingBuilder mockDailyTradingBuilder;
	private static DailyTradingModel model1;
	private static DailyTradingModel model2;
	private static DailyTradingModel model3;
	private static DailyTradingModel model4 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		//Double pricePerUnit, Double units, Double agreedFX, LocalDate originalSettlementDate,LocalDate instructionDate,
		//String entityName, String transactionType, String currencyType
		
		mockDailyTradingBuilder = mock(DailyTradingBuilder.class);
		model1 = new DailyTradingModel(Double.valueOf(150.25), Double.valueOf(300), Double.valueOf(0.50), LocalDate.parse("2019-02-01"), LocalDate.parse("2019-02-02"), "FOO", "BUY", "SGP");
		model2 = new DailyTradingModel(Double.valueOf(130.25), Double.valueOf(400), Double.valueOf(0.22), LocalDate.parse("2019-02-01"), LocalDate.parse("2019-02-02"), "BAR", "SELL", "AED");
		model3 = new DailyTradingModel(Double.valueOf(130.25), Double.valueOf(550), Double.valueOf(0.78), LocalDate.parse("2019-02-01"), LocalDate.parse("2019-02-02"), "FOO", "BUY", "GBP");
		

	}

	@Test
	public void testDailyTradeForModel1()   {
		
		try {
			doAnswer((i) -> {
				assertTrue(model1.equals(i.getArgument(0)));
				return null;
			}).when(mockDailyTradingBuilder).buildTradeDetails(model1);
		} catch (DailyTradingCustomException e) {
			e.getCause();
		}
		
	}
	
	@Test
	public void testDailyTradeWhenNoDataProvided() throws DailyTradingCustomException{
		
		doThrow(DailyTradingCustomException.class).when(mockDailyTradingBuilder).buildTradeDetails(null);
		
		assertThrows(DailyTradingCustomException.class, () -> mockDailyTradingBuilder.buildTradeDetails(null));
		
	}
	
	@Test
	public void testDailyTradeWithCurrencyAed(){
		
		try {
			doAnswer((i) -> {
				assertTrue(model2.equals(i.getArgument(0)));
				 new DailyTradingBuilder().buildTradeDetails(model2);
				return null;
			}).when(mockDailyTradingBuilder).buildTradeDetails(model2);
		} catch (DailyTradingCustomException e) {
			e.getCause();
		}
		
		 assertNotNull(DailyTradingBuilder.USDSettledIncoming);
		 assertNotNull(DailyTradingBuilder.USDSettledOutgoing);
		 assertNotNull(DailyTradingBuilder.USDSettledRanking);
		
	}
	
	
	@Test
	public void testDailyTradeWithCurrencyOtherThanAedAndSGP(){
		
		try {
			doAnswer((i) -> {
				assertTrue(model3.equals(i.getArgument(0)));
				 new DailyTradingBuilder().buildTradeDetails(model2);
				return null;
			}).when(mockDailyTradingBuilder).buildTradeDetails(model2);
		} catch (DailyTradingCustomException e) {
			e.getCause();
		}
		
		 assertNotNull(DailyTradingBuilder.USDSettledIncoming);
		 assertNotNull(DailyTradingBuilder.USDSettledOutgoing);
		 assertNotNull(DailyTradingBuilder.USDSettledRanking);
		
	}
	
	@Test(expected=DailyTradingCustomException.class)
	public void testForNullTradeData() throws DailyTradingCustomException{
		new DailyTradingBuilder().buildTradeDetails(model4);
	}
	

}
