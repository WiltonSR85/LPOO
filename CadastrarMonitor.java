package unitea.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unitea.dao.FamiliarDao;
import unitea.dao.MonitorDao;
import unitea.model.AlunoModel;
import unitea.model.FamiliarModel;
import unitea.model.MonitorModel;
import unitea.model.UsuarioModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class CadastrarMonitor
 */
//@WebServlet("/CadastrarMonitor")
public class CadastrarMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastrarMonitor() {
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
		String senha= request.getParameter("senha");
		String endereco= request.getParameter("endereco");
		String perfil= request.getParameter("perfil");
		String especializacao= request.getParameter("especializacao");
		String anosExperiencia= request.getParameter("anosExperiencia");
		String formacaoAcademica= request.getParameter("formacaoAcademica");
		
		int anosExperiencia1= Integer.parseInt(anosExperiencia);
		
		System.out.println(nome);
		System.out.println(email);
		System.out.println(senha);
		System.out.println(endereco);
		System.out.println(perfil);
		System.out.println(especializacao);
		System.out.println(anosExperiencia1);
		System.out.println(formacaoAcademica);
		
		List<String> disciplinas = new ArrayList<>();
	    for (int i = 1; i <= 4; i++) {
	        String disciplina = request.getParameter("disciplina" + i);
	        if (disciplina != null && !disciplina.isEmpty()) {
	            disciplinas.add(disciplina);
	        }
	    }
	    
	    MonitorModel monitor1= new MonitorModel(anosExperiencia1, formacaoAcademica, especializacao, disciplinas);
	    monitor1.exibirDisciplinas();

		String mensagem;
		MonitorDao monitorDao= new MonitorDao();
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("cadastroMonitor.jsp");
		
		if(nome != null && !nome.isEmpty() && 
			    email != null && !email.isEmpty() && 
			    senha != null && !senha.isEmpty() && 
			    endereco != null && !endereco.isEmpty() && 
			    perfil != null && !perfil.isEmpty() && 
			    especializacao != null && !especializacao.isEmpty() && 
			    anosExperiencia != null && !anosExperiencia.isEmpty() && 
			    formacaoAcademica != null && !formacaoAcademica.isEmpty() && 
			    !disciplinas.isEmpty()) {
			
			
			UsuarioModel usuario= new UsuarioModel(nome, email, senha, endereco, perfil);
			MonitorModel monitor= new MonitorModel(anosExperiencia1, formacaoAcademica, especializacao, disciplinas);
			//usuario.cadastrar();
			monitorDao.cadastrarMonitor(usuario, monitor);
			System.out.println(usuario.getNome());
			System.out.println(usuario.getEmail());
			System.out.println(usuario.getSenha());
			System.out.println(usuario.getEndereco());
			System.out.println(usuario.getPerfil());
			System.out.println(monitor.getAnosExperiencia());
			System.out.println(monitor.getEspecializacao());
			System.out.println(monitor.getFormacaoAcademica());
			monitor.exibirDisciplinas();
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
