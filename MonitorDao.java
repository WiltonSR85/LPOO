package unitea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import unitea.model.MonitorModel;
import unitea.model.UsuarioModel;

public class MonitorDao {

	public void cadastrarMonitor(UsuarioModel usuario, MonitorModel monitor) {
	    UsuarioDao user = new UsuarioDao();
	    int idUsuario = user.cadastrarUsuario(usuario); 
	    System.out.println("ID do Usuário inserido: " + idUsuario);

	    String sql = "INSERT INTO UNITEA.monitor (id_usuario, especializacao, anosExperiencia, formacaoAcademica, disciplinas)"+
	    "VALUES (?,?,?,?,?)";

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet generatedKeysMonitor = null;

	    try {
	        // 1. Conectar ao banco de dados
	        conn = new MysqlConnection().getConnection();
	        if (conn != null) {
	            System.out.println("Conexão com o banco de dados bem-sucedida.");
	        } else {
	            System.out.println("Falha na conexão com o banco de dados.");
	            return; 
	        }

	        conn.setAutoCommit(false);  

	        // 2. Inserir o monitor
	        ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, idUsuario);
	        ps.setString(2, monitor.getEspecializacao());  
	        ps.setInt(3, monitor.getAnosExperiencia());
	        ps.setString(4, monitor.getFormacaoAcademica());
	        ps.setString(5, monitor.getDisciplinasAsJson());

	        int rowsAffectedFa = ps.executeUpdate();
	        System.out.println("Monitor inserido. Linhas afetadas: " + rowsAffectedFa);

	        if (rowsAffectedFa > 0) {
	            generatedKeysMonitor = ps.getGeneratedKeys();
	            if (generatedKeysMonitor.next()) {
	                int idMonitor = generatedKeysMonitor.getInt(1);
	                monitor.setIdMonitor(idMonitor);
	                System.out.println("ID do monitor: " + idMonitor);
	                
	                conn.commit();
	                System.out.println("Transação bem-sucedida. Commit realizado.");
	            } else {
	                System.out.println("Erro ao obter o ID do monitor. Fazendo rollback.");
	                conn.rollback();
	            }
	        } else {
	            System.out.println("Erro ao inserir o monitor. Fazendo rollback.");
	            conn.rollback();
	        }

	    } catch (SQLException e) {
	        try {
	            if (conn != null) conn.rollback(); 
	        } catch (SQLException se) {
	            se.printStackTrace();
	        }
	        e.printStackTrace(); 
	    } finally {
	        try {
	            if (generatedKeysMonitor != null) generatedKeysMonitor.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	}
}
