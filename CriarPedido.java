package unitea.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import unitea.dao.CriarPedidoDao;
import unitea.model.PedidoMonitoriaModel;

import java.io.IOException;

/**
 * Servlet implementation class CriarPedido
 */
@WebServlet("/CriarPedido")
public class CriarPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CriarPedido() {
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
		
		HttpSession session = request.getSession();
		int idFamiliar= (Integer) session.getAttribute("id_familiar");
		int idAluno= (Integer) session.getAttribute("id_aluno");
		String status= "pendente";
		
		String disciplina= request.getParameter("disciplina");
		String informacoes= request.getParameter("informacoesAdicionais");
		String nomechat= request.getParameter("nomechat");
		
		PedidoMonitoriaModel pedido= new PedidoMonitoriaModel(idFamiliar,idAluno, informacoes, status, nomechat, disciplina);
		
		CriarPedidoDao pedidoDao= new CriarPedidoDao();
		
		pedidoDao.CriarPedido(pedido);
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("pedidosFamiliar.jsp");
		dispatcher.forward(request, response);
	}

}
