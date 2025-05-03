package dao;

import exceptions.DBExcpetion;
import model.TipoServico;
import util.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoServicoDAO {

    public void inserir(TipoServico tipo) {
        String sql = "INSERT INTO tipoServico (nomeServico,descricao,preco) VALUES (?,?,?)";

        // Tenta a conexão com BD
        try (Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,tipo.getNomeServico());
            stmt.setString(2,tipo.getDescricao());
            stmt.setDouble(3,tipo.getPreco());
            stmt.executeUpdate();

         // Retorna uma exceção caso tenha um erro
        }catch (SQLException e){
            throw new DBExcpetion("Erro ao inserir o tipo de serviço!");
        }
    }

    // Atualizar dados
    public void atualizar(TipoServico tipo){
        String sql = "UPDATE tipoServico SET nomeServico = ?, descricao = ?, preco = ? WHERE idServico = ?";

        // Testa conexão com o BD
        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,tipo.getNomeServico());
            stmt.setString(2,tipo.getDescricao());
            stmt.setDouble(3,tipo.getPreco());
            stmt.setInt(4,tipo.getId());
            stmt.executeUpdate();

            // Retorna uma mensagem caso tenha um erro
        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao atualizar o tipo de serviço!");
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tipoServico WHERE idServico = ?";

        try (Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao excluir o tipo de serviço!");
        }
    }


    // Busca de um tipo de serviço específico pelo ID
    public TipoServico buscarPorId(int id){
        String sql = "SELECT * FROM tipoServico WHERE idServico = ?";

        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                TipoServico tp = new TipoServico();
                tp.setId(rs.getInt("idServico"));
                tp.setNomeServico(rs.getString("nomeServico"));
                tp.setDescricao(rs.getString("descricao"));
                tp.setPreco(rs.getDouble("preco"));
                return tp;
            }
        }catch (SQLException e){
            throw new DBExcpetion("Erro ao buscar o tipo de serviço pelo ID!");
        }return null;
    }

    // Retorna a lista de todos os tipos de serviço
    public List<TipoServico> listarTodos() {
        List<TipoServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipoServico";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoServico tp = new TipoServico();
                tp.setId(rs.getInt("idServico"));
                tp.setNomeServico(rs.getString("nomeServico"));
                tp.setDescricao(rs.getString("descricao"));
                tp.setPreco(rs.getDouble("preco"));
                lista.add(tp);
            }

        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao listar tipos de serviço!");
        }

        return lista;
    }
}