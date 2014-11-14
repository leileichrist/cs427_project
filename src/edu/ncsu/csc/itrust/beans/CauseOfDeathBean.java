package edu.ncsu.csc.itrust.beans;


/**
 * A bean for storing data about diagnosis counts
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 * 
 *  
 */
public class CauseOfDeathBean {
	
	private long num_death;
	private String name;
	private String icdcode;
	/**
	 * Constructor for an empty bean 
	 */
	public CauseOfDeathBean() {
		this.num_death = 0l;
		name = null;
		icdcode = null;
	}

	public CauseOfDeathBean(long num_death, String name, String icdcode) {
		this.num_death = num_death;
		this.name = name;
		this.icdcode = icdcode;
	}

	/**
	 * get number of death by this disease
	 * @return num_death
	 */
	public long getQuantityOfDeath() {
		return num_death;
	}

	/**
	 * set number of death by this disease
	 * @return 
	 */
	public void setQuantityOfDeath(long num_death) {
		this.num_death = num_death;
	}
	
	
	/**
	 * get name of this disease
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * set name of this disease
	 * @return 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * get icdcode of this disease
	 * @return icdcode
	 */
	public String getIcdcode() {
		return icdcode;
	}
	
	/**
	 * set icdcode of this disease
	 * @return 
	 */
	public void setIcdcode(String icdcode) {
		this.icdcode = icdcode;
	}
	
	
}
