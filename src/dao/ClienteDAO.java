package dao;


import exceptions.DBExcpetion;
import model.Cliente;
import util.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome,cpf,telefone,email,placaVeiculo) VALUES (?,?,?,?,?)";

        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,cliente.getNome());
            stmt.setString(2,cliente.getCpf());
            stmt.setString(3,cliente.getTelefone());
            stmt.setString(4,cliente.getEmail());
            stmt.setString(5,cliente.getPlacaVeiculo());

            System.out.println("Salvando cliente: " + cliente.getNome() + ", CPF: " + cliente.getCpf());
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");

        }catch (SQLException e){
            e.printStackTrace();
            throw new DBExcpetion("Não foi possível cadastrar o cliente, tente novamente!");
        }

    }

    // Editar dados
    public void editar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, cpf = ?, telefone = ?, email = ?, placaVeiculo = ? WHERE id = ?";

        try (Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getPlacaVeiculo());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao editar o cliente!");
        }
    }

    // Excluir Cliente
    public void excluir(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao excluir o cliente!");
        }
    }

    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try (Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setPlacaVeiculo(rs.getString("placaVeiculo"));
                return cliente;
            }

        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao buscar cliente pelo CPF!");
        }

        return null;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setPlacaVeiculo(rs.getString("placaVeiculo"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            throw new DBExcpetion("Erro ao listar os clientes!");
        }

        return lista;
    }


    public Cliente buscarPorId(int id){
        String sql = "SELECT * FROM clientes WHERE id = ?";


        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                return cliente;
            }

        } catch (SQLException e) {
            throw new DBExcpetion("Esse id não existe!");
        }
        return null;


    }

}
