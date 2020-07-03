package Emulator.TechnicalServices.DataBaseAccess;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import Emulator.ApplicationLogic.Program;

public class ProgramDAO {
	
	private SessionFactory DBSessionFactory;
	private Session DBSession;
	
	private void openSession() {
		 Configuration configuration = new Configuration();
		 configuration.configure("hibernate.cfg.xml");
		 StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();     
		 DBSessionFactory = configuration.buildSessionFactory(serviceRegistry);
		 DBSession = DBSessionFactory.getCurrentSession();
	}
	private void closeSession() {
		DBSessionFactory.close();
	}
	
	public ProgramDAO(){
	}
	
	public ArrayList<Program> getPrograms() {
		openSession();
		DBSession.beginTransaction();
		
		Query query = DBSession.createQuery("FROM Emulator.ApplicationLogic.Program");
		ArrayList<Program> result = (ArrayList<Program>)query.list();
		
		DBSession.getTransaction().commit();
		closeSession();
		return result;
	}
	
	
	

}
