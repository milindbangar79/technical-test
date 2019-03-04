/**
 * 
 */
package com.technical.test.trades;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author milindbangar
 * Static helper class to print the results of Trades for entities foo and bar and rank the trade in descending order for Buy and Sell 
 * transactions
 */
public class TradingReport {
	
	private static final Logger log = LogManager.getLogger();
	
	private static final String AMOUNT_STATEMENT = " and amount is $";
	
	private static DecimalFormat dformat = new DecimalFormat("####0.00");
	
	private TradingReport(){}
	
	/*
	 * Method to print the different transactions based on trade data received
	 */
	public static void outputBuyAndSellTransactions(){
		
		log.info("### Outgoing USD Settlements ###");

		for (Entry<String, Double> entry : DailyTradingBuilder.USDSettledOutgoing.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();

			log.info("Settlement Date is " + date + AMOUNT_STATEMENT + dformat.format(value));
		}

		log.info("### End of Outgoing USD Settlements ###");
		
		log.info("---------------------------------");

		log.info("### Incoming USD Settlements ###");

		for (Entry<String, Double> entry : DailyTradingBuilder.USDSettledIncoming.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			log.info("Settlement Date is " + date + AMOUNT_STATEMENT + dformat.format(value));
		}

		log.info("End of Incoming USD Settlements");
	}
	
	/**
	 * 
	 * @param Settlements.USDSettledRanking
	 * To print the Settlements for Buy and Sell across dates and rank them in descending order
	 */
	public static void outputRankByTrades(Map<String, Double> rankMap) {

		log.debug("Create the output for ranking the transactions");

		Set<Entry<String, Double>> mapEntries = rankMap.entrySet();

		// used linked list to sort, as insertion of elements in linked
		// list is faster than an array list.
		List<Entry<String, Double>> rankingList = new LinkedList<Entry<String, Double>>(mapEntries);

		// sorting the List
		Collections.sort(rankingList, new Comparator<Entry<String, Double>>() {

			public int compare(Entry<String, Double> element1, Entry<String, Double> element2) {
				return element2.getValue().compareTo(element1.getValue());
			}
		});

		// Store the list into Linked HashMap to preserve the order of
		// insertion.
		Map<String, Double> rankingMap = new LinkedHashMap<>();
		for (Entry<String, Double> entry : rankingList) {
			rankingMap.put(entry.getKey(), entry.getValue());
		}

		// Print values after sorting of map
		log.info("###### Ranking of USD Settlements by descending order #####            ");

		for (Entry<String, Double> entry : rankingMap.entrySet()) {
			log.info("TransactionType ::: " + entry.getKey() + AMOUNT_STATEMENT + dformat.format(entry.getValue()));
		}
		
		log.info("##### End of Ranking of USD Settlements #####            ");
		
	}

}
