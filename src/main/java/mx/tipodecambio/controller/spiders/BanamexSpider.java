package mx.tipodecambio.controller.spiders;

import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BanamexSpider implements Spider {

	String url = "http://portal.banamex.com.mx/c719_004/economiaFinanzas/es/home?xhost=http://www.banamex.com/";
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
		tcambio.put("compra", cleanValue(els.get(0).text()));
		tcambio.put("venta", cleanValue(els.get(4).text()));
		return tcambio;
	}
        
	public String cleanValue(String data) {
		return data.substring(0,5);
	}

}
