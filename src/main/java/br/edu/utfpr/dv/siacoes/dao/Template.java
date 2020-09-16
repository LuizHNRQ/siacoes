package br.edu.utfpr.dv.siacoes.dao;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Template<T> {

    public final void closeConnection( PreparedStatement stmt, ResultSet rs, Connection conn){
        if((stmt != null) && !stmt.isClosed())
            stmt.close();
        if((rs != null) && !rs.isClosed())
            rs.close();
        if((conn != null) && !conn.isClosed())
            conn.close();
    }

    protected abstract String getStringSQLFindId();

    public T findById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(getStringSQLFindId());

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return this.loadObject(rs);
            } else {
                return null;
            }
        } finally {
            closeConnection(stmt, rs, conn);
        }
    }

    protected abstract String getStringFirstLocal();

    protected abstract String getStringSecondLocal();

    protected abstract Class getIdClasse();

    protected abstract void definirSaveLocal(PreparedStatement stmt, T classe);

    public void save(T classe, int idUser) {
        boolean insert = (this.T.getIdClasse() == 0);
        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();

            if(insert){
                stmt = conn.prepareStatement(getStringFirstLocal());
            }else{
                stmt = conn.prepareStatement(getStringSecondLocal());
            }

            definirSaveLocal(stmt, classe);

            stmt.execute();

            if(insert){
                rs = stmt.getGeneratedKeys();

                if(rs.next()){
                    department.setIdDepartment(rs.getInt(1));
                }

                new UpdateEvent(conn).registerInsert(idUser, department);
            }else {
				new UpdateEvent(conn).registerUpdate(idUser, department);
			}
			
			return T.getIdClasse();
		}finally{
			closeConnection(stmt, rs, conn);
		}
    }

}
