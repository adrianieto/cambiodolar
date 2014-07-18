package mx.tipodecambio.controller.spiders;

import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HsbcSpider implements Spider {
	
	String url = "https://www.hsbc.com.mx/1/2/es/personas/divisas";
	Document doc = null;
	Elements elements = null;
	HashMap<String, String> tcambio = null;


	public Document connectToServer() {
		doc = ConnectSpider.connectToServer(url);
		return doc;
	}

	public HashMap<String, String> getData(Document doc) {
		Elements els = doc.select("tr > td");
		tcambio = new HashMap<String, String>();
		tcambio.put("compra", cleanValue(els.get(15).text()));
		tcambio.put("venta", cleanValue(els.get(16).text()));
		return tcambio;
	}
	
	public String cleanValue(String data) {
		String cleanString = data;
		cleanString = cleanString.replace("$", "");
		cleanString = cleanString.replace(" ", "");
		return cleanString.substring(0,5);
	}

}
