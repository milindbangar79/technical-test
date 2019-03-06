package com.technical.test.trades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

import com.technical.test.exception.DailyTradingCustomException;
import com.technical.test.model.DailyTradingModel;

/**
 * @author milindbangar
 * @version 1.0 Main class to create the Settlement Object, calculate amount and
 *          sort the entries based on input provided at different days of week
 * @inheritDoc 
 * @throws DailyTradingCustomException
 */
public class DailyTradingController implements DailyTrading {
	
	private static final Logger log = Logger.getLogger(DailyTradingController.class.getName());

	private static final String DOUBLECOLON_SPLIT = "::";


	protected DailyTradingController() {
	}

	public static void main(String[] args) throws DailyTradingCustomException {
		
		log.finer("Instantiate Sample Data for Settlements Including Buy or Sell");
		
		String fileName;

		if (args.length > 0) {
			fileName = args[0]; // consolidated incoming file from entities foo
								// and bar
		} else {
			throw new DailyTradingCustomException("No argument(s) provided");
		}

		DailyTradingController dailyTradeController = new DailyTradingController();

		File file = dailyTradeController.getFile(fileName);

		dailyTradeController.createDailyTrading(file);
		
	}

	@Override
	public void createDailyTrading(File dataFile) throws DailyTradingCustomException {

		try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {

			String readTradeData;

			String[] dataItems;

			while ((readTradeData = br.readLine()) != null) {

				dataItems = readTradeData.split(DOUBLECOLON_SPLIT);
				DailyTradingBuilder tradingBuilder = new DailyTradingBuilder();

				tradingBuilder.buildTradeDetails(new DailyTradingModel(Double.valueOf(dataItems[0]),
						Double.valueOf(dataItems[1]), Double.valueOf(dataItems[2]), LocalDate.parse(dataItems[3]),
						LocalDate.parse(dataItems[4]), dataItems[5], dataItems[6], dataItems[7]));
			}
			

			// output all incoming(SELL) and outgoing(BUY) transactions
			TradingReport.outputBuyAndSellTransactions();

			// print Trade ranking for either a buy and sell
			TradingReport.outputRankByTrades(DailyTradingBuilder.USDSettledRanking);


		} catch (IOException e) {
			log.fine("Trade Data could not be processed with exception" + e.getCause());
			System.exit(1);
			throw new DailyTradingCustomException("Got Exception while reading the file", e.getCause());
		}
		System.exit(0);
	}

	// Created a private static method that creates and returns the new object
	// and
	// replaced the constructor in the main() method with a call to that new
	// method.
	// Added a protected static property that could be used to store an instance

	protected static DailyTradingController instance = null;

}
