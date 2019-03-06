package com.technical.test.trades;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import com.technical.test.exception.DailyTradingCustomException;
import com.technical.test.model.DailyTradingModel;
import com.technical.test.trades.DailyTradingBuilder;

public class DailyTradingBuilderTest {

	private static DailyTradingBuilder mockDailyTradingBuilder = mock(DailyTradingBuilder.class);
	private static DailyTradingModel model1;
	private static DailyTradingModel model2;
	private static DailyTradingModel model3;
	private static DailyTradingModel model4 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// Double pricePerUnit, Double units, Double agreedFX, LocalDate
		// originalSettlementDate,LocalDate instructionDate,
		// String entityName, String transactionType, String currencyType
		
		model1 = new DailyTradingModel(Double.valueOf(150.25), Double.valueOf(300), Double.valueOf(0.50),
				LocalDate.parse("2019-02-01"), LocalDate.parse("2019-02-02"), "FOO", "B", "SAR");
		model2 = new DailyTradingModel(Double.valueOf(130.25), Double.valueOf(400), Double.valueOf(0.22),
				LocalDate.parse("2019-02-01"), LocalDate.parse("2019-02-02"), "BAR", "S", "AED");
		model3 = new DailyTradingModel(Double.valueOf(130.25), Double.valueOf(550), Double.valueOf(0.78),
				LocalDate.parse("2019-02-02"), LocalDate.parse("2019-02-03"), "FOO", "B", "GBP");

	}

	@Test
	public void testDailyTradeWhenNoDataProvided() throws DailyTradingCustomException {

		doThrow(DailyTradingCustomException.class).when(mockDailyTradingBuilder).buildTradeDetails(null);

		assertThrows(DailyTradingCustomException.class, () -> mockDailyTradingBuilder.buildTradeDetails(null));

	}

	@Test
	public void testDailyTradeSettlementdateWithCurrencyGBP() throws DailyTradingCustomException {

		DailyTradingBuilder mockBuilder = mock(DailyTradingBuilder.class);
		
		DailyTradingBuilder.USDSettledRanking.clear();

		doCallRealMethod().when(mockBuilder).buildTradeDetails(any(DailyTradingModel.class));
		mockBuilder.buildTradeDetails(model3);

		verify(mockBuilder, times(1)).buildTradeDetails(model3);

		assertNotNull(DailyTradingBuilder.USDSettledIncoming);
		assertNotNull(DailyTradingBuilder.USDSettledOutgoing);
		assertNotNull(DailyTradingBuilder.USDSettledRanking);

		String dateValue = getDateFromRankingList();
		
		assertEquals("04 Feb 2019", dateValue);

	}

	private String getDateFromRankingList() {
		
		Set<Entry<String, Double>> mapEntries = DailyTradingBuilder.USDSettledRanking.entrySet();

		List<Entry<String, Double>> outputList = new LinkedList<Entry<String, Double>>(mapEntries);

		String type = "";

		for (Entry<String, Double> entry : outputList) {
			type = entry.getKey();
		}

		String[] typeDataItems = type.split(" ");

		List<String> dataItems = IntStream.range(0, typeDataItems.length).filter(i -> i > 1 && i < 5)
				.mapToObj(i -> typeDataItems[i]).collect(Collectors.toList());
		
		return String.join(" ", dataItems);
	}

	@Test
	public void testDailyTradeWithStandarCurrency() throws DailyTradingCustomException {
		
		DailyTradingBuilder mockBuilder = mock(DailyTradingBuilder.class);

		doCallRealMethod().when(mockBuilder).buildTradeDetails(any(DailyTradingModel.class));
		mockBuilder.buildTradeDetails(model2);

		verify(mockBuilder, times(1)).buildTradeDetails(model2);
		
		doCallRealMethod().when(mockBuilder).buildTradeDetails(any(DailyTradingModel.class));
		mockBuilder.buildTradeDetails(model1);

		verify(mockBuilder, times(1)).buildTradeDetails(model1);

		
		assertNotNull(DailyTradingBuilder.USDSettledIncoming);
		assertNotNull(DailyTradingBuilder.USDSettledOutgoing);
		assertNotNull(DailyTradingBuilder.USDSettledRanking);

	}

	@Test(expected = DailyTradingCustomException.class)
	public void testForNullTradeData() throws DailyTradingCustomException {
		new DailyTradingBuilder().buildTradeDetails(model4);
	}

}
