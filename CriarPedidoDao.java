package unitea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import unitea.model.PedidoMonitoriaModel;

public class CriarPedidoDao {

    public void CriarPedido(PedidoMonitoriaModel pedido) {
        // Definindo os SQLs
        String sqlInsert = "INSERT INTO UNITEA.pedidoMonitoria (id_aluno, id_familiar, status, nome_chat, disciplina, informacoes) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlSelect = "SELECT * FROM UNITEA.pedidoMonitoria WHERE id_pedido = ?";
        String sqlInsert1 = "INSERT INTO UNITEA.pedidos_criados  (id_pedido, id_familiar) VALUES (?, ?)";

        PreparedStatement pStatementInsert = null;
        PreparedStatement pStatementSelect = null;
        PreparedStatement pStatementInsert1 = null;
        Connection conn = null;
        ResultSet generatedId = null;
        ResultSet rs1 = null;
        
        int IdPedido=0;
        int IdFamiliar=0;
        int IdAluno=0;
        String informacoes= null;
        String status= null;
        String nomeChat= null;
        String disciplina= null;

        int idPedido = 0;
        

        try {
            // Conectando ao banco de dados
            conn = new MysqlConnection().getConnection();
            
            if (conn != null) {
                // Inserindo o pedido no banco
                pStatementInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pStatementInsert.setInt(1, pedido.getAluno());
                pStatementInsert.setInt(2, pedido.getSolicitante());
                pStatementInsert.setString(3, pedido.getStatus());
                pStatementInsert.setString(4, pedido.getNomeChat());
                pStatementInsert.setString(5, pedido.getDisciplina());
                pStatementInsert.setString(6, pedido.getInformacoes());
                
                int rowsAffected = pStatementInsert.executeUpdate();
                System.out.println("Pedido inserido. Linhas afetadas: " + rowsAffected);

                // Verificando se o ID do pedido foi gerado
                generatedId = pStatementInsert.getGeneratedKeys();
                if (generatedId.next()) {
                    idPedido = generatedId.getInt(1);
                    System.out.println("ID do Pedido: " + idPedido);
                } else {
                    System.out.println("Falha ao gerar o ID do pedido.");
                    return; // Se não gerar o ID, retornamos sem continuar o processo
                }

                // Buscando os dados do pedido inserido
                pStatementSelect = conn.prepareStatement(sqlSelect);
                pStatementSelect.setInt(1, idPedido);
                rs1 = pStatementSelect.executeQuery();
                
                if (rs1.next()) {
                	System.out.println("to no if ");
                    IdPedido=rs1.getInt("id_pedido");
                    IdFamiliar= rs1.getInt("id_familiar");
                    IdAluno=rs1.getInt("id_aluno");
                    informacoes=rs1.getString("informacoes");
                    status=rs1.getString("status");
                    nomeChat=rs1.getString("nome_chat");
                    disciplina=rs1.getString("disciplina");
                    
                    System.out.println(IdPedido);
                    System.out.println(IdAluno);
                    
                    pStatementInsert1 = conn.prepareStatement(sqlInsert1);
                    pStatementInsert1.setInt(1, IdPedido);
                    pStatementInsert1.setInt(2, IdFamiliar);
                    
                    pStatementInsert1.executeUpdate();
                    
                    System.out.println("Dados do pedido atualizados.");
                }
                
                System.out.println("Dados do pedido atualizados.");

            } else {
                System.out.println("Falha na conexão com o banco de dados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Fechando os recursos no bloco finally para garantir que sempre sejam fechados
                if (rs1 != null) rs1.close();
                if (pStatementInsert != null) pStatementInsert.close();
                if (pStatementSelect != null) pStatementSelect.close();
                if (pStatementInsert1 != null) pStatementInsert1.close();
                if (conn != null) conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}

