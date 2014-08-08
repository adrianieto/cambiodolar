package mx.tipodecambio.controller.spiders;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Conecta las aranas de los diferentes bancos proporcionando
 * la url de banco que invoca a los metodos de la clase
 * 
 * @author Adrian
 * @version 0.90 08/08/14
 *
 */
public class ConnectSpider {
	
	/** Realiza la conexion a los servidores usando GET
	 *  y cacha las distintas excepciones
	 *  
	 * @param url direccion a la que se conecta
	 * @return regresa un objeto Document
	 */
	public static Document connectToServer(String url) {
		Logger log = LoggerFactory.getLogger(ConnectSpider.class);
		Document doc = null;;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30").get();
		} catch (HttpStatusException e) {
			try{
				doc = Jsoup.connect(url).get();
			}catch (IOException ex){
				log.info("Inside HttpStatusException");
				ex.printStackTrace();
			}
		} catch (SocketTimeoutException e){
			try{
				doc = Jsoup.connect(url).get();
			}catch (IOException ex){
				log.info("Inside SocketTimeoutException");
				ex.printStackTrace();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		return doc;
	}
	
	
	/** Realiza la conexion a los servidores usando GET JSON
	 * y cacha las distintas excepciones
	 * 
	 * @param url direccion a la que se conecta
	 * @return objeto JSON en forma de un String
	 */
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
