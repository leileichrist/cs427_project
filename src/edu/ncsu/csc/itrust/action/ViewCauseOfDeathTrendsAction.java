package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.CauseOfDeathBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CauseOfDeathDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class ViewCauseOfDeathTrendsAction {
	private CauseOfDeathDAO causeOfDeathDAO;

	/**
	 * Constructor for the action. Initializes DAO fields
	 * 
	 * @param factory
	 *            The session's factory for DAOs
	 */
	public ViewCauseOfDeathTrendsAction(DAOFactory factory) {
		this.causeOfDeathDAO = factory.getCauseOfDeathDAO();
	}

	/**
	 * Use a DAO to get cause of death data from database
	 * @param hcp
	 * @param start
	 * @param end
	 * @param gender
	 * @return dsbeans
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public List<CauseOfDeathBean> getCauseOfDeathTrends(Long hcp, String start,
			String end, String gender) throws DBException {
		List<CauseOfDeathBean> dsBeans;
		dsBeans = causeOfDeathDAO.getTopTwoCausesOfDeath(hcp, start,
				end, gender);
		return dsBeans;
	}
}
