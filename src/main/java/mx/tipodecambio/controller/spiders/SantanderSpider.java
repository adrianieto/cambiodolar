package mx.tipodecambio.controller.spiders;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import mx.tipodecambio.model.Santander;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SantanderSpider implements SpiderJson, Runnable {
	
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

	public void run() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cambiodolar");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		HashMap<String, String> tipo_de_cambio_santander = new HashMap<String, String>();
		
		Santander santanderBean = new Santander();
		SpiderJson santander = new SantanderSpider();
		tipo_de_cambio_santander = santander.getData(santander
				.connectToServer());
		santanderBean.setCompra(Float.parseFloat(tipo_de_cambio_santander
				.get("compra")));
		santanderBean.setVenta(Float.parseFloat(tipo_de_cambio_santander
				.get("venta")));
		santanderBean.setFecha(new Date());
		
		tx.begin();
		em.persist(santanderBean);
		tx.commit();
		
	}

	
}
