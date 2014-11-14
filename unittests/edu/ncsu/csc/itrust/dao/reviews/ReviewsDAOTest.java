package edu.ncsu.csc.itrust.dao.reviews;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ReviewsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ReviewsDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ReviewsDAOTest {
	/** obj to mock and generate TestDataGenerator */
    private TestDataGenerator gen = new TestDataGenerator();
    /** ReviewsDAO instance for testing */
    private ReviewsDAO rdao;
    /** test instance of beans for testing */
    private ReviewsBean beanValid, beanInvalid;
    private static final long PID1 = 9000000000L, PID2 = 9000000003L, MID = 106L;
	private static final Date REVDATE = new java.sql.Date(new Date().getTime());
	
	/**
	 * Provide setup for the rest of the tests; initialize all globals.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc61reviews();
		DAOFactory factory = TestDAOFactory.getTestInstance();
		rdao = new ReviewsDAO(factory);
		
		beanValid = new ReviewsBean();
		beanValid.setMID(MID);
		beanValid.setPID(PID1);
		beanValid.setDescriptiveReview("oh well");
		beanValid.setDateOfReview(REVDATE);
		beanValid.setRating(2);
		
		beanInvalid = new ReviewsBean();
		beanInvalid.setMID(MID);
		beanInvalid.setPID(PID2);
		beanInvalid.setDateOfReview(REVDATE);
		beanInvalid.setDescriptiveReview("oh well");
		beanInvalid.setRating(2);
	}
	

	@After
	public void tearDown() throws Exception {
	
	}

	/**
	 * Tests adding a review to the reviewsTable by comparing sizes of 
	 * lists.
	 * Pre-condition: assuming the ReviewsDAO.getAllReviews() works AND
	 * setup is run between ea test.
	 */
	@Test
	public final void testAddReviewValid() {
	    try {
	    	//sanity check for the initial size of the entries in the reviews table
	    	List<ReviewsBean> l = rdao.getAllReviews();
	    	assertEquals(6, l.size());
	    	//try adding a valid bean
	    	assertTrue(rdao.addReview(beanValid));
			//check the number of reviews table entries went up by 1
			l = rdao.getAllReviews();
			assertEquals(7, l.size());
		} catch (DBException e) {
			fail();
		}
	    
	}
	
	/**
	 * Tests getting reviews from a current database for a 
	 * given HCP.
	 */
	@Test
	public final void testGetReviews() {
		List<ReviewsBean> l;
		try {
			//test getting reviews for Kelly Doctor
			l = rdao.getReviews(PID1);
			assertEquals(4, l.size());
			
			//test getting reviews for Gandolf Stormcloud
			l=rdao.getReviews(PID2);
			assertEquals(2, l.size());
		} catch (DBException e) {
			fail();
		}
	}
	
	/**
	 * Tests that ALL in table reviews are retrieved when called.
	 */
	public final void testGetAllReviews(){
		try {
			setUp();
			List<ReviewsBean> l = rdao.getAllReviews();
			assertEquals(5, l.size());
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests that overall rating averages are returned for ea
	 * HCP.
	 */
	@Test
	public final void testGetTotalAverageRating() {
	    /** expected average rating for Kelly Doctor */
		final double PID1AVG = 2.5;
		/** expected average rating for Gandolf Stomcloud */
		final double PID2AVG = 4.5;
	    try {
			assertTrue(PID1AVG == rdao.getTotalAverageRating(PID1));
			assertTrue(PID2AVG == rdao.getTotalAverageRating(PID2));
		} catch (DBException e) {
			fail();
		}
	}

	/**
	 * Tests that a Patient can ONLY review a Physician they have previously seen.
	 */
	@Test
	public final void testIsRateable() {
		try {
			//Tests when it should not work, 
			//for Patient Tom Nook and HCP Gandolf Stormcloud
			assertFalse(rdao.isRateable(MID, PID2));
			//Tests when it should be added, 
			//for Patient Tom Nook and HCP Kelly Doctor
			assertTrue(rdao.isRateable(MID, PID1));
		} catch (DBException e) {
			fail();
		}
	}

	/**
	 * Tests adding aN INVALID review to the reviewsTable by comparing sizes of 
	 * lists.
	 * Pre-condition: assuming the ReviewsDAO.getAllReviews() works AND
	 * setup is run between ea test.
	 */
	@Test(expected = DBException.class)  
	public final void testAddReviewInValid() throws DBException{
	   	//try adding a invalid bean
	   	assertFalse(rdao.addReview(null));
	   	ReviewsBean r = new ReviewsBean();	    
	   	r.setTitle("Please put test cases that you write for Part 2 in the original test classes for those listed four classes in Part 2. Don't change the names of these original test classes for the listed four classes. Don't move these original test classes to a new location. If there is no original test class for one of the listed four classes in Part 2, please create a new test class with meaningful naming convention to allow others to trace from the class under test to the test class; please place the new test class in the same package as other test classes for those other classes of the same package as the one for the class under test.The four listed classes may have some lines of statement being covered by other unit tests not written for these four listed classes. As long as the coverage of each of the four classes reaches 80% with running all the unit tests, you are good in terms of grading. Of course, if running the unit test classes (including your newly added unit test cases) written for these four classes can allow each of the four classes to reach 80%, you know running all the unit tests can also allow each of the four classes to reach 80%. In the process of covering a line of statement that hasn't been covered yet, you can rerun only the test class that you freshly modify and then see whether the target line of statement is covered or not. In this way, your iterative process (of adding new test cases and checking newly achieved statement coverage) will be more  " );
	   	rdao.addReview(r);
	}
	
}
