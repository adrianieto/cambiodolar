package mx.tipodecambio.controller.spiders;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import mx.tipodecambio.model.Banamex;
import mx.tipodecmabio.controller.TdcBackgroundTask;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Implementacion de Spider para Banamex.
 * @version 0.5
 * @author Adrian
 *
 */
public class BanamexSpider implements Spider, Runnable {

	/** Contiene la url del recurso a visitar*/
	String url = "http://portal.banamex.com.mx/c719_004/economiaFinanzas/es/home?xhost=http://www.banamex.com/";
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
		tcambio.put("compra", cleanValue(els.get(0).text()));
		tcambio.put("venta", cleanValue(els.get(4).text()));
		return tcambio;
	}
        
	
	/**
	 * Limpia el string para remover caracteres no deseados.
	 * @param data String con la data
	 * @return la data limpia
	 */
	public String cleanValue(String data) {
		return data.substring(0,5);
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
		
		
		//Add logic to see if it should add a new register or not
		
		tx.begin();
		em.persist(banamexBean);
		tx.commit();

	}

}
