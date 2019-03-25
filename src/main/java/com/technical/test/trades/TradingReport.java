/**
 * 
 */
package com.technical.test.trades;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.Map.Entry;


/**
 * @author milindbangar Static helper class to print the results of Trades for
 *         entities foo and bar and rank the trade in descending order for Buy
 *         and Sell transactions
 */
public class TradingReport {
	
	private static final Logger logger = Logger.getLogger(TradingReport.class.getName());

	private static final String AMOUNT_STATEMENT = " Amount in $";

	private static DecimalFormat dformat = new DecimalFormat("####0.00");

	private TradingReport() {
	}
	
	
	/*
	 * Method to print the different transactions based on trade data received
	 */
	public static void outputBuyAndSellTransactions() {

		for (Entry<String, Double> entry : DailyTradingBuilder.USDSettledOutgoing.entrySet()) {
			String date = entry.getKey();
			String value = dformat.format(entry.getValue());
			
			writedataToConsole(date,value,"B");
		}

		for (Entry<String, Double> entry : DailyTradingBuilder.USDSettledIncoming.entrySet()) {
			String date = entry.getKey();
			String value = dformat.format(entry.getValue());
			
			writedataToConsole(date,value,"S");	
		}

	}


	/**
	 * 
	 * @param Settlements.USDSettledRanking
	 *            To print the Settlements for Buy and Sell across dates and
	 *            rank them in descending order
	 */
	public static void outputRankByTrades(Map<String, Double> rankMap) {
		
		logger.finer("Create the output for ranking the transactions");

		Set<Entry<String, Double>> mapEntries = rankMap.entrySet();

		// used linked list to sort, as insertion of elements in linked
		// list is faster than an array list.
		List<Entry<String, Double>> rankingList = new LinkedList<Entry<String, Double>>(mapEntries);

		//Use Lambda to compare the List . Possible as Comparator has one abstract method to be qualified as functional interface
		Collections.sort(rankingList,(element1,element2) -> element2.getValue().compareTo(element1.getValue()));
		
		for (Entry<String, Double> entry : rankingList) {
			String type = entry.getKey();
			String value = dformat.format(entry.getValue());
			// Print values after sorting of map
			writeTradeRankingDataToConsole(type,value);
		}

	}
	
	private static void writeTradeRankingDataToConsole(String type, String tradeValue) {
		
		PrintWriter consoleRankOutput = new PrintWriter(System.out,true);
		
		String format = "Transaction Type ::: %s, %s, %s\n" ;
		
		consoleRankOutput.printf(format, type,AMOUNT_STATEMENT,tradeValue);
		
	}

	private static void writedataToConsole(String date, String value,String transactionType){
		
		PrintWriter consoleOutput = new PrintWriter(System.out,true);
		
		String format = transactionType.equals("B")  ? "Buy Settlement Date ::: %s, %s, %s\n" : "Sell Settlement Date ::: %s, %s, %s\n";
		
		consoleOutput.printf(format, date,AMOUNT_STATEMENT,value);
		
	}

}
