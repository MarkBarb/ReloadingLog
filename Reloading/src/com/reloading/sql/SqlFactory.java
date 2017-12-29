package com.reloading.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PropertyResourceBundle;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.reloading.Constants;
import com.reloading.components.Bullet;
import com.reloading.components.Cartridge;
import com.reloading.components.Case;
import com.reloading.components.CastBullet;
import com.reloading.components.FactoryLoad;
import com.reloading.components.Firearm;
import com.reloading.components.Load;
import com.reloading.components.Powder;
import com.reloading.components.Primer;
import com.reloading.components.Reload;
import com.reloading.exceptions.ReloadingException;
import com.reloading.factory.Factory;
import com.reloading.target.TargetEvaluatorShot;
import com.reloading.target.TargetEvaluatorTest;
import com.reloading.testing.Shot;
import com.reloading.testing.Test;

public class SqlFactory extends Factory {
	private static final Logger LOGGER = Logger.getLogger("Sql Factory");
	private static SessionFactory sessionFactory = null;

	protected static final String HIBERNATE_CFG_KEY = "HIBERNATE_CFG_FILE";
	protected static String hibernateCfg = "";

	protected static final String BULLETFILE_KEY = "BULLETFILE";
	protected static final String CARTRIDGEFILE_KEY = "CARTRIDGEFILE";
	protected static final String CASEFILE_KEY = "CASEFILE";
	protected static final String FIREARMSFILE_KEY = "FIREARMFILE";
	protected static final String LOADFILE_KEY = "LOADFILE";
	protected static final String POWDERFILE_KEY = "POWDERFILE";
	protected static final String PRIMERFILE_KEY = "PRIMERFILE";
	protected static final String TARGETTESTFILE_KEY = "TARGETTESTFILE";
	protected static final String TARGETTESTSHOTFILE_KEY = "TARGETTESTSHOTFILE";
	

	protected static final String CARTRIDGE_MAP_KEY = "CARTRIDGE_MAP";

	private String bulletsFileName = "";
	private String cartridgesFileName = "";
	private String casesFileName = "";
	private String powdersFileName = "";
	private String primersFileName = "";
	private String loadsFileName = "";
	private String firearmsFileName = "";
	private String targetTestFileName = "";
	private String targetTestShotFileName = "";
	

	public SqlFactory() {
		// TODO Auto-generated constructor stub
	}

	public SqlFactory(String resourceFileName) {
		super(resourceFileName);
		setFactory();
	}

	@Override

	public void setPropertyResourceBundle(PropertyResourceBundle propertyResourceBundle) {
		this.propertyResourceBundle = propertyResourceBundle;
		setFactory();
	}

	private void setFactory() {
		hibernateCfg = propertyResourceBundle.getString(HIBERNATE_CFG_KEY);
		System.out.println("\n\n" + hibernateCfg + "\n\n");
		// get the mapping file names
		bulletsFileName = propertyResourceBundle.getString(BULLETFILE_KEY);
		cartridgesFileName = propertyResourceBundle.getString(CARTRIDGEFILE_KEY);
		casesFileName = propertyResourceBundle.getString(CASEFILE_KEY);
		powdersFileName = propertyResourceBundle.getString(POWDERFILE_KEY);
		primersFileName = propertyResourceBundle.getString(PRIMERFILE_KEY);
		loadsFileName = propertyResourceBundle.getString(LOADFILE_KEY);
		firearmsFileName = propertyResourceBundle.getString(FIREARMSFILE_KEY);
		targetTestFileName  = propertyResourceBundle.getString(TARGETTESTFILE_KEY);
		targetTestShotFileName = propertyResourceBundle.getString(TARGETTESTSHOTFILE_KEY);
		
		try {
			Configuration configuration = new Configuration();
			configuration.configure(hibernateCfg);
			configuration.addResource(cartridgesFileName);
			configuration.addResource(casesFileName);
			configuration.addResource(bulletsFileName);
			configuration.addResource(primersFileName);
			configuration.addResource(powdersFileName);
			configuration.addResource(loadsFileName);
			configuration.addResource(firearmsFileName);
			configuration.addResource(targetTestFileName);
			configuration.addResource(targetTestShotFileName);
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			throw e;
		} finally {
			// if (sessionFactory != null) {
			// sessionFactory.close();
			// }
		}

	}

