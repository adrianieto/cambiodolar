package mx.tipodecambio.controller.spiders;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import mx.tipodecambio.model.Banamex;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BanamexSpider implements Spider, Runnable {

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

	public void run() {
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cambiodolar");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		HashMap<String, String> tipo_de_cambio_banamex = new HashMap<String, String>();

		Banamex banamexBean = new Banamex();
		Spider banamex = new BanamexSpider();
		tipo_de_cambio_banamex = new HashMap<String, String>();
		try {
			tipo_de_cambio_banamex = banamex.getData(banamex.connectToServer());
		} catch (IndexOutOfBoundsException e) {
			try {
				tipo_de_cambio_banamex = banamex.getData(banamex
						.connectToServer());
			} catch (IndexOutOfBoundsException e1) {
				tipo_de_cambio_banamex = banamex.getData(banamex
						.connectToServer());
			}
		} 

		banamexBean.setCompra(Float.parseFloat(tipo_de_cambio_banamex
				.get("compra")));
		banamexBean.setVenta(Float.parseFloat(tipo_de_cambio_banamex
				.get("venta")));
		banamexBean.setFecha(new Date());
		
		tx.begin();
		em.persist(banamexBean);
		tx.commit();

	}

}
