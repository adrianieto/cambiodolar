package mx.tipodecambio.controller.spiders;

import java.util.HashMap;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Adrian
 *
 */
public interface Spider {
	public Document connectToServer();
	public HashMap<String, String> getData(Document doc);
	public String cleanValue(String data);
}

