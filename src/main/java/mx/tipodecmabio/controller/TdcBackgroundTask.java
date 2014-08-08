package mx.tipodecmabio.controller;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Application Lifecycle Listener implementation class TdcBackgroundTask
 *
 */
public class TdcBackgroundTask implements ServletContextListener {

    private ScheduledThreadPoolExecutor executor = null;
	
    public TdcBackgroundTask() {

    }

	/**
	 * 
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         
         executor = new ScheduledThreadPoolExecutor(6);
         
         String[] tasks = sce.getServletContext().getInitParameter("BackgroundTasks").split(",(\\s)*");
         
         for(int i=0;i<tasks.length;i++){
        	 try{
            	 Runnable object = (Runnable)Class.forName(tasks[i]).newInstance();
            	 executor.scheduleAtFixedRate(object, 0, 5, TimeUnit.MINUTES);
             }catch (Exception e){
            	 e.printStackTrace();
             }
         }
         
    }
    
    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  {
    	
    	this.executor.shutdown();
    }
	
}
