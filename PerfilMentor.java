package unitea.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class PerfilMentor
 */
//@WebServlet("/PerfilMentor")
public class PerfilMentor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilMentor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperando informações da sessão
        HttpSession session = request.getSession();
        String nome = (String) session.getAttribute("nome");
        String email = (String) session.getAttribute("email");
        String endereco = (String) session.getAttribute("endereco");
        String perfil = (String) session.getAttribute("perfil");
        String especializacao = (String) session.getAttribute("especializacao");
        String anosDeExperiencia = (String) session.getAttribute("anosDeExperiencia");
        String formacaoAcademica = (String) session.getAttribute("formacaoAcademica");

        // Recuperando o campo JSON de disciplinas
        String disciplinasJsonStr = (String) session.getAttribute("disciplinas");

        // Parse do JSON
        JSONObject disciplinasJson = new JSONObject(disciplinasJsonStr);
        JSONArray disciplinasArray = disciplinasJson.getJSONArray("disciplinas"); // Aqui recuperamos o array de disciplinas

        // Agora, podemos pegar cada disciplina
        String disciplina1 = disciplinasArray.getString(0);
        String disciplina2 = disciplinasArray.getString(1);
        String disciplina3 = disciplinasArray.getString(2);
        String disciplina4 = disciplinasArray.getString(3);

        // Definindo os atributos para a view (JSP)
        request.setAttribute("nome", nome);
        request.setAttribute("email", email);
        request.setAttribute("endereco", endereco);
        request.setAttribute("perfil", perfil);
        request.setAttribute("especializacao", especializacao);
        request.setAttribute("anosDeExperiencia", anosDeExperiencia);
        request.setAttribute("formacaoAcademica", formacaoAcademica);
        request.setAttribute("disciplina1", disciplina1);
        request.setAttribute("disciplina2", disciplina2);
        request.setAttribute("disciplina3", disciplina3);
        request.setAttribute("disciplina4", disciplina4);

        // Redirecionando para a página JSP
        request.getRequestDispatcher("/perfilMentor.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
