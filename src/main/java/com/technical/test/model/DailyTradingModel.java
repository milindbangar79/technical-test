package com.technical.test.model;

import java.time.LocalDate;


/**
 * 
 * @author milindbangar
 * @version 1.0
 * 
 *          Data Model for Settlements
 * 
 *          Would have used Lombok library , which could have helped in avoiding
 *          to use the boiler plate code, due to issue with latest release of
 *          the library
 *          
 *          Only Getters are generated as per the requirement
 */

public class DailyTradingModel {

	private Double pricePerUnit;
	private Double units;
	private Double agreedFX;
	private LocalDate originalSettlementDate;
	private LocalDate instructionDate;
	private String entityName;
	private String transactionType;
	// Currency library provided by Java complying to ISO 4217 can also be used for getting the currency definition of passed code
	// Can be used to validate the decimal fraction allowed for different currencies e.g. AED is 2
	private String currencyType; 

	public DailyTradingModel(Double pricePerUnit, Double units, Double agreedFX, LocalDate originalSettlementDate,LocalDate instructionDate,
			String entityName, String transactionType, String currencyType) {
		super();
		this.pricePerUnit = pricePerUnit;
		this.units = units;
		this.agreedFX = agreedFX;
		this.originalSettlementDate = originalSettlementDate;
		this.instructionDate = instructionDate;
		this.entityName = entityName;
		this.transactionType = transactionType;
		this.currencyType = currencyType;
	}

	/**
	 * @return the pricePerUnit
	 */
	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	/**
	 * @return the units
	 */
	public Double getUnits() {
		return units;
	}

	/**
	 * @return the agreedFX
	 */
	public Double getAgreedFX() {
		return agreedFX;
	}


	/**
	 * @return the originalSettlementDate
	 */
	public LocalDate getOriginalSettlementDate() {
		return originalSettlementDate;
	}

	/**
	 * @return the instructionDate
	 */
	public LocalDate getInstructionDate() {
		return instructionDate;
	}


	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @return the currencyType
	 */
	public String getCurrencyType() {
		return currencyType;
	}
	

}
