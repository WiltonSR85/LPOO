package unitea.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import unitea.dao.EditarPerfilDao;
import unitea.model.AlunoModel;
import unitea.model.FamiliarModel;
import unitea.model.UsuarioModel;

import java.io.IOException;

/**
 * Servlet implementation class EditarPerfilFamiliar
 */
//@WebServlet("/EditarPerfilFamiliar")
public class EditarPerfilFamiliar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarPerfilFamiliar() {
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
		
		
		String nome= request.getParameter("nome");
		String email= request.getParameter("email");
		String endereco= request.getParameter("endereco");
		String nomeAluno= request.getParameter("nomeDoAluno");
		String turma= request.getParameter("turma");
		String diagnostico= request.getParameter("diagnostico");
		String mensagem;
		EditarPerfilDao editarDao= new EditarPerfilDao();
		
		System.out.println(nome);
		System.out.println(email);
		System.out.println(endereco);
		System.out.println(turma);
		System.out.println(diagnostico);
		System.out.println(nomeAluno);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("perfilFamiliar.jsp");
		
		if(nome !=null && !nome.isEmpty() && email !=null
				&& !email.isEmpty() && endereco != null && !endereco.isEmpty() &&
				nomeAluno!=null && !nomeAluno.isEmpty() &&
				turma!=null && !turma.isEmpty() &&
				diagnostico!=null && !diagnostico.isEmpty()) {
			
			UsuarioModel usuario= new UsuarioModel(nome, email, endereco);
			FamiliarModel familiar= new FamiliarModel();
			AlunoModel aluno= new AlunoModel(nomeAluno, diagnostico, turma);
			//usuario.cadastrar();
			editarDao.editarPerfilFamiliar(request, usuario, familiar, aluno);
			editarDao.atualizarDadosFamiliar(request);
			System.out.println(usuario.getNome());
			System.out.println(usuario.getEmail());
			System.out.println(usuario.getSenha());
			System.out.println(usuario.getEndereco());
			System.out.println(usuario.getPerfil());
			System.out.println(aluno.getNome());
			System.out.println(aluno.getDiagnosticoTEA());
			System.out.println(aluno.getTurma());
			mensagem= "Usuário editado com sucesso!";
			request.setAttribute("mensagem", mensagem);			
			dispatcher.forward(request, response);
		}else {
			mensagem= "Os campos precisam ser preenchidos";
			request.setAttribute("mensagem", mensagem);			
			dispatcher.forward(request, response);
		}
	}

}
