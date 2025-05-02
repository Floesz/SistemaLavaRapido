package services;

import exceptions.ServicoException;
import model.TipoServico;

import java.util.ArrayList;
import java.util.List;

public class TipoServices {

    private static List<TipoServico> listaServicos = new ArrayList<>();


    // Cadastrar um novo Serviço
    public void cadastrarServico(TipoServico s){
        if(s.getNomeServico() == null || s.getNomeServico().isEmpty()){
            throw new ServicoException("O nome do tipo de serviço é obrigatório!");
        }
        if(s.getDescricao() == null || s.getDescricao().isEmpty()){
            throw new ServicoException("A descrição desse serviço é obrigatório!");
        }
        if(s.getPreco() <= 0){
            throw new ServicoException("O preço desse serviço tem que ser maior que zero!");
        }

        listaServicos.add(s);
    }

    // Editar dados serviço
    public void editarDadosServico(TipoServico tsAtualizado){
        TipoServico tipoExistente = buscarPorId(tsAtualizado.getId());

        if(tipoExistente == null){
            throw new ServicoException("Tipo de serviço não encontrado!");
        }

        tipoExistente.setNomeServico(tsAtualizado.getNomeServico());
        tipoExistente.setId(tsAtualizado.getId());
        tipoExistente.setDescricao(tsAtualizado.getDescricao());
        tipoExistente.setPreco(tsAtualizado.getPreco());
    }

    //Excluir um serviço
    public void excluirServico(int id){
        TipoServico servico = buscarPorId(id);

        if(servico == null){
            throw new ServicoException("Serviço não encontrado!");
        }

        listaServicos.remove(servico);
    }

    // Listar todos os serviços disponíveis
    public static List<TipoServico> listarTodosOsServicos(){
        System.out.println("Lista de serviços: " + listaServicos);
        return new ArrayList<>(listaServicos);
    }

    //Buscar serviço pelo seu ID
    public TipoServico buscarPorId(int id){
        for(TipoServico ts : listaServicos){
            if(ts.getId() == id){
                return ts;
            }
        }
        throw new ServicoException("ID nao encontrado!");
    }
}
