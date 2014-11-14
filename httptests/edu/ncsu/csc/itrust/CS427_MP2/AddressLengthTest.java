package edu.ncsu.csc.itrust.CS427_MP2;


import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;


public class AddressLengthTest extends iTrustHTTPTest  {
		
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testAddAddress() throws Exception{
		WebConversation wc = login("9000000001", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		wr = wr.getLinkWith("Edit Personnel").click();
		WebForm patientForm = wr.getForms()[1];
		patientForm.setParameter("FIRST_NAME", "Kelly");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();	
		patientForm = wr.getForms()[2];
		patientForm.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/staff/editPersonnel.jsp", wr.getURL().toString());

		WebForm editPersonnelForm = wr.getForms()[0];
		editPersonnelForm.setParameter("streetAddress1", "98762222wwwsssssssssssssssssssssssssssssssss5 Oak Hills Drive");
		editPersonnelForm.setParameter("city", "Capitol City");
		editPersonnelForm.setParameter("state", "NC");
		editPersonnelForm.setParameter("phone", "555-877-5100");
		editPersonnelForm.setParameter("zip", "28700-0458");
		
		editPersonnelForm.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information not valid"));
		
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
	}

//	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
