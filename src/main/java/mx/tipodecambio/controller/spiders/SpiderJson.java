package mx.tipodecambio.controller.spiders;

import java.util.HashMap;

public interface SpiderJson {
	public String connectToServer();
	public HashMap<String, String> getData(String json);
	public String cleanValue(String data);
}
