package br.edu.utfpr.dv.siacoes.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.ActivityUnit;
import br.edu.utfpr.dv.siacoes.model.Department;

public class ActivityUnitDAO extends Template{

	public void closeConnection() {
		closeConnection(PreparedStatement stmt, ResultSet rs, Connection conn);
	}

	@Override
	protected String getStringSQLFindById() {
		return "SELECT * FROM activityunit WHERE idActivityUnit=?";
	}
	@Override
	public Object findById(int id) throws SQLException {
		super.findById(id);
	}

	public List<ActivityUnit> listAll() throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery("SELECT * FROM activityunit ORDER BY description");
			
			List<ActivityUnit> list = new ArrayList<ActivityUnit>();
			
			while(rs.next()){
				list.add(this.loadObject(rs));
			}
			
			return list;
		}finally{
			closeConnection(stmt, rs, conn);
		}
	}

	@Override
	protected String getStringFirstLocal() {
		return "INSERT INTO activityunit(description, fillAmount, amountDescription) VALUES(?, ?, ?)";
	}

	@Override
	protected String getStringSecondLocal() {
		return "UPDATE activityunit SET description=?, fillAmount=?, amountDescription=? WHERE idActivityUnit=?";
	}

	@Override
	protected Class getIdClasse() {
		return ActivityUnit.getIdActivityUnit() ;
	}

	@Override
	protected void definirSaveLocal(PreparedStatement stmt, Department department)throws SQLException {
		stmt.setString(1, unit.getDescription());
		stmt.setInt(2, (unit.isFillAmount() ? 1 : 0));
		stmt.setString(3, unit.getAmountDescription());

		if(!insert){
			stmt.setInt(4, unit.getIdActivityUnit());
		}

	}


	
	private ActivityUnit loadObject(ResultSet rs) throws SQLException{
		ActivityUnit unit = new ActivityUnit();
		
		unit.setIdActivityUnit(rs.getInt("idActivityUnit"));
		unit.setDescription(rs.getString("Description"));
		unit.setFillAmount(rs.getInt("fillAmount") == 1);
		unit.setAmountDescription(rs.getString("amountDescription"));
		
		return unit;
	}

}
