package unitea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import unitea.model.AlunoModel;
import unitea.model.FamiliarModel;
import unitea.model.UsuarioModel;

public class FamiliarDao {
	
	public void cadastrarFamiliar(UsuarioModel usuario, FamiliarModel familiar, AlunoModel aluno) {
	    UsuarioDao user = new UsuarioDao();
	    int idUsuario = user.cadastrarUsuario(usuario); 
	    System.out.println("ID do Usuário inserido: " + idUsuario);

	    String sqlFamiliar = "INSERT INTO UNITEA.familiar (id_usuario) VALUES (?)";
	    String sqlAluno = "INSERT INTO UNITEA.aluno (nome, id_familiar, turma, diagnosticoTEA) VALUES (?, ?, ?, ?)";
	    String sqlUpdateFamiliarAlunos = "UPDATE UNITEA.familiar SET alunos = ? WHERE id_familiar = ?";

	    Connection conn = null;
	    PreparedStatement psFa = null;
	    PreparedStatement psAl = null;
	    PreparedStatement psUpdateFa = null;
	    ResultSet generatedKeysFamiliar = null;
	    ResultSet generatedKeysAluno = null;

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

	        // 2. Inserir o Familiar
	        psFa = conn.prepareStatement(sqlFamiliar, Statement.RETURN_GENERATED_KEYS);
	        psFa.setInt(1, idUsuario);  

	        int rowsAffectedFa = psFa.executeUpdate();
	        System.out.println("Familiar inserido. Linhas afetadas: " + rowsAffectedFa);

	        if (rowsAffectedFa > 0) {
	            generatedKeysFamiliar = psFa.getGeneratedKeys();
	            if (generatedKeysFamiliar.next()) {
	                int idFamiliar = generatedKeysFamiliar.getInt(1);
	                familiar.setIdFamiliar(idFamiliar);
	                System.out.println("ID do Familiar: " + idFamiliar);

	                // 3. Inserir o Aluno
	                psAl = conn.prepareStatement(sqlAluno, Statement.RETURN_GENERATED_KEYS);
	                psAl.setString(1, aluno.getNome());
	                psAl.setInt(2, idFamiliar);
	                psAl.setString(3, aluno.getTurma());
	                psAl.setString(4, aluno.getDiagnosticoTEA());

	                int rowsAffectedAl = psAl.executeUpdate();
	                System.out.println("Aluno inserido. Linhas afetadas: " + rowsAffectedAl);

	                if (rowsAffectedAl > 0) {
	                    generatedKeysAluno = psAl.getGeneratedKeys();
	                    if (generatedKeysAluno.next()) {
	                        int idAluno = generatedKeysAluno.getInt(1);  
	                        aluno.setIdAluno(idAluno);
	                        System.out.println("ID do Aluno: " + idAluno);

	                        // 4. Atualizar a coluna 'alunos' na tabela 'familiar'
	                        psUpdateFa = conn.prepareStatement(sqlUpdateFamiliarAlunos);
	                        psUpdateFa.setInt(1, idAluno);
	                        psUpdateFa.setInt(2, idFamiliar);

	                        int rowsUpdatedFa = psUpdateFa.executeUpdate();
	                        System.out.println("Familiar atualizado com o ID do aluno. Linhas afetadas: " + rowsUpdatedFa);

	                        conn.commit();
	                        System.out.println("Transação bem-sucedida. Commit realizado.");
	                    } else {
	                        System.out.println("Erro ao inserir o aluno. Fazendo rollback.");
	                        conn.rollback();  
	                    }
	                } else {
	                    System.out.println("Erro ao inserir o aluno. Fazendo rollback.");
	                    conn.rollback();
	                }
	            } else {
	                System.out.println("Erro ao obter o ID do familiar. Fazendo rollback.");
	                conn.rollback();
	            }
	        } else {
	            System.out.println("Erro ao inserir o familiar. Fazendo rollback.");
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
	            if (generatedKeysFamiliar != null) generatedKeysFamiliar.close();
	            if (generatedKeysAluno != null) generatedKeysAluno.close();
	            if (psFa != null) psFa.close();
	            if (psAl != null) psAl.close();
	            if (psUpdateFa != null) psUpdateFa.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	}



}
