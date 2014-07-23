package mx.tipodecmabio.controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		Query query = em.createQuery("SELECT t FROM Banorte t ORDER BY t.id DESC").setMaxResults(1);
		Banorte banorteBean = (Banorte)query.getSingleResult();
		query = em.createQuery("SELECT t FROM Banamex t ORDER BY t.id DESC").setMaxResults(1);
		Banamex banamexBean = (Banamex)query.getSingleResult();
		query = em.createQuery("SELECT t FROM Bancomer t ORDER BY t.id DESC").setMaxResults(1);
		Bancomer bancomerBean = (Bancomer)query.getSingleResult();
		query = em.createQuery("SELECT t FROM Hsbc t ORDER BY t.id DESC").setMaxResults(1);
		Hsbc hsbcBean = (Hsbc)query.getSingleResult();
		query = em.createQuery("SELECT t FROM Santander t ORDER BY t.id DESC").setMaxResults(1);
		Santander santanderBean = (Santander)query.getSingleResult();
		query = em.createQuery("SELECT t FROM Skotiabank t ORDER BY t.id DESC").setMaxResults(1);
		Skotiabank skotiaBean = (Skotiabank)query.getSingleResult();
		
		
		request.setAttribute("Banorte", banorteBean);
		request.setAttribute("Banamex", banamexBean);
		request.setAttribute("Bancomer", bancomerBean);
		request.setAttribute("HSBC", hsbcBean);
		request.setAttribute("Santander", santanderBean);
		request.setAttribute("Skotiabank", skotiaBean);
		request.getRequestDispatcher("tdcmain.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
