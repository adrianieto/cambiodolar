package mx.tipodecambio.controller.spiders;

import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BanorteSpider implements Spider {

	String url = "http://www.banorte.com/portal/personas/home.web";
	Document doc = null;
        StringBuilder KS=new StringBuilder();
	Elements elements = null;
	HashMap<String, String> tcambio = null;


	public Document connectToServer() {
		doc = ConnectSpider.connectToServer(url);
		return doc;
	}


	public HashMap<String, String> getData(Document doc) {
		Elements els = doc.select("tr > td");
		tcambio = new HashMap<String, String>();
		tcambio.put("compra", cleanValue(els.get(5).text()));
		tcambio.put("venta", cleanValue(els.get(6).text()));
		return tcambio;
	}
	

	public String cleanValue(String data) {
		String cleanedValue = data.replace("$", "");
                cleanedValue = cleanedValue.replace(",", ".");
		return cleanedValue;
	}


}
