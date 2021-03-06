package com.technical.test.trades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.technical.test.exception.DailyTradingCustomException;
import com.technical.test.model.DailyTradingModel;

/**
 * @author milindbangar
 * @version 1.0 Class sets the transaction settlements against different
 *          entities foo and bar based on the buy and sell transactions
 */
public class DailyTradingBuilder {

	private static final Logger log = Logger.getLogger(DailyTradingBuilder.class.getName());

	private static final double EXISTING_USD_AMOUNT_TRADE = 0.00;

	// HashMap to set the incoming, outgoing . Sort/Rank the settlements
	static final Map<String, Double> USDSettledIncoming = new HashMap<>();
	static final Map<String, Double> USDSettledOutgoing = new HashMap<>();
	static final Map<String, Double> USDSettledRanking = new HashMap<>();

	/**
	 * 
	 * @param settlementModel
	 * @throws DailyTradingCustomException
	 */
	public void buildTradeDetails(DailyTradingModel settlementModel) throws DailyTradingCustomException {

		try {

			// Calculate the USD Amount Trade based on data received
			// irrespective of
			// Instruction type
			double usdAmountTrade = settlementModel.getPricePerUnit() * settlementModel.getUnits()
					* settlementModel.getAgreedFX();

			// Get the settlement date based on currency type for which trade
			// needs
			// to be calculated
			LocalDate settlementDate = getSettlementDateBasedOnWorkdayOfWeek(settlementModel.getCurrencyType(),
					settlementModel.getOriginalSettlementDate());

			// Update Map objects for Incoming, Outgoing and Ranking
			setTradeAmountsBasedOnBuyOrSell(settlementDate, usdAmountTrade, settlementModel.getTransactionType(),
					settlementModel.getEntityName());

		} catch (Exception e) {
			log.fine("No Trade Data Available");
			throw new DailyTradingCustomException("No Trade Data Available", e.getCause());
		}

	}

	/**
	 * 
	 * @param currency
	 * @param settlementDate
	 * @return dateBasedonWeekDay
	 */
	private LocalDate getSettlementDateBasedOnWorkdayOfWeek(String currency, LocalDate localDate) {

		LocalDate dateBasedonWeekDay = localDate;

		log.finer("Date Passed To Check for working day and/or weekend is " + localDate);

		// Check for settlement to be done between Sunday to Thursday for
		// currency types AED and SGP

		if ("AED".equals(currency) || "SAR".equals(currency)) {

			if (localDate.getDayOfWeek().toString().equals("FRIDAY")) {

				dateBasedonWeekDay = localDate.plusDays(2);

			} else if (localDate.getDayOfWeek().toString().equals("SATURDAY")) {

				dateBasedonWeekDay = localDate.plusDays(1);
			}
		} else { // Check for settlement to be done between Monday to Friday
			if (localDate.getDayOfWeek().toString().equals("SATURDAY")) {

				dateBasedonWeekDay = localDate.plusDays(2);

			} else if (localDate.getDayOfWeek().toString().equals("SUNDAY")) {

				dateBasedonWeekDay = localDate.plusDays(1);
			}
		}

		return dateBasedonWeekDay;
	}

	/**
	 * 
	 * @param settlementDate
	 * @param usdAmountTrade
	 * @param transactionType
	 *            USDSettledRanking HashMap will be set based on different
	 *            transaction type to provide output for settled trades
	 */
	private void setTradeAmountsBasedOnBuyOrSell(LocalDate settlementDate, Double usdAmountTrade,
			String transactionType, String entityName) {

				log.log(Level.FINER,
						"Settlement for Outgoing and Incoming is set with date {0} , trade amount {1} and transaction type {2}",
						new Object[]{settlementDate, usdAmountTrade, transactionType});

		// Initialise to set the default value. Add this value to calculated
		// trade amount for existing settlement date
		double existingUsdAmountTrade = EXISTING_USD_AMOUNT_TRADE;

		String europeanDatePattern = "dd MMM yyyy";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);

		// B –> BUY –> outgoing trade
		// S –> SELL –> incoming trade
		if ("B".equals(transactionType)) {
			// add to outgoing settlement
			if (USDSettledOutgoing.containsKey(europeanDateFormatter.format(settlementDate))) {

				existingUsdAmountTrade = USDSettledOutgoing.get(europeanDateFormatter.format(settlementDate));

				USDSettledOutgoing.put(europeanDateFormatter.format(settlementDate),
						usdAmountTrade + existingUsdAmountTrade);
			} else {
				USDSettledOutgoing.put(europeanDateFormatter.format(settlementDate), usdAmountTrade);
			}
			USDSettledRanking.put(entityName + " on " + europeanDateFormatter.format(settlementDate) + " BUY ",
					usdAmountTrade);

		} else if ("S".equals(transactionType)) {

			// otherwise add to incoming settlement
			if (USDSettledIncoming.containsKey(europeanDateFormatter.format(settlementDate))) {

				existingUsdAmountTrade = USDSettledIncoming.get(europeanDateFormatter.format(settlementDate));

				USDSettledIncoming.put(europeanDateFormatter.format(settlementDate),
						usdAmountTrade + existingUsdAmountTrade);

			} else {
				USDSettledIncoming.put(europeanDateFormatter.format(settlementDate), usdAmountTrade);
			}
			USDSettledRanking.put(entityName + " on " + europeanDateFormatter.format(settlementDate) + " SELL ",
					usdAmountTrade);
		}

	}
}
