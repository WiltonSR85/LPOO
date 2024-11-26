package unitea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import unitea.model.AlunoModel;
import unitea.model.FamiliarModel;
import unitea.model.MonitorModel;
import unitea.model.UsuarioModel;

public class EditarPerfilDao {

	public void editarPerfilFamiliar(HttpServletRequest request, UsuarioModel usuario, FamiliarModel familiar, AlunoModel aluno) {
		HttpSession session = request.getSession();
        int idUsuario = (Integer) session.getAttribute("id_usuario");
        int idFamiliar = (Integer) session.getAttribute("id_familiar");
		
        String query = "UPDATE usuario SET nome = ?,email= ?, endereco = ? WHERE id_usuario = ?";
		String queryFamiliar = "UPDATE aluno SET nome = ?, turma = ?, diagnosticoTEA = ? WHERE id_familiar = ?";
		PreparedStatement Stat= null;
		PreparedStatement Stat1= null;
		Connection conn= null;
		
		try {
			conn= new MysqlConnection().getConnection();
			
			if(conn!= null) {
				Stat= conn.prepareStatement(query);
				
				Stat.setString(1, usuario.getNome());
				Stat.setString(2, usuario.getEmail());
				Stat.setString(3, usuario.getEndereco());
				Stat.setInt(4, idUsuario);
				
				Stat.executeUpdate();
				
				Stat1= conn.prepareStatement(queryFamiliar);
				
				Stat1.setString(1, aluno.getNome());
				Stat1.setString(2, aluno.getTurma());
				Stat1.setString(3, aluno.getDiagnosticoTEA());
				Stat1.setInt(4, idFamiliar);
				
				Stat1.executeUpdate();
				
				System.out.println("Atualizações realizadas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if (Stat != null) {
                    Stat.close();
                }
                if (Stat1 != null) {
                    Stat1.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		
	}
	
	public void editarPerfilMonitor(HttpServletRequest request, UsuarioModel usuario, MonitorModel monitor) {
		HttpSession session = request.getSession();
		int idUsuario = (Integer) session.getAttribute("id_usuario");
        int idMentor = (Integer) session.getAttribute("id_mentor");
        
        String query = "UPDATE usuario SET nome = ?,email= ?, endereco = ? WHERE id_usuario = ?";
        String queryMonitor = "UPDATE monitor SET especializacao = ?, anosExperiencia = ?, formacaoAcademica = ?, disciplinas= ? WHERE id_monitor = ?";
        PreparedStatement Stat= null;
		PreparedStatement Stat1= null;
		Connection conn= null;
		
		try {
			conn= new MysqlConnection().getConnection();
			
			if(conn!= null) {
				Stat= conn.prepareStatement(query);
				
				Stat.setString(1, usuario.getNome());
				Stat.setString(2, usuario.getEmail());
				Stat.setString(3, usuario.getEndereco());
				Stat.setInt(4, idUsuario);
				
				Stat.executeUpdate();
				
				Stat1= conn.prepareStatement(queryMonitor);
				
				Stat1.setString(1, monitor.getEspecializacao());
				Stat1.setInt(2, monitor.getAnosExperiencia());
				Stat1.setString(3, monitor.getFormacaoAcademica());
				Stat1.setString(4, monitor.getDisciplinasAsJson());
				Stat1.setInt(5, idMentor);
				
				System.out.println(monitor.getAnosExperiencia());
				System.out.println(monitor.getEspecializacao());
				System.out.println(monitor.getFormacaoAcademica());
				monitor.exibirDisciplinas();
				
				
				Stat1.executeUpdate();
				
				System.out.println("Atualizações realizadas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if (Stat != null) {
                    Stat.close();
                }
                if (Stat1 != null) {
                    Stat1.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
	}
	
	public void atualizarDadosFamiliar(HttpServletRequest request) {
		// Captura os dados de login
		HttpSession session = request.getSession();
        int idUsuario = (Integer) session.getAttribute("id_usuario");
        int idFamiliar = (Integer) session.getAttribute("id_familiar");
        //int idAluno = (Integer) session.getAttribute("id_aluno");
        
        System.out.println(idUsuario);
        System.out.println(idUsuario);

        // Variáveis para armazenar os dados do usuário
        String nome = null;
        String email1= null;
        String senha1= null;
        String endereco= null;
        String perfil = null;
        String nomeAluno= null;
        String turma= null;
        String diagnostico= null;
        Connection conn = null;

        // Conexão com o banco de dados e validação do login
        try {
            // Conexão com o banco de dados
        	conn = new MysqlConnection().getConnection();
            String query = "SELECT id_usuario, nome, email, senha, endereco, perfil FROM usuario WHERE id_usuario= ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idUsuario);
            System.out.println(idUsuario);

            ResultSet rs = stmt.executeQuery();

            // Verifica se o usuário foi encontrado
            if (rs.next()) {
                nome = rs.getString("nome");
                email1= rs.getString("email");
                senha1= rs.getString("senha");
                endereco= rs.getString("endereco");
                perfil = rs.getString("perfil");

                session.setAttribute("id_usuario", idUsuario);
                session.setAttribute("nome", nome);
                session.setAttribute("email", email1);
                session.setAttribute("senha", senha1);
                session.setAttribute("endereco", endereco);
                session.setAttribute("perfil", perfil);

               
                    String query2 = "SELECT nome, diagnosticoTEA, turma FROM aluno WHERE id_familiar = ?";
                    PreparedStatement stmt2 = conn.prepareStatement(query2);
                    stmt2.setInt(1, idFamiliar); 
                    ResultSet rs2 = stmt2.executeQuery();
                    
                    if (rs2.next()) {
                        nomeAluno = rs2.getString("nome");
                        turma= rs2.getString("turma");
                        diagnostico= rs2.getString("diagnosticoTEA");
                    }
                    
                    session.setAttribute("nomeAluno", nomeAluno);
                    session.setAttribute("turma", turma);
                    session.setAttribute("diagnostico", diagnostico);
 

            	}
        }catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void atualizarMentor(HttpServletRequest request) {
		
		// Captura os dados de login
				HttpSession session = request.getSession();
		        int idUsuario = (Integer) session.getAttribute("id_usuario");
		        int idMentor = (Integer) session.getAttribute("id_mentor");
		        
		        System.out.println(idUsuario);
		        System.out.println(idUsuario);
		        System.out.println(idMentor);

		        // Variáveis para armazenar os dados do usuário
		        String nome = null;
		        String email1= null;
		        String senha1= null;
		        String endereco= null;
		        String perfil = null;
		        String especializacao= null;
		        String anosExperiencia=null;
		        String formacaoAcademica=null;
		        Connection conn = null;
	
		        try {
		        	
		        	conn = new MysqlConnection().getConnection();
		            String query = "SELECT id_usuario, nome, email, senha, endereco, perfil FROM usuario WHERE id_usuario= ?";
		            PreparedStatement stmt = conn.prepareStatement(query);
		            stmt.setInt(1, idUsuario);
		            System.out.println(idUsuario);

		            ResultSet rs = stmt.executeQuery();

		            // Verifica se o usuário foi encontrado
		            if (rs.next()) {
		                nome = rs.getString("nome");
		                email1= rs.getString("email");
		                senha1= rs.getString("senha");
		                endereco= rs.getString("endereco");
		                perfil = rs.getString("perfil");

		                session.setAttribute("id_usuario", idUsuario);
		                session.setAttribute("nome", nome);
		                session.setAttribute("email", email1);
		                session.setAttribute("senha", senha1);
		                session.setAttribute("endereco", endereco);
		                session.setAttribute("perfil", perfil);
		             }
		                
		        	String query2 = "SELECT especializacao, anosExperiencia, formacaoAcademica, disciplinas FROM monitor WHERE id_usuario = ?";
		            PreparedStatement stmt2 = conn.prepareStatement(query2);
		            stmt2.setInt(1, idUsuario); 
		            
		            System.out.println("estou aqui "+ idUsuario);
		            ResultSet rs2 = stmt2.executeQuery();
		            
		            if (rs2.next()) {
		                especializacao = rs2.getString("especializacao");
		                anosExperiencia = rs2.getString("anosExperiencia");
		                formacaoAcademica = rs2.getString("formacaoAcademica");

		                // Recupera o JSON das disciplinas
		                String disciplinasJson = rs2.getString("disciplinas");
		                if (disciplinasJson != null && !disciplinasJson.isEmpty()) {
		                    try {
		                        // Converte o JSON para uma lista de strings (disciplinas)
		                        org.json.JSONArray jsonArray = new org.json.JSONArray(disciplinasJson);
		                        List<String> disciplinas = new ArrayList<>();

		                        for (int i = 0; i < jsonArray.length(); i++) {
		                            disciplinas.add(jsonArray.getString(i)); // Adiciona cada disciplina à lista
		                        }
		                        session.setAttribute("disciplinas", disciplinas);
		                        
		                        int quantidadeDisciplinas = disciplinas.size();
		                        System.out.println("Quantidade de disciplinas cadastradas: " + quantidadeDisciplinas);

		                    } catch (Exception e) {
		                        e.printStackTrace(); // Trate possíveis erros no JSON
		                    }
		                }
		            }
		            
		            session.setAttribute("especializacao", especializacao);
		            session.setAttribute("anosExperiencia", anosExperiencia);
		            session.setAttribute("formacaoAcademica", formacaoAcademica);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
}


