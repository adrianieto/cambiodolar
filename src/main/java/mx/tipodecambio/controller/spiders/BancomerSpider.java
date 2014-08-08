package mx.tipodecambio.controller.spiders;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import mx.tipodecambio.model.Bancomer;
import mx.tipodecmabio.controller.TdcBackgroundTask;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Implementacion de Spider para Bancomer.
 * @version 0.5
 * @author Adrian
 *
 */
public class BancomerSpider implements Spider, Runnable {

	String url = "https://bbv.infosel.com/bancomerindicators/mainboard5V13.html";
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
		tcambio = new HashMap<String, String>();
		Elements els = doc.select("tr > td");
		tcambio.put("compra", els.get(10).text());
		tcambio.put("venta", els.get(13).text());
		return tcambio;
	}
	
	/**
	 * Limpia el string para remover caracteres no deseados.
	 * @param data String con la data
	 * @return la data limpia
	 */
	public String cleanValue(String data) {
		//String data = data.replace(arg0, arg1);
		return data;
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
		
		HashMap<String, String> tipo_de_cambio_bancomer = new HashMap<String, String>();
		
		Bancomer bancomerBean = new Bancomer();
		Spider bancomer = new BancomerSpider();
		tipo_de_cambio_bancomer = bancomer.getData(bancomer
				.connectToServer());
		bancomerBean.setCompra(Float.parseFloat(tipo_de_cambio_bancomer
				.get("compra")));
		bancomerBean.setVenta(Float.parseFloat(tipo_de_cambio_bancomer
				.get("venta")));
		bancomerBean.setFecha(new Date());
		
		tx.begin();
		em.persist(bancomerBean);
		tx.commit();
		
	}

}
