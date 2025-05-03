package controller;

import dao.AgendamentoDAO;
import exceptions.AgendamentoException;
import model.Agendamento;
import services.AgendamentoServices;

import java.util.List;

public class AgendamentoController {

    private AgendamentoServices agservices = new AgendamentoServices();
    private AgendamentoDAO agDAO = new AgendamentoDAO();

    // Controller para adicionar um Agendamento novo
    public void adicionarAgendamento(Agendamento ag){
        if(ag.getCliente() == null || ag.getTipo() == null){
            throw new AgendamentoException("O nome do cliente e o tipo de serviço são obrigatórios!");
        }
        agDAO.adicionar(ag);
    }

    // Controller para editar dados de um Agendamento
    public void editarAgendamento(Agendamento ag){
        if(ag.getIdAgendamento() < 0){
            throw new AgendamentoException("ID inválido!");
        }
        agDAO.editar(ag);
    }

    // Controller para cancelar um Agendamento
    public void cancelarAgendamento(int id){
        if(id < 0){
            throw new AgendamentoException("ID inválido!");
        }
        agDAO.cancelar(id);
    }

    // Listagem de todos os Agendamentos
    public List<Agendamento> listarTodos(){
        return agDAO.listarTodos();
    }

    // Buscar o Agendamento pelo ID
    public Agendamento buscarPorId(int id){
        return agDAO.buscarPorId(id);
    }

}
