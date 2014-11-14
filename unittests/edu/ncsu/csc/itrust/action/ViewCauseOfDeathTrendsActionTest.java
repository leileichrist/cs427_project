package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.CauseOfDeathBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewCauseOfDeathTrendsActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewCauseOfDeathTrendsAction action;
	
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		action = new ViewCauseOfDeathTrendsAction(TestDAOFactory.getTestInstance());	
	}
	
	public void testGetCauseOfDeathTrends_hcp() throws DBException {
		Long hcp = new Long(9000000000L);
		String start = "1900-01-01";
		String end = "2014-12-31";
		String gender = "All";
		List<CauseOfDeathBean> dsBeans = action.getCauseOfDeathTrends(hcp, start, end, gender);
		assertEquals(7, dsBeans.get(0).getQuantityOfDeath());
		assertEquals("Influenza", dsBeans.get(0).getName());
		assertEquals("487.00", dsBeans.get(0).getIcdcode());
		assertEquals(2, dsBeans.get(1).getQuantityOfDeath());
		assertEquals("Diabetes with ketoacidosis", dsBeans.get(1).getName());
		assertEquals("250.10", dsBeans.get(1).getIcdcode());
	}
	
	public void testGetCauseOfDeathTrends() throws DBException {
		String start = "1900-01-01";
		String end = "2014-12-31";
		String gender = "All";
		List<CauseOfDeathBean> dsBeans = action.getCauseOfDeathTrends(null, start, end, gender);
		assertEquals(7, dsBeans.get(0).getQuantityOfDeath());
		assertEquals("Influenza", dsBeans.get(0).getName());
		assertEquals("487.00", dsBeans.get(0).getIcdcode());
		assertEquals(3, dsBeans.get(1).getQuantityOfDeath());
		assertEquals("Diabetes with ketoacidosis", dsBeans.get(1).getName());
		assertEquals("250.10", dsBeans.get(1).getIcdcode());
	}
	
	public void testGetCauseOfDeathTrends_hcp_male() throws DBException {
		Long hcp = new Long(9000000000L);
		String start = "1900-01-01";
		String end = "2014-12-31";
		String gender = "Male";
		List<CauseOfDeathBean> dsBeans = action.getCauseOfDeathTrends(hcp, start, end, gender);
		assertEquals(4, dsBeans.get(0).getQuantityOfDeath());
		assertEquals("Influenza", dsBeans.get(0).getName());
		assertEquals("487.00", dsBeans.get(0).getIcdcode());
		assertEquals(2, dsBeans.get(1).getQuantityOfDeath());
		assertEquals("Diabetes with ketoacidosis", dsBeans.get(1).getName());
		assertEquals("250.10", dsBeans.get(1).getIcdcode());
	}
	
	public void testGetCauseOfDeathTrends_hcp_female() throws DBException {
		Long hcp = new Long(9000000000L);
		String start = "1900-01-01";
		String end = "2014-12-31";
		String gender = "Female";
		List<CauseOfDeathBean> dsBeans = action.getCauseOfDeathTrends(hcp, start, end, gender);
		assertEquals(3, dsBeans.get(0).getQuantityOfDeath());
		assertEquals("Influenza", dsBeans.get(0).getName());
		assertEquals("487.00", dsBeans.get(0).getIcdcode());
		assertEquals(2, dsBeans.get(1).getQuantityOfDeath());
		assertEquals("Diabetes with other coma", dsBeans.get(1).getName());
		assertEquals("250.30", dsBeans.get(1).getIcdcode());
	}
	
	public void testGetCauseOfDeathTrends_all_male() throws DBException {
		String start = "1900-01-01";
		String end = "2014-12-31";
		String gender = "male";
		List<CauseOfDeathBean> dsBeans = action.getCauseOfDeathTrends(null, start, end, gender);
		assertEquals(4, dsBeans.get(0).getQuantityOfDeath());
		assertEquals("Influenza", dsBeans.get(0).getName());
		assertEquals("487.00", dsBeans.get(0).getIcdcode());
		assertEquals(3, dsBeans.get(1).getQuantityOfDeath());
		assertEquals("Diabetes with ketoacidosis", dsBeans.get(1).getName());
		assertEquals("250.10", dsBeans.get(1).getIcdcode());
	}
	
	public void testGetCauseOfDeathTrends_all_female() throws DBException {
		String start = "1900-01-01";
		String end = "2014-12-31";
		String gender = "Female";
		List<CauseOfDeathBean> dsBeans = action.getCauseOfDeathTrends(null, start, end, gender);
		assertEquals(3, dsBeans.get(0).getQuantityOfDeath());
		assertEquals("Influenza", dsBeans.get(0).getName());
		assertEquals("487.00", dsBeans.get(0).getIcdcode());
		assertEquals(2, dsBeans.get(1).getQuantityOfDeath());
		assertEquals("Diabetes with other coma", dsBeans.get(1).getName());
		assertEquals("250.30", dsBeans.get(1).getIcdcode());
	}

}
