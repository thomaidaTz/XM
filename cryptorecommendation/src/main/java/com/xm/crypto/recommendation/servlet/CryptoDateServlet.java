package com.xm.crypto.recommendation.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xm.crypto.recommendation.processor.CryptoCalculator;
import com.xm.crypto.recommendation.StaticVariables;


import com.xm.crypto.recommendation.model.CryptoModel;
import java.lang.StringBuffer;
import java.text.SimpleDateFormat;

public class CryptoDateServlet extends HttpServlet {

	private static final long serialVersionUID = 1234567L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String date = request.getParameter("date");

		CryptoCalculator cal = new CryptoCalculator();
		
		List<CryptoModel> modelsByDate;
		StringBuffer json = new StringBuffer();
		
		try {
			Date date1 = new SimpleDateFormat(StaticVariables.DATE_FORMAT_FROM_INPUT).parse(date);
			modelsByDate = cal.getCryptoModelsByDate(date1);
		} catch (Exception e) {
			response.getOutputStream().println("UNPARSABLE DATE");
			return;
		}

		CryptoCalculator calc = new CryptoCalculator();

		

		List<CryptoModel> sortedModelsByDate = calc.cryptoModelsSorted(null, StaticVariables.COMPARE_PRICES, modelsByDate);
		json.append("==============================================");
		json.append("For Date: " + date);
		
		if (sortedModelsByDate!=null && !sortedModelsByDate.isEmpty() && sortedModelsByDate.size()>=0) {
			// calculate oldest / newest 

			json.append("MIN ENTRY IS :" + sortedModelsByDate.get(0).toString()+"\n");
			json.append("MAX ENTRY IS :" + sortedModelsByDate.get(sortedModelsByDate.size()-1).toString()+"\n");
			json.append("NORMAL PRICE IS :"+calc.calculateNormalPrice(sortedModelsByDate.get(0).getPrice(), 
					sortedModelsByDate.get(sortedModelsByDate.size()-1).getPrice()));
		}

		response.getOutputStream().println(json.toString());

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		this.doGet(request, response);
	}
}