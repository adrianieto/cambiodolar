package mx.tipodecambio.controller.spiders;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SantanderSpider implements SpiderJson {
	
	String url = "http://www.mam.com.mx/indicadores/getjson.aspx?jsoncallback=?";
	String json = null;
	HashMap<String, String> tcambio = null;

	public String connectToServer() {
		json = ConnectSpider.connectToServerJson(url);
		return json;
	}

	public HashMap<String, String> getData(String json) {
		
		ObjectMapper m = new ObjectMapper();
		JsonNode rootNode=null;;
		try {
			rootNode = m.readTree(formatJson(json));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonNode dollarNode = rootNode.path("dollar");
		String compra = dollarNode.path("buy").toString();
		String venta = dollarNode.path("sell").toString();
		
		tcambio = new HashMap<String, String>();
		tcambio.put("compra", cleanValue(compra));
		tcambio.put("venta", cleanValue(venta));
		return tcambio;
	}
	
	public String cleanValue(String data) {
		String cleanedValue = data.replace("\"", "");
		return cleanedValue;
	}
	
	public String formatJson(String json){
		String jsonr = json.replace("?", "");
		jsonr = jsonr.replace("(","");
		jsonr = jsonr.replace(")","");
		return jsonr;
	}

	
}
