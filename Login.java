package unitea.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import unitea.dao.MysqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class Login
 */
//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// Captura os dados de login
        String email = request.getParameter("Email");
        String senha = request.getParameter("Senha");

        // Variáveis para armazenar os dados do usuário
        int idUsuario= 0;
        String nome = null;
        String email1= null;
        String senha1= null;
        String endereco= null;
        String perfil = null;
        int idFamiliar= 0;
        int idAluno= 0;
        String nomeAluno= null;
        String turma= null;
        String diagnostico= null;
        int idMentor=0;
        String especializacao= null;
        String anosExperiencia=null;
        String formacaoAcademica=null;
        Connection conn = null;

        // Conexão com o banco de dados e validação do login
        try {
            // Conexão com o banco de dados
        	conn = new MysqlConnection().getConnection();
            String query = "SELECT id_usuario, nome, email, senha, endereco, perfil FROM usuario WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            // Verifica se o usuário foi encontrado
            if (rs.next()) {
            	idUsuario= rs.getInt("id_usuario");
                nome = rs.getString("nome");
                email1= rs.getString("email");
                senha1= rs.getString("senha");
                endereco= rs.getString("endereco");
                perfil = rs.getString("perfil");

                // Armazena os dados do usuário na sessão
                HttpSession session = request.getSession();
                session.setAttribute("id_usuario", idUsuario);
                session.setAttribute("nome", nome);
                session.setAttribute("email", email1);
                session.setAttribute("senha", senha1);
                session.setAttribute("endereco", endereco);
                session.setAttribute("perfil", perfil);

                // Redireciona para a tela inicial personalizada
                if ("familiar".equalsIgnoreCase(perfil)) {
                    String query1 = "SELECT id_familiar, alunos FROM familiar WHERE id_usuario = ?";
                    PreparedStatement stmt1 = conn.prepareStatement(query1);
                    stmt1.setInt(1, idUsuario); // Passa o ID do usuário como parâmetro
                    ResultSet rs1 = stmt1.executeQuery();

                    if (rs1.next()) {
                        idFamiliar = rs1.getInt("id_familiar");
                        String alunosJson = rs1.getString("alunos"); // Recupera o JSON bruto da coluna alunos

                        // Extrai o ID do aluno do JSON e armazena como int
                        if (alunosJson != null && !alunosJson.isEmpty()) {
                            try {
                                // Converte a string JSON para um único valor e armazena como int
                                idAluno = Integer.parseInt(new org.json.JSONArray(alunosJson).getString(0));
                            } catch (Exception e) {
                                e.printStackTrace(); // Trate possíveis erros (ex.: JSON inválido ou número inválido)
                            }
                        }
                    }
                    String query2 = "SELECT nome, diagnosticoTEA, turma FROM aluno WHERE id_familiar = ?";
                    PreparedStatement stmt2 = conn.prepareStatement(query2);
                    stmt2.setInt(1, idFamiliar); 
                    ResultSet rs2 = stmt2.executeQuery();
                    
                    if (rs2.next()) {
                        nomeAluno = rs2.getString("nome");
                        turma= rs2.getString("turma");
                        diagnostico= rs2.getString("diagnosticoTEA");
                    }
                    
                    session.setAttribute("id_familiar", idFamiliar);
                    session.setAttribute("id_aluno", idAluno);
                    session.setAttribute("nomeAluno", nomeAluno);
                    session.setAttribute("turma", turma);
                    session.setAttribute("diagnostico", diagnostico);

                    response.sendRedirect("telaFamiliar.jsp"); 
                 // Redireciona para a tela de familiar
                } else if ("mentor".equalsIgnoreCase(perfil)) {
                    String query2 = "SELECT id_monitor, especializacao, anosExperiencia, formacaoAcademica, disciplinas FROM monitor WHERE id_usuario = ?";
                    PreparedStatement stmt2 = conn.prepareStatement(query2);
                    stmt2.setInt(1, idUsuario); 
                    ResultSet rs2 = stmt2.executeQuery();
                    
                    if (rs2.next()) {
                        idMentor = rs2.getInt("id_monitor");
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

                            } catch (Exception e) {
                                e.printStackTrace(); // Trate possíveis erros no JSON
                            }
                        }
                    }
                    
                    session.setAttribute("id_mentor", idMentor);
                    session.setAttribute("especializacao", especializacao);
                    session.setAttribute("anosExperiencia", anosExperiencia);
                    session.setAttribute("formacaoAcademica", formacaoAcademica);
                    
                    response.sendRedirect("telaMentor.jsp"); // Redireciona para a tela do mentor
                }


            } else {
                // Se o login falhar
                request.setAttribute("mensagem", "Email ou senha incorretos.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao acessar o banco de dados.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

}
