package edu.ncsu.csc.itrust.CS427_MP2;


import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class EditPersonnelTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testSearchAferEditPersonnel() throws Exception{
		WebConversation wc = login("9000000001", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		wr = wr.getLinkWith("Edit Personnel").click();
		WebForm personnelForm = wr.getForms()[1];
		personnelForm.getScriptableObject().setParameterValue("FIRST_NAME", "Kelly");
		personnelForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Found 1 Records"));
		
		//get the first personnel and click
		WebForm firstPersonnel = wr.getForms()[2];
		firstPersonnel.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/staff/editPersonnel.jsp", wr.getURL().toString());
		
		WebForm editPersonnel = wr.getForms()[0];
		editPersonnel.getScriptableObject().setParameterValue("firstName", "Kellyy");
		editPersonnel.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
		
		
		wr = wr.getLinkWith("Logout").click();
		//login with patient
		wc = login("2", "pw");	
		wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		wr = wr.getLinkWith("Find an Expert").click();
		WebForm findExpertForm = wr.getForms()[0];
		findExpertForm.getScriptableObject().setParameterValue("specialty", "Surgeon");
		findExpertForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Kellyy"));
	}
	
	

	public void testInteractAndBack() throws Exception{
		WebConversation wc = login("9000000001", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		wr = wr.getLinkWith("Edit Personnel").click();
		WebForm personnelForm = wr.getForms()[1];
		personnelForm.getScriptableObject().setParameterValue("FIRST_NAME", "Cool");
		personnelForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Found 1 Records"));
		
		//get the first personnel and click
		WebForm firstPersonnel = wr.getForms()[2];
		firstPersonnel.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/staff/editPersonnel.jsp", wr.getURL().toString());
		
		wr = wr.getLinkWith("Edit Personnel").click();
		assertTrue(wr.getText().contains("Please Select a Personnel"));
		

	}
	
	
	
	
}
