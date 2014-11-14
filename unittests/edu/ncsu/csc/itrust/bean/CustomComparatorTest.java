package edu.ncsu.csc.itrust.bean;

import edu.ncsu.csc.itrust.beans.PersonnelBean;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.CustomComparator;

public class CustomComparatorTest  extends TestCase {	
	public void testCompare() throws Exception {
		PersonnelBean p1 = new PersonnelBean();
		PersonnelBean p2 = new PersonnelBean();
		
		p1.setMID(000001);
		p2.setMID(000002);
		
		CustomComparator c = new CustomComparator();
		int diff = c.compare(p1, p2);
		assertEquals(diff, (int)(p1.getMID()-p2.getMID()));
	}
}
