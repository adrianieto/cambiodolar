package mx.tipodecmabio.controller;

import java.io.IOException;
//import java.util.Calendar;
import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.tipodecambio.controller.spiders.*;
import mx.tipodecambio.model.Banamex;
import mx.tipodecambio.model.Bancomer;
import mx.tipodecambio.model.Banorte;
import mx.tipodecambio.model.Hsbc;
import mx.tipodecambio.model.Santander;
import mx.tipodecambio.model.Skotiabank;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("cambiodolar");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Date now = new Date();

		HashMap<String, String> tipo_de_cambio_banamex = new HashMap<String, String>();
		HashMap<String, String> tipo_de_cambio_bancomer = new HashMap<String, String>();
		HashMap<String, String> tipo_de_cambio_banorte = new HashMap<String, String>();
		HashMap<String, String> tipo_de_cambio_hsbc = new HashMap<String, String>();
		HashMap<String, String> tipo_de_cambio_santander = new HashMap<String, String>();
		HashMap<String, String> tipo_de_cambio_skotia = new HashMap<String, String>();

		
		Banamex banamexBean = null;
		Bancomer bancomerBean = null;
		Banorte banorteBean = null;
		Hsbc hsbcBean = null;
		Santander santanderBean = null;
		Skotiabank skotiaBean = null;
		
		banamexBean = new Banamex();
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
			banamexBean.setFecha(now);

			bancomerBean = new Bancomer();
			Spider bancomer = new BancomerSpider();
			tipo_de_cambio_bancomer = bancomer.getData(bancomer
					.connectToServer());
			bancomerBean.setCompra(Float.parseFloat(tipo_de_cambio_bancomer
					.get("compra")));
			bancomerBean.setVenta(Float.parseFloat(tipo_de_cambio_bancomer
					.get("venta")));
			bancomerBean.setFecha(now);

			banorteBean = new Banorte();
			Spider banorte = new BanorteSpider();
			tipo_de_cambio_banorte = banorte.getData(banorte.connectToServer());
			banorteBean.setCompra(Float.parseFloat(tipo_de_cambio_banorte
					.get("compra")));
			banorteBean.setVenta(Float.parseFloat(tipo_de_cambio_banorte
					.get("venta")));
			banorteBean.setFecha(now);
			
			hsbcBean = new Hsbc();
			Spider hsbc = new HsbcSpider();
			tipo_de_cambio_hsbc = hsbc.getData(hsbc.connectToServer());
			hsbcBean.setCompra(Float.parseFloat(tipo_de_cambio_hsbc
					.get("compra")));
			hsbcBean.setVenta(Float.parseFloat(tipo_de_cambio_hsbc
					.get("venta")));
			hsbcBean.setFecha(now);
			
			santanderBean = new Santander();
			SpiderJson santander = new SantanderSpider();
			tipo_de_cambio_santander = santander.getData(santander.connectToServer());
			santanderBean.setCompra(Float.parseFloat(tipo_de_cambio_santander
					.get("compra")));
			santanderBean.setVenta(Float.parseFloat(tipo_de_cambio_santander
					.get("venta")));
			santanderBean.setFecha(now);
			
			skotiaBean = new Skotiabank();
			SpiderJson skotia = new SkotiabankSpider();
			tipo_de_cambio_skotia = skotia.getData(skotia.connectToServer());
			skotiaBean.setCompra(Float.parseFloat(tipo_de_cambio_skotia
					.get("compra")));
			skotiaBean.setVenta(Float.parseFloat(tipo_de_cambio_skotia
					.get("venta")));
			skotiaBean.setFecha(now);

			tx.begin();
			em.persist(banamexBean);
			em.persist(bancomerBean);
			em.persist(banorteBean);
			em.persist(hsbcBean);
			em.persist(santanderBean);
			em.persist(skotiaBean);
			tx.commit();
		
		request.setAttribute("Banorte", banorteBean);
		request.setAttribute("Banamex", banamexBean);
		request.setAttribute("Bancomer", bancomerBean);
		request.setAttribute("HSBC", hsbcBean);
		request.setAttribute("Santander", santanderBean);
		request.setAttribute("Skotiabank", skotiaBean);
		request.getRequestDispatcher("tdcmain.jsp").forward(request, response);
	}

	//
	// private HashMap<String, Integer> getTime(){
	// HashMap<String, Integer> time = new HashMap<String, Integer>();
	// Date date = new Date(System.currentTimeMillis());
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(date);
	// int hora = cal.get(Calendar.HOUR_OF_DAY);
	// int minuto = cal.get(Calendar.MINUTE);
	// time.put("hora", hora);
	// time.put("minuto", minuto);
	// return time;
	// }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
