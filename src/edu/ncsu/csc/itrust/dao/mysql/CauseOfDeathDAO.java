package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.CauseOfDeathBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class CauseOfDeathDAO {
	private DAOFactory factory;

	/**
	 * @param factory
	 */
	public CauseOfDeathDAO(DAOFactory factory) {
		this.factory = factory;
	}

	public List<CauseOfDeathBean> getTopTwoCausesOfDeath(Long HCP,
			String startYear, String endYear, String gender) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<CauseOfDeathBean> cdBeans = new ArrayList<CauseOfDeathBean>();
		// Date startDate = Date.valueOf(startYear);
		// Date endDate = Date.valueOf(endYear);
		try {
			conn = factory.getConnection();
			if (HCP != null) {
				if (gender.equals("All")) {
					ps = conn
							.prepareStatement("SELECT CauseOfDeath,icdcodes.Description, COUNT(DISTINCT patients.MID) AS count FROM patients INNER JOIN officevisits INNER JOIN icdcodes WHERE officevisits.HCPID=? AND patients.MID=officevisits.PatientID AND patients.CauseOfDeath!=\"\"  AND patients.DateOfDeath>=? AND patients.DateOfDeath<=? AND icdcodes.Code = patients.CauseOfDeath GROUP BY CauseOfDeath ORDER BY count DESC");
					ps.setString(1, HCP.toString());
					ps.setString(2, startYear);
					ps.setString(3, endYear);
				} else {
					ps = conn
							.prepareStatement("SELECT CauseOfDeath, icdcodes.Description,COUNT(DISTINCT patients.MID) AS count FROM patients INNER JOIN officevisits INNER JOIN icdcodes WHERE officevisits.HCPID=? AND patients.MID=officevisits.PatientID AND patients.Gender=? AND patients.CauseOfDeath!=\"\"  AND patients.DateOfDeath>=? AND patients.DateOfDeath<=? AND icdcodes.Code = patients.CauseOfDeath GROUP BY CauseOfDeath ORDER BY count DESC");
					ps.setString(1, HCP.toString());
					ps.setString(2, gender);
					ps.setString(3, startYear);
					ps.setString(4, endYear);
				}

			} else {
				if (gender.equals("All")) {
					ps = conn
							.prepareStatement("SELECT CauseOfDeath, icdcodes.Description,COUNT(DISTINCT patients.MID) AS count FROM patients INNER JOIN officevisits INNER JOIN icdcodes  WHERE patients.MID=officevisits.PatientID AND patients.CauseOfDeath!=\"\"  AND patients.DateOfDeath>=? AND patients.DateOfDeath<=? AND icdcodes.Code = patients.CauseOfDeath GROUP BY CauseOfDeath ORDER BY count DESC");
					ps.setString(1, startYear);
					ps.setString(2, endYear);
				} else {
					ps = conn
							.prepareStatement("SELECT CauseOfDeath, icdcodes.Description, COUNT(DISTINCT patients.MID) AS count FROM patients INNER JOIN officevisits INNER JOIN icdcodes  WHERE patients.MID=officevisits.PatientID AND patients.Gender=? AND patients.CauseOfDeath!=\"\"  AND patients.DateOfDeath>=? AND patients.DateOfDeath<=? AND icdcodes.Code = patients.CauseOfDeath GROUP BY CauseOfDeath ORDER BY count DESC");
					ps.setString(1, gender);
					ps.setString(2, startYear);
					ps.setString(3, endYear);
				}

			}
			ResultSet rs = ps.executeQuery();
			int i=0;
			while(rs.next()){
				i++;
				CauseOfDeathBean item = new CauseOfDeathBean(rs.getLong(3), rs.getString(2), rs.getString(1));					
				cdBeans.add(item);
				if(i>=2){
					break;
				}
			}
			return cdBeans;
		} catch (SQLException e) {

			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}

}
