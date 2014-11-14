package edu.ncsu.csc.itrust.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewCauseOfDeathTrendsTest extends iTrustHTTPTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();

		gen.standardData();
	}

	/**
	 * Authenticate LHCP MID 9000000000 Password: pw StartYear: 1930, EndYear:
	 * 2014
	 * 
	 * @throws Exception
	 */
	public void testViewCauseOfDeathTrends_LHCP() throws Exception {
		// hcp views causeOfDeathTrends
		// login hcp
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());

		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// click Diagnosis Trends
		wr = wr.getLinkWith("Cause of Death Trends").click();

		WebForm form = wr.getFormWithID("formMain");
//		form.getScriptableObject().setParameterValue("gender", "All");
//		form.getScriptableObject().setParameterValue("patients", "myPatients");
//		form.getScriptableObject().setParameterValue("startYear", "1930");
//		form.getScriptableObject().setParameterValue("endYear", "2014");
		
		form.getSubmitButtonWithID("select_causeOfDeath").click();
	
		wr = wc.getCurrentPage();

		WebTable table = wr.getTableWithID("causeOfDeathTrendsTable");
		WebTable[] tables = wr.getTables();
		table=tables[0];
//		System.out.println(tables);
		
		if(table!=null){
			System.out.print(table.getRowCount());
		}
		
//		
//		assertTrue(table.getCellAsText(0, 1).contains("487.00"));
//		assertTrue(table.getCellAsText(1, 1).contains("Influenza"));
//		assertTrue(table.getCellAsText(1, 2).contains("7"));

	}

}
