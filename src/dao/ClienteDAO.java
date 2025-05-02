package dao;


import exceptions.DBExcpetion;
import model.Cliente;
import util.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome,cpf,telefone,email,placaVeiculo) VALUES (?,?,?,?,?)";

        try(Connection conn = connection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,cliente.getNome());
            stmt.setString(2,cliente.getCpf());
            stmt.setString(3,cliente.getTelefone());
            stmt.setString(4,cliente.getEmail());
            stmt.setString(5,cliente.getPlacaVeiculo());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DBExcpetion("Não foi possível cadastrar o cliente, tente novamente!");
        }
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
