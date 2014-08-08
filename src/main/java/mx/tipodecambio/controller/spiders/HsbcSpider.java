package mx.tipodecambio.controller.spiders;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import mx.tipodecambio.model.Hsbc;
import mx.tipodecmabio.controller.TdcBackgroundTask;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Implementacion de Spider para HSBC.
 * @version 0.5
 * @author Adrian
 *
 */
public class HsbcSpider implements Spider, Runnable {
	
	String url = "https://www.hsbc.com.mx/1/2/es/personas/divisas";
	Document doc = null;
	Elements elements = null;
	HashMap<String, String> tcambio = null;

	/**
	 * Realiza la conexion al recurso del banco.
	 * @see ConnectSpider#connectToServer(String)
	 */
	public Document connectToServer() {
		doc = ConnectSpider.connectToServer(url);
		return doc;
	}

	/**
	 * Paresea el documento para extraer la data requerida.
	 * @param doc 
	 * @return HashMap con los datos
	 * @see Elements
	 */
	public HashMap<String, String> getData(Document doc) {
		Elements els = doc.select("tr > td");
		tcambio = new HashMap<String, String>();
		tcambio.put("compra", cleanValue(els.get(15).text()));
		tcambio.put("venta", cleanValue(els.get(16).text()));
		return tcambio;
	}
	
	/**
	 * Limpia el string para remover caracteres no deseados.
	 * @param data String con la data
	 * @return la data limpia
	 */
	public String cleanValue(String data) {
		String cleanString = data;
		cleanString = cleanString.replace("$", "");
		cleanString = cleanString.replace(" ", "");
		return cleanString.substring(0,5);
	}

	/**
	 * Implementacion de run() para realizar proceso en segundo plano, 
	 * ademas de persistir la data en la base de datos usando EntityManager.
	 * 
	 * @see Runnable
	 * @see EntityManager
	 * @see TdcBackgroundTask
	 */
	public void run() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cambiodolar");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		HashMap<String, String> tipo_de_cambio_hsbc = new HashMap<String, String>();
		
		Hsbc hsbcBean = new Hsbc();
		Spider hsbc = new HsbcSpider();
		tipo_de_cambio_hsbc = hsbc.getData(hsbc
				.connectToServer());
		hsbcBean.setCompra(Float.parseFloat(tipo_de_cambio_hsbc
				.get("compra")));
		hsbcBean.setVenta(Float.parseFloat(tipo_de_cambio_hsbc
				.get("venta")));
		hsbcBean.setFecha(new Date());
		
		tx.begin();
		em.persist(hsbcBean);
		tx.commit();
		
	}
	
	
}
