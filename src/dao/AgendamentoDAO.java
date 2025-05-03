package dao;

import exceptions.DBExcpetion;
import model.Cliente;
import model.TipoServico;
import model.Agendamento;
import util.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public void adicionar(Agendamento ag){
        String sql = "INSERT INTO agendamentos (cliente_id,tipoServico_id,dataHora,status) VALUES (?,?,?,?)";

        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1,ag.getCliente().getId());
            stmt.setInt(2,ag.getTipo().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(ag.getDataHora()));
            stmt.setString(4,ag.getStatus());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DBExcpetion("Não foi possível adicionar um agendamento novo!");
        }
    }
    public void editar(Agendamento ag){
        String sql = "UPDATE agendamentos SET cliente_id = ?, tipoServico_id = ?, dataHora = ?, status = ? WHERE idAgendamento = ?";

        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,ag.getCliente().getId());
            stmt.setInt(2,ag.getTipo().getId());
            stmt.setTimestamp(3,Timestamp.valueOf(ag.getDataHora()));
            stmt.setString(4,ag.getStatus());
            stmt.setInt(5,ag.getIdAgendamento());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DBExcpetion("Erro ao atualizar o agendamento!");
        }
    }

    public void cancelar(int id){
        String sql = "UPDATE agendamentos SET status = 'Cancelado' WHERE idAgendamento = ?";;

        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DBExcpetion("Erro ao cancelar o agendamento desejado!");
        }
    }

    public Agendamento buscarPorId(int id) {
        String sql = "SELECT * FROM agendamentos WHERE idAgendamento = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Agendamento ag = new Agendamento();
                ag.setIdAgendamento(rs.getInt("id"));
                ag.setDataHora(rs.getTimestamp("dataHora").toLocalDateTime());
                ag.setStatus(rs.getString("status"));

                ClienteDAO clienteDAO = new ClienteDAO();
                TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();

                Cliente cliente = clienteDAO.buscarPorId(rs.getInt("cliente_id"));
                TipoServico tipo = tipoServicoDAO.buscarPorId(rs.getInt("tipoServico_id"));

                ag.setCliente(cliente);
                ag.setTipo(tipo);

                return ag;
            }

        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao buscar o agendamento por ID!");
        }

        return null;
    }


    public List<Agendamento> listarTodos() {
        String sql = "SELECT * FROM agendamentos";
        List<Agendamento> lista = new ArrayList<>();

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ClienteDAO clienteDAO = new ClienteDAO();
            TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();

            while (rs.next()) {
                Agendamento ag = new Agendamento();
                ag.setIdAgendamento(rs.getInt("idAgendamento"));
                ag.setDataHora(rs.getTimestamp("dataHora").toLocalDateTime());
                ag.setStatus(rs.getString("status"));

                Cliente cliente = clienteDAO.buscarPorId(rs.getInt("cliente_id"));
                TipoServico tipoServico = tipoServicoDAO.buscarPorId(rs.getInt("tipoServico_id"));

                ag.setCliente(cliente);
                ag.setTipo(tipoServico);

                lista.add(ag);
            }

        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao listar agendamentos!");
        }

        return lista;
    }

}
