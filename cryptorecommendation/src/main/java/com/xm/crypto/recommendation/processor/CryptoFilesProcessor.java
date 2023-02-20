package com.xm.crypto.recommendation.processor;

import com.xm.crypto.recommendation.model.CryptoModel;
import com.xm.crypto.recommendation.CryptoUtils;
import com.xm.crypto.recommendation.StaticVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.text.SimpleDateFormat;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CryptoFilesProcessor {

	

	public List<CryptoModel> getAllCryptos(String type) {


		List<CryptoModel> models;
		if(StringUtils.isEmpty(type) || StaticVariables.ALL_COINS.equals(type)){
			System.out.println(StaticVariables.ALL_COINS);
			List<String> allCryptos = Arrays.asList(StaticVariables.ALL_CRYPTOS);
			models = new ArrayList<CryptoModel>();
			
			for (String crypto: allCryptos) {
				System.out.println(crypto);
				String path = StaticVariables.CRYPTO_FILE_PATH + crypto + StaticVariables.FILE_SUFFIX;
				System.out.println(path);
				models.addAll(readCryptoFile(path));
			}
		}else {
			String path = StaticVariables.CRYPTO_FILE_PATH + type + StaticVariables.FILE_SUFFIX;
			models = readCryptoFile(path);
		}
		
		

		return models;
	}

	private List<CryptoModel> readCryptoFile(String path) {
		List<CryptoModel> models = new ArrayList();
		String line = "";
		
		
		try {

			BufferedReader br = new BufferedReader(new FileReader(path));
			CryptoModel model;
			
			//skip the head line
			br.readLine();
			
			while ((line = br.readLine()) != null)
			{
				
				String[] modelLine = line.split(StaticVariables.SPLIT_LINE_BY);
				
				if (modelLine.length==3) {
					model = new CryptoModel();
					model.setTimestamp(CryptoUtils.transformDate(modelLine[0], StaticVariables.DATE_FORMAT));
					model.setSymbol(modelLine[1]);
					model.setPrice(new BigDecimal(modelLine[2]));
					
					models.add(model);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return models;
	}
	
	public static void main(String args[]) {
		
		CryptoFilesProcessor proc = new CryptoFilesProcessor();
		
		List<CryptoModel> ms = proc.getAllCryptos(null);
		for (CryptoModel m : ms) {
			System.out.println(m.toString());
		}
		
		String ts = StaticVariables.DEFAULT_DATE;
		
		Timestamp time = new Timestamp(Long.parseLong(ts));
		
		Date date = new Date(time.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.S");
		
		System.out.println("" + time + " date: " + sdf.format(date));
	}

}
