package unitea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import unitea.model.UsuarioModel;

public class UsuarioDao {

    public int cadastrarUsuario(UsuarioModel usuario) {
        String sql = "INSERT INTO UNITEA.usuario (nome, email, senha, endereco, perfil) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pStatement = null;
        Connection conn = null;
        int generatedId = -1;

        try {
            conn = new MysqlConnection().getConnection();
            
            if (conn != null) {
                
                pStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                
                
                pStatement.setString(1, usuario.getNome());
                pStatement.setString(2, usuario.getEmail());
                pStatement.setString(3, usuario.getSenha());
                pStatement.setString(4, usuario.getEndereco());
                pStatement.setString(5, usuario.getPerfil());
                
                
                pStatement.executeUpdate();
                
                
                ResultSet rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);  
                }
                rs.close(); 

            } else {
                System.out.println("Falha na conexão com o banco de dados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pStatement != null) {
                    pStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return generatedId;  
    }
}

