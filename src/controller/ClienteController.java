package controller;

import dao.ClienteDAO;
import dao.TipoServicoDAO;
import model.Cliente;
import services.ClienteServices;

import java.util.List;

public class ClienteController {

    private ClienteServices clServices = new ClienteServices();
    private ClienteDAO clDAO = new ClienteDAO();
    // Adicionar um cliente novo
    public void adicionarCliente(Cliente c){
        clDAO.salvar(c);
    }

    // Atualizar dados de um cliente registrado
    public void editarCliente(Cliente c){
        clDAO.editar(c);
    }

    // Excluir cliente
    public void excluirCliente(int id){
        clDAO.excluir(id);
    }

    // Buscar por ID
    public Cliente buscarPorId(int id){
        return clDAO.buscarPorId(id);
    }
    // Buscar por Cpf
    public Cliente buscarPorCpf(String cpf){
        return clDAO.buscarPorCpf(cpf);
    }

    // Listagem de todos os clientes
    public List<Cliente> listarTodos(){
        return clDAO.listarTodos();
    }

}
