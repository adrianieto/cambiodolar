package mx.tipodecambio.controller.spiders;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import mx.tipodecambio.model.Banorte;
import mx.tipodecmabio.controller.TdcBackgroundTask;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Implementacion de Spider para Banorte.
 * @version 0.5
 * @author Adrian
 *
 */
public class BanorteSpider implements Spider, Runnable {

	String url = "http://www.banorte.com/portal/personas/home.web";
	Document doc = null;
        StringBuilder KS=new StringBuilder();
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
		tcambio.put("compra", cleanValue(els.get(5).text()));
		tcambio.put("venta", cleanValue(els.get(6).text()));
		return tcambio;
	}
	

	/**
	 * Limpia el string para remover caracteres no deseados.
	 * @param data String con la data
	 * @return la data limpia
	 */
	public String cleanValue(String data) {
		String cleanedValue = data.replace("$", "");
                cleanedValue = cleanedValue.replace(",", ".");
		return cleanedValue;
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
		
		HashMap<String, String> tipo_de_cambio_banorte = new HashMap<String, String>();
		
		Banorte banorteBean = new Banorte();
		Spider banorte = new BanorteSpider();
		tipo_de_cambio_banorte = banorte.getData(banorte
				.connectToServer());
		banorteBean.setCompra(Float.parseFloat(tipo_de_cambio_banorte
				.get("compra")));
		banorteBean.setVenta(Float.parseFloat(tipo_de_cambio_banorte
				.get("venta")));
		banorteBean.setFecha(new Date());
		
		tx.begin();
		em.persist(banorteBean);
		tx.commit();
		
	}


}
