package mx.tipodecambio.controller.spiders;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.jsoup.select.Elements;

import mx.tipodecambio.model.Skotiabank;
import mx.tipodecmabio.controller.TdcBackgroundTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementacion de Spider para Skotiabank.
 * @version 0.5
 * @author Adrian
 *
 */
public class SkotiabankSpider implements SpiderJson, Runnable {
	
	String url = "https://finanzasenlinea.infosel.com/inverlat/dolarinterbancario.ashx";
	String json = null;
	HashMap<String, String> tcambio = null;

	/**
	 * Realiza la conexion al recurso del banco.
	 * @see ConnectSpider#connectToServer(String)
	 */
	public String connectToServer() {
		json = ConnectSpider.connectToServerJson(url);
		return json;
	}

	/**
	 * Paresea el documento para extraer la data requerida.
	 * @param doc 
	 * @return HashMap con los datos
	 * @see Elements
	 */
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

	/**
	 * Limpia el string para remover caracteres no deseados.
	 * @param data String con la data
	 * @return la data limpia
	 */
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
		
		HashMap<String, String> tipo_de_cambio_skotiabank = new HashMap<String, String>();
		
		Skotiabank skotiabankBean = new Skotiabank();
		SpiderJson skotiabank = new SkotiabankSpider();
		tipo_de_cambio_skotiabank = skotiabank.getData(skotiabank
				.connectToServer());
		skotiabankBean.setCompra(Float.parseFloat(tipo_de_cambio_skotiabank
				.get("compra")));
		skotiabankBean.setVenta(Float.parseFloat(tipo_de_cambio_skotiabank
				.get("venta")));
		skotiabankBean.setFecha(new Date());
		
		tx.begin();
		em.persist(skotiabankBean);
		tx.commit();
		
	}

}
