package com.xm.crypto.recommendation.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xm.crypto.recommendation.processor.CryptoFilesProcessor;
import com.xm.crypto.recommendation.StaticVariables;
import com.xm.crypto.recommendation.processor.CryptoCalculator;

import org.apache.commons.lang3.StringUtils;

import com.xm.crypto.recommendation.model.CryptoModel;
import java.lang.StringBuffer;
import java.math.BigDecimal;

public class CryptoSortedServlet extends HttpServlet {

	private static final long serialVersionUID = 1234567L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		List<String> allCryptos = Arrays.asList(StaticVariables.ALL_CRYPTOS);

		CryptoCalculator calc = new CryptoCalculator();

		StringBuffer json = new StringBuffer("");
		for (String crypto : allCryptos) {
			List<CryptoModel> sortedModels = calc.cryptoModelsSorted(crypto, StaticVariables.COMPARE_PRICES, null);

			if (sortedModels != null && !sortedModels.isEmpty() && sortedModels.size() >= 0) {
				// calculate oldest / newest
				BigDecimal minPrice = sortedModels.get(0).getPrice();
				BigDecimal maxPrice = sortedModels.get(sortedModels.size() - 1).getPrice();

				json.append("==============================================\n");
				json.append("Output for crypto : " + crypto + "\n");
				json.append("Normalized Range : " + calc.calculateNormalPrice(minPrice, maxPrice)+ "\n");
				

				for (CryptoModel model : sortedModels) {
					json.append(model.toString());
					json.append(",");
				}
				json.append("==============================================\n");
			}

		}

		response.getOutputStream().println(json.toString());

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		this.doGet(request, response);
		
	}
}