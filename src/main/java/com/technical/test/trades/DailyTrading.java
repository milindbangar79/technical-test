package com.technical.test.trades;

import java.io.File;

import com.technical.test.exception.DailyTradingCustomException;


/**
 * @author milindbangar
 * @version 1.0
 * @since 03/03/2019
 *
 */
public interface DailyTrading {
	
	/**
	 * Generic method to get the file to be passed as parameter with Trade Data
	 * @param fileName
	 * @return file object
	 * @throws DailyTradingCustomException
	 */
	default File getFile(String fileName) throws DailyTradingCustomException{
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			return new File(classLoader.getResource(fileName).getFile());
		} catch (Exception e) {
			throw new DailyTradingCustomException("Got exception {} while loading the file",
					e.getCause(),false,false);
		}
	}
	
	public void createDailyTrading(File dataFile) throws DailyTradingCustomException;
	
}
