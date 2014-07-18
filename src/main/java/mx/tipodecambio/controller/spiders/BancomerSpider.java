package mx.tipodecambio.controller.spiders;

import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BancomerSpider implements Spider {

	String url = "https://bbv.infosel.com/bancomerindicators/mainboard5V13.html";
	Document doc = null;
	Elements elements = null;
	HashMap<String, String> tcambio = null;
	

        public Document connectToServer() {
		doc = ConnectSpider.connectToServer(url);
		return doc;
	}


        public HashMap<String, String> getData(Document doc) {
		tcambio = new HashMap<String, String>();
		Elements els = doc.select("tr > td");
		tcambio.put("compra", els.get(10).text());
		tcambio.put("venta", els.get(13).text());
		return tcambio;
	}
	
	public String cleanValue(String data) {
		//String data = data.replace(arg0, arg1);
		return data;
	}

}
