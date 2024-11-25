package unitea.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unitea.dao.UsuarioDao;
import unitea.model.UsuarioModel;

import java.io.IOException;

/**
 * Servlet implementation class CadastrarUsuario
 */
	@WebServlet("/CadastrarUsuario")
public class CadastrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastrarUsuario() {
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
		
		String nome= request.getParameter("Nome");
		String email= request.getParameter("Email");
		String senha= request.getParameter("Senha");
		String endereco= request.getParameter("Endereco");
		String perfil= request.getParameter("Perfil");
		String mensagem;
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("java.jsp");
		
		if(nome !=null && !nome.isEmpty() && email !=null
				&& !email.isEmpty() && senha !=null && !senha.isEmpty()
				&& endereco != null && !endereco.isEmpty() &&
				perfil !=null && !perfil.isEmpty()) {
			
			UsuarioModel usuario= new UsuarioModel(nome, email, senha, endereco, perfil);
			usuario.cadastrar();
			System.out.println(usuario.getNome());
			System.out.println(usuario.getEmail());
			System.out.println(usuario.getSenha());
			System.out.println(usuario.getEndereco());
			System.out.println(usuario.getPerfil());
			mensagem= "Usuário cadastrado com sucesso!";
			request.setAttribute("mensagem", mensagem);			
			dispatcher.forward(request, response);
		}
		
		else {
			mensagem= "Os campos precisam ser preenchidos";
			request.setAttribute("mensagem", mensagem);			
			dispatcher.forward(request, response);
		}
	}
	
}
