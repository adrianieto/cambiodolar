package mx.tipodecambio.controller.spiders;

import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ConnectSpider {
	
	public static Document connectToServer(String url) {
		Document doc = null;;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla").get();
		} catch (HttpStatusException e) {
			try{
				doc = Jsoup.connect(url).get();
			}catch (IOException ex){
				ex.printStackTrace();
			}
			
		} catch (IOException e){
			e.printStackTrace();
		}
		return doc;
	}
	
	public static String connectToServerJson(String url){
		String doc = null;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	

}