	@Override
	public Bullet getBulletByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bullet> getBullets() {
		ArrayList<Bullet> bullets = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Bullet> list = (List<Bullet>) session.createQuery("from Bullet").list();
			bullets = new ArrayList<Bullet>(list);

			// List<CastBullet> castList = (List<CastBullet>)
			// session.createQuery("from CastBullet").list();
			// for(int idx = 0;idx < castList.size();idx++){
			// CastBullet castBullet = castList.get(idx);
			// bullets.add(castBullet);
			// }

			Collections.sort(bullets);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return bullets;
	}

	@Override
	public void saveBullet(Bullet bullet) throws ReloadingException {
		String className = bullet.getClass().getName();
		System.out.println(className);
		switch (className) {

		case Constants.CASTBULLET_CLASSNAME:
			CastBullet castBullet = (CastBullet) bullet;
			saveCastBullet(castBullet);
			break;

		default:
			saveFactoryBullet(bullet);
			break;
		}
	}

	private void saveCastBullet(CastBullet bullet) throws ReloadingException {
		int bulletID = bullet.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (bulletID > 0) {
				session.update(bullet);
			} else {
				session.save(bullet);
				System.out.println("Save: " + bullet.getId());
				int bulletId = bullet.getId();
				//Query query = session
				//		.createSQLQuery("update Bullets set Cast = 1" + " where ID = :bulletId");
				//query.setParameter("bulletId", bulletId);
				//int result = query.executeUpdate();
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private void saveFactoryBullet(Bullet bullet) throws ReloadingException {
		int bulletID = bullet.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (bulletID > 0) {
				session.update(bullet);
			} else {
				session.save(bullet);
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public Cartridge getCartridgeByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Cartridge> getCartridges() {
		ArrayList<Cartridge> cartridges = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Cartridge> list = (List<Cartridge>) session.createQuery("from Cartridge").list();
			cartridges = new ArrayList<Cartridge>(list);

			Collections.sort(cartridges);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return cartridges;
	}

	@Override
	public void saveCartridge(Cartridge cartridge) throws ReloadingException {
		int cartridgeID = cartridge.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (cartridgeID > 0) {
				session.update(cartridge);
			} else {
				session.save(cartridge);
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public Case getCaseByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Case> getCases() {
		ArrayList<Case> cases = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Case> list = (List<Case>) session.createQuery("from Case").list();
			cases = new ArrayList<Case>(list);
			Collections.sort(cases);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return cases;
	}

	@Override
	public void saveCase(Case nCase) throws ReloadingException {
		int nCaseID = nCase.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (nCaseID > 0) {
				session.update(nCase);
			} else {
				session.save(nCase);
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public Load getLoadByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Load> getLoads() {
		ArrayList<Load> loads = new ArrayList<Load>();
		Session session = null;
		try {
			System.out.println("getLoads trying to open session");
			session = sessionFactory.openSession();
			System.out.println("getLoads trying to get factory loads");
			List<FactoryLoad> factoryLoads = (List<FactoryLoad>) session.createQuery("from FactoryLoad").list();
			for (int index = 0; index < factoryLoads.size(); index++) {
				FactoryLoad fLoad = factoryLoads.get(index);
				loads.add(fLoad);
			}
			System.out.println("getLoads trying to get reloads loads");
			List<Reload> reloads = (List<Reload>) session.createQuery("from Reload").list();
			for (int index = 0; index < reloads.size(); index++) {
				Reload reload = reloads.get(index);
				loads.add(reload);
			}

			Collections.sort(loads);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return loads;
	}

	@Override
	public ArrayList<Load> getLoadsByCartridgeId(int cartridgeId) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public void saveLoadx(Load load) throws ReloadingException {

		String className = load.getClass().getName();
		switch (className) {
		case Constants.FACTORYLOAD_CLASSNAME:
			saveFactoryLoad(load);
			break;
		case Constants.RELOAD_CLASSNAME:
			Reload reload = (Reload) load;
			saveReload(reload);
			break;
		default:
		}
	}
	@Override
	public void saveLoad(Load load) throws ReloadingException {
		String className = load.getClass().getName();

		int loadID = load.getId();

		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			FactoryLoad tempLoad = new FactoryLoad();
			transaction.begin();
			//tempLoad.setId(load.getId());
			//tempLoad.setCartridge(load.getCartridge());
			//tempLoad.setComments(load.getComments());
			//tempLoad.setManufacturer(load.getManufacturer());
			//tempLoad.setName(load.getName());
			//tempLoad.setShortName(load.getShortName());
			if (loadID > 0) {
				session.update(load);
			} else {
				session.save(load);
				loadID = load.getId();
				System.out.println("loadID: " + loadID);
				//load.setId(loadID);
				session.refresh(load);
			}

			transaction.commit();
			

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}
		//switch (className) {
		//case Constants.FACTORYLOAD_CLASSNAME:
		//	break;
		//case Constants.RELOAD_CLASSNAME:
		//	Reload reload = (Reload) load;
		//	saveReload(reload);
		//	break;
		//default:
		//}
	}

	private void saveFactoryLoad(Load load) throws ReloadingException {

		int loadID = load.getId();

		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (loadID > 0) {
				session.update(load);
			} else {
				session.save(load);
				session.refresh(load);
			}

			transaction.commit();
		}catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	private void saveReload(Reload load) throws ReloadingException {
		int loadId = load.getId();
		String name = load.getName();
		String shortName = load.getShortName();
		String manufacturer = load.getManufacturer();
		int bulletId = load.getBulletId();
		int cartridgeId = load.getCartridgeId();
		int caseId = load.getCasingId();
		float powderCharge = load.getPowderCharge();
		int powderId = load.getPowderId();
		String powderMeasureSetting = load.getPowderMeasureSetting();
		int primerId = load.getPrimerId();
		String comments = load.getComments();

		System.out.println("\n\n loadId" + loadId 
				+ "\n bulletId: " + bulletId 
				+ "\n cartridgeId: " + cartridgeId
				+ "\n caseId: " + caseId
				+ "\n powderCharge: " + powderCharge 
				+ "\n powderId: " + powderId 
				+ "\n powderMeasureSetting: "+ powderMeasureSetting
				+ "\n comments: "+ comments);

		int loadID = load.getId();
		Session session = null;
		try {
			System.out.println("loadID: " + loadID);

			String queryString = "";
			if (loadID <= 0){
				queryString = "insert into reloadinglog.loads( ";
				queryString += "ShortName ";
				queryString += ",Name ";
				queryString += ",Manufacturer ";
				queryString += ",Reload ";
				queryString += ",BulletId ";
				queryString += ",CartridgeId ";
				queryString += ",CaseId ";
				queryString += ",PowderCharge ";
				queryString += ",PowderId ";
				queryString += ",PowderMeasureSetting ";
				queryString += ",PrimerId ";
				queryString += ",Comments ";
				queryString += ") VALUES ( ";
				queryString += ":shortName ";
				queryString += ",:name ";
				queryString += ",:manufacturer ";
				queryString += ",1 ";
				queryString += ",:bulletId ";
				queryString += ",:cartridgeId ";
				queryString += ",:caseId ";
				queryString += ",:powderCharge ";
				queryString += ",:powderId ";
				queryString += ",:powderMeasureSetting ";
				queryString += ",:primerId ";
				queryString += ",:comments ";
				queryString += ")";
			}
			else{
				queryString = "update reloadinglog.loads set Name = :name ";
				queryString += ",ShortName = :shortName ";
				queryString += ",Manufacturer = :manufacturer ";
				queryString += ",BulletId = :bulletId ";
				queryString += ",CartridgeId = :cartridgeId ";
				queryString += ",CaseId = :caseId ";
				queryString += ",PowderCharge = :powderCharge ";
				queryString += ",PowderId = :powderId ";
				queryString += ",PowderMeasureSetting = :powderMeasureSetting ";
				queryString += ",PrimerId= :primerId ";
				queryString += ",Comments= :comments ";
				queryString += "where ID = " + loadId;
			}
			System.out.println(queryString);
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			
			//Query query = session.createSQLQuery(queryString);
			Query query = session.createSQLQuery(queryString);
			query.setParameter("name", name);
			query.setParameter("shortName", shortName);
			query.setParameter("manufacturer", manufacturer);
			query.setParameter("bulletId", bulletId);
			query.setParameter("cartridgeId", cartridgeId);
			query.setParameter("caseId", caseId);
			query.setParameter("powderCharge", powderCharge);
			query.setParameter("powderId", powderId);
			query.setParameter("powderMeasureSetting", powderMeasureSetting);
			query.setParameter("primerId", primerId);
			query.setParameter("comments", comments);
			
			int result = query.executeUpdate();
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Powder getPowderByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Powder> getPowders() {
		ArrayList<Powder> powders = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Powder> list = (List<Powder>) session.createQuery("from Powder").list();
			powders = new ArrayList<Powder>(list);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		Collections.sort(powders);
		return powders;
	}

	@Override
	public void savePowder(Powder powder) throws ReloadingException {
		int powderID = powder.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (powderID > 0) {
				session.update(powder);
			} else {
				session.save(powder);
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public Primer getPrimerByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Primer> getPrimers() {
		ArrayList<Primer> primers = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Primer> list = (List<Primer>) session.createQuery("from Primer").list();
			primers = new ArrayList<Primer>(list);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return primers;
	}

	@Override
	public void savePrimer(Primer primer) throws ReloadingException {
		int primerID = primer.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (primerID > 0) {
				session.update(primer);
			} else {
				session.save(primer);
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public Firearm getFirearmByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Firearm> getFirearms() {
		ArrayList<Firearm> firearms = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Firearm> list = (List<Firearm>) session.createQuery("from Firearm").list();
			firearms = new ArrayList<Firearm>(list);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return firearms;
	}

	@Override
	public void saveFirearm(Firearm firearm) throws ReloadingException {
		// TODO Auto-generated method stub
		int firearmID = firearm.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (firearmID > 0) {
				session.update(firearm);
			} else {
				session.save(firearm);
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}

	
	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public Test getTestByID(int id){
		Test test = new TargetEvaluatorTest();
		return test;
	}
	public ArrayList<Test> getTests(){
		ArrayList<Test> tests=  new ArrayList<Test>();Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Test> list = (List<Test>) session.createQuery("from TargetEvaluatorTest").list();
			tests = new ArrayList<Test>(list);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return tests;
	}
	
	public void saveTest(Test test) throws ReloadingException{
		int testId = test.getId();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			if (testId > 0) {
				session.update(test);
			} else {
				session.save(test);
			}
			
			if (test instanceof TargetEvaluatorTest){
				TargetEvaluatorTest tETest = (TargetEvaluatorTest) test;
				ArrayList<Shot> shots = tETest.getShotsList();
				for (int sIdx=0;sIdx < shots.size();sIdx++){
					TargetEvaluatorShot shot = (TargetEvaluatorShot)shots.get(sIdx);
					saveShot(session,shot);
				}
			}
			transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return;
	}

	
	/****************************************************************/
	/*                                                              */
	/****************************************************************/
	
	public Shot getShotByID(int id){
		Shot shot = new TargetEvaluatorShot();
		return shot;
	}
	/*
	public ArrayList<Shot> getShots(){
		ArrayList<Shot> shots=  new ArrayList<Shot>();Session session = null;
		try {
			session = sessionFactory.openSession();
			List<Shot> list = (List<Shot>) session.createQuery("from TargetEvaluatorShot").list();
			shots = new ArrayList<Shot>(list);

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return shots;
	}
	*/
	
	public void saveShot(Session session, Shot shot) throws ReloadingException{
		int shotId = shot.getId();
		//Session session = null;
		try {
			//session = sessionFactory.openSession();
			//Transaction transaction = session.getTransaction();
			//transaction.begin();
			if (shotId > 0) {
				session.update(shot);
			} else {
				session.save(shot);
			}
			//transaction.commit();

		} catch (Exception e) {
			LOGGER.log(Level.FATAL, e.getMessage(), e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			ReloadingException rException = new ReloadingException();
			rException.setStackTrace(e.getStackTrace());
			throw rException;

		} 
		return;
	}
	
	/******************************************************************/
	/* Hibernate stuff */
	/******************************************************************/

	public static void main(String args[]) {
		System.out.println("Configuration File: " + args[0]);
		SqlFactory factory = new SqlFactory(args[0]);
		//factory.run();
		System.out.println("\n*********************************************");
		System.out.println("            Cartridges");
		System.out.println("*********************************************\n");
		ArrayList<Cartridge> cartridges = factory.getCartridges();
		for (int idx = 0; idx < cartridges.size(); idx++) {
			Cartridge cartridge = cartridges.get(idx);
			System.out.println(cartridge.getShortName());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Cases");
		System.out.println("*********************************************\n");
		ArrayList<Case> cases = factory.getCases();
		for (int idx = 0; idx < cases.size(); idx++) {
			Case ncase = cases.get(idx);
			System.out.println(ncase.getShortName() + "  " + ncase.getCartridge().getShortName());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Bullets");
		System.out.println("*********************************************\n");
		ArrayList<Bullet> bullets = factory.getBullets();
		for (int idx = 0; idx < bullets.size(); idx++) {
			Bullet bullet = bullets.get(idx);
			System.out.println(
					bullet.getClass().toString() + " " + bullet.getShortName() + "  " + bullet.getManufacturer());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Powders");
		System.out.println("*********************************************\n");
		ArrayList<Powder> powders = factory.getPowders();
		for (int idx = 0; idx < powders.size(); idx++) {
			Powder powder = powders.get(idx);
			System.out.println(
					powder.getClass().toString() + " " + powder.getShortName() + "  " + powder.getManufacturer());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Primers");
		System.out.println("*********************************************\n");
		ArrayList<Primer> primers = factory.getPrimers();
		for (int idx = 0; idx < primers.size(); idx++) {
			Primer primer = primers.get(idx);
			System.out.println(
					primer.getClass().toString() + " " + primer.getShortName() + "  " + primer.getManufacturer());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Loads");
		System.out.println("*********************************************\n");
		ArrayList<Load> loads = factory.getLoads();
		for (int idx = 0; idx < loads.size(); idx++) {
			Load load = loads.get(idx);
			System.out.println(load.getManufacturer());
			System.out.println(load.getClass().toString());
			System.out.println(load.getCartridge().getShortName());
		}

		System.out.println("\n*********************************************");
		System.out.println("            Firearms");
		System.out.println("*********************************************\n");
		ArrayList<Firearm> firearms = factory.getFirearms();
		for (int idx = 0; idx < firearms.size(); idx++) {
			Firearm firearm = firearms.get(idx);
			System.out.println(firearm.getClass().toString() + " " + firearm.getModel());
		
		}
		System.out.println("\n*********************************************");
		System.out.println("            Tests");
		System.out.println("*********************************************\n");
		ArrayList<Test> tests = factory.getTests();
		for (int idx = 0; idx < tests.size(); idx++) {
			TargetEvaluatorTest test = (TargetEvaluatorTest) tests.get(idx);
			System.out.println("\nGroup Size: " + Double.toString(test.getGroupSize()));
			System.out.println("Average Velocity: " + Double.toString(test.getAverageVelocity()));
			ArrayList<Shot> shots =  test.getShotsList();
			Collections.sort(shots);
			for (int sIdx = 0; sIdx < shots.size();sIdx++){
				TargetEvaluatorShot shot = (TargetEvaluatorShot) shots.get(sIdx);
				System.out.println("\tShot: " + Integer.toString(shot.getShotNumber()) 
				+ " Velocity: " + Double.toString(shot.getVelocity()) 
				+ " Windage: " + Double.toString(shot.getWindage())
				+ " RawWindage: " + Double.toString(shot.getRawWindage())
				+ " Elevation: " + Double.toString(shot.getElevation())
				+ " RawElevation: " + Double.toString(shot.getRawElevation()));
			}
			test.recalculateAllPoints();
			System.out.println("\nGroup Size: " + Double.toString(test.getGroupSize()));
			System.out.println("Average Velocity: " + Double.toString(test.getAverageVelocity()));
			shots =  test.getShotsList();
			Collections.sort(shots);
			for (int sIdx = 0; sIdx < shots.size();sIdx++){
				TargetEvaluatorShot shot = (TargetEvaluatorShot) shots.get(sIdx);
				System.out.println("\tShot: " + Integer.toString(shot.getShotNumber()) 
				+ " Velocity: " + Double.toString(shot.getVelocity()) 
				+ " Windage: " + Double.toString(shot.getWindage())
				+ " RawWindage: " + Double.toString(shot.getRawWindage())
				+ " Elevation: " + Double.toString(shot.getElevation())
				+ " RawElevation: " + Double.toString(shot.getRawElevation()));
			}
			try{
				factory.saveTest(test);
			}
			catch(ReloadingException ex){
				ex.printStackTrace();
			}
			
		}
	}

	public void run() {
		Cartridge cartridge = new Cartridge();
		cartridge.setId(-1);
		cartridge.setName(".30-06 Springfield");
		cartridge.setShortName(".30-06");
		cartridge.setNominalSize(".308");
		cartridge.setManufacturer("Springfield");

		try {
			saveCartridge(cartridge);
		} catch (ReloadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
