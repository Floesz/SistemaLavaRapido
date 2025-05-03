package services;

import exceptions.ClienteException;
import model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteServices {

    private static List<Cliente> listaClientes = new ArrayList<>();


    // Cadastro do cliente
    public void cadastrarCliente(Cliente c){
        if (c.getNome() == null || c.getNome().isEmpty()){
            throw new ClienteException("Nome do cliente é obrigatório!");
        }
        if(c.getCpf() == null || c.getCpf().isEmpty()){
            throw new ClienteException("CPF do cliente é obrigatório!");
        }

        listaClientes.add(c);
    }

    // Editar dados do cliente
    public void editarDadosCliente(Cliente clienteAtualizado){

        Cliente clienteExistente = buscarPorId(clienteAtualizado.getId());

        if(clienteExistente == null){
            throw new ClienteException("Cliente não encontrado!");
        }

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setPlacaVeiculo(clienteAtualizado.getPlacaVeiculo());
    }

    // Excluir um cliente
    public void excluirCliente(int id){
        Cliente cliente = buscarPorId(id);

        if(cliente == null){
            throw new ClienteException("Cliente não encontrado!");
        }

        listaClientes.remove(cliente);
    }

    // Listar todos os clientes
    public static List<Cliente> listarTodosOsClientes(){
        return new ArrayList<>(listaClientes);
    }

    //Busca do cliente pelo CPF
    public Cliente buscarPorCpf(String cpf){
        for(Cliente c: listaClientes){
            if(c.getCpf().equals(cpf)){
                return c;
            }
        }
        throw new ClienteException("Nenhum cliente encontrado com esse CPF!");
    }

    // Busca do cliente pelo ID
    public Cliente buscarPorId(int id) {
        for (Cliente c : listaClientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new ClienteException("Nenhum cliente encontrado com esse ID!");
    }



    // Verifica se o CPF já está cadastrado
    private boolean cpfJaCadastrado(String cpf) {
        return listaClientes.stream().anyMatch(c -> c.getCpf().equals(cpf));
    }
}
