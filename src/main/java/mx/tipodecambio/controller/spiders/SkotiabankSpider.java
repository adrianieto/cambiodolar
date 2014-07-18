package mx.tipodecambio.controller.spiders;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SkotiabankSpider implements SpiderJson {
	
	String url = "https://finanzasenlinea.infosel.com/inverlat/dolarinterbancario.ashx";
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

		JsonNode detalleNode = rootNode.path("Detalle");
		String compra = detalleNode.path("Compra24Hr").toString();
		String venta = detalleNode.path("Venta24Hr").toString();
		
		tcambio = new HashMap<String, String>();
		tcambio.put("compra", cleanValue(compra));
		tcambio.put("venta", cleanValue(venta));
		return tcambio;
	}

	public String cleanValue(String data) {
		String cleanedValue = data.replace("\"", "");
		return cleanedValue.substring(0, 5);
	}
	
	public String formatJson(String json){
		String jsonr = json.replace("ObtenDatos({ \"DolarInterbancario\": ", "");
		jsonr = jsonr.replace("[","");
		jsonr = jsonr.replace("]","");
		jsonr = jsonr.replace(");","");
		return jsonr;
	}

}
