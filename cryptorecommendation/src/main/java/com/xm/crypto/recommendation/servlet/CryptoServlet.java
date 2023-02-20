package com.xm.crypto.recommendation.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xm.crypto.recommendation.processor.CryptoCalculator;
import com.xm.crypto.recommendation.StaticVariables;

import org.apache.commons.lang3.StringUtils;

import com.xm.crypto.recommendation.model.CryptoModel;
import java.lang.StringBuffer;

public class CryptoServlet extends HttpServlet {

	private static final long serialVersionUID = 1234567L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String coin = request.getParameter("cryptoCoin");

		CryptoCalculator calc = new CryptoCalculator();

		if (StringUtils.isEmpty(coin) || StringUtils.equals(coin, StaticVariables.ALL_COINS)) {
			coin = StaticVariables.ALL_COINS;
		}

		HashMap<String, CryptoModel> map = calc.minMaxCalculations(coin);

		StringBuffer json = new StringBuffer("");

		json.append("==============================================\n");
		json.append("Starting calculations for  : " + coin + "\n");
		
		if(map!=null && !map.isEmpty()) {
			if (map.containsKey(StaticVariables.MIN_PRICE)) {
				json.append("MIN PRICE: ");
				json.append(map.get(StaticVariables.MIN_PRICE));
				json.append("\n");
			}
			if (map.containsKey(StaticVariables.MAX_PRICE)) {
				json.append("MAX PRICE: ");
				json.append(map.get(StaticVariables.MAX_PRICE));
				json.append("\n");
			}
			if (map.containsKey(StaticVariables.OLDEST)) {
				json.append("OLDEST VALUE: ");
				json.append(map.get(StaticVariables.OLDEST));
				json.append("\n");
			}
			if (map.containsKey(StaticVariables.NEWEST)) {
				json.append("NEWEST VALUE: ");
				json.append(map.get(StaticVariables.NEWEST));
				json.append("\n");
			}
		}
		
		json.append("==============================================\n");
		

		response.getOutputStream().println(json.toString());

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		this.doGet(request, response);
	}
}