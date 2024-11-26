package unitea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import unitea.model.AlunoModel;
import unitea.model.FamiliarModel;
import unitea.model.UsuarioModel;

public class ExcluirPerfilDao {

	public void excluirPerfil(HttpServletRequest request) {
		HttpSession session = request.getSession();
        int idUsuario = (Integer) session.getAttribute("id_usuario");
        //int idFamiliar = (Integer) session.getAttribute("id_familiar");
		
        String query = "DELETE FROM usuario WHERE id_usuario = ?";
		PreparedStatement Stat= null;
		Connection conn= null;
		
		try {
			conn= new MysqlConnection().getConnection();
			
			if(conn!= null) {
				Stat= conn.prepareStatement(query);
				
				Stat.setInt(1, idUsuario);
				
				Stat.executeUpdate();
				
				System.out.println("Exclusões realizadas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if (Stat != null) {
                    Stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		
	}
}
