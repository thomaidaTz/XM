package com.xm.crypto.recommendation.processor;

import java.util.List;

import com.xm.crypto.recommendation.CryptoUtils;
import com.xm.crypto.recommendation.StaticVariables;
import com.xm.crypto.recommendation.model.CryptoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class CryptoCalculator {
	
	
	public List<CryptoModel> cryptoModelsSorted(String crypto, final int compareBy, List<CryptoModel> models){
		CryptoFilesProcessor processor = new CryptoFilesProcessor();
		if (models==null || models.isEmpty()) {
			models = processor.getAllCryptos(crypto);
		}
		Collections.sort(models, new Comparator<CryptoModel>() {

	        public int compare(CryptoModel m1, CryptoModel m2) {
	        	
	            return compareBy(m1, m2, compareBy);
	        }
	    });
		
		return models;
		
	}
	
	private static int compareBy(CryptoModel m1, CryptoModel m2, int compareBy) {
		if (compareBy == StaticVariables.COMPARE_PRICES) {
			return m1.getPrice().compareTo(m2.getPrice());
		}else if(compareBy == StaticVariables.COMPARE_DATES) {
			return m1.getTimestamp().compareTo(m2.getTimestamp());
		}else {
			return -1;
		}
	}
	
	public void minMaxCalculations() {
		
		List<String> allCryptos = Arrays.asList(StaticVariables.ALL_CRYPTOS);
		
		for (String crypto : allCryptos) {
			minMaxCalculations(crypto);
		}
		
	}
	
	public HashMap<String, CryptoModel> minMaxCalculations(String crypto) {
		
		HashMap<String, CryptoModel> map = new HashMap<String, CryptoModel>();
		
		System.out.println("==============================================");
		System.out.println("Starting calculations for crypto : " + crypto);
		
		// calculate oldest / newest 
		List<CryptoModel> models =  this.cryptoModelsSorted(crypto, StaticVariables.COMPARE_DATES, null);
		
		if (models!=null && !models.isEmpty() && models.size()>=0) {
			map.put(StaticVariables.NEWEST, models.get(0));
			map.put(StaticVariables.OLDEST, models.get(models.size()-1));
			System.out.println("NEWEST ENTRY IS :" + models.get(0).toString());
			System.out.println("OLDEST ENTRY IS :" + models.get(models.size()-1).toString());
		}
		
		
		models =  this.cryptoModelsSorted(crypto, StaticVariables.COMPARE_PRICES, null);
		
		if (models!=null && !models.isEmpty() && models.size()>=0) {
			// calculate oldest / newest 
			map.put(StaticVariables.MIN_PRICE, models.get(0));
			map.put(StaticVariables.MAX_PRICE, models.get(models.size()-1));
			System.out.println("MIN ENTRY IS :" + models.get(0).toString());
			System.out.println("MAX ENTRY IS :" + models.get(models.size()-1).toString());
		}
		
		System.out.println("==============================================");
		System.out.println("");
		
		return map;
		
	}
	
	public BigDecimal calculateNormalPrice(BigDecimal minPrice, BigDecimal maxPrice) {
		return maxPrice.subtract(minPrice).divide(maxPrice, RoundingMode.HALF_UP);
	}
	
	public List<CryptoModel> getCryptoModelsByDate(Date date){
		CryptoFilesProcessor fileProc = new CryptoFilesProcessor();
		List<CryptoModel> models = fileProc.getAllCryptos(StaticVariables.ALL_COINS);
		List<CryptoModel> modelsByDate = new ArrayList<CryptoModel>();
		for (CryptoModel model: models) {
			if (CryptoUtils.removeTime(model.getTimestamp()).compareTo(CryptoUtils.removeTime(date)) == 0) {
				modelsByDate.add(model);
			}
		}
		return modelsByDate;
	}
	
	public static void main(String args[]) {
		
		
		
		
		
	}

}
