package services;

import exceptions.AgendamentoException;
import model.Agendamento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoServices {

    private static List<Agendamento> listaAgendamentos = new ArrayList<>();

    // Cadastrar um agendamento
    public static void agendarLavagem(Agendamento a){

        if(a.getCliente() == null){
            throw new AgendamentoException("Cliente é obrigatório!");
        }
        if(a.getTipo() == null){
            throw new AgendamentoException("Tipo de serviço é obrigatório!");
        }
        if(a.getDataHora() == null){
            throw new AgendamentoException("Data e hora são obrigatórios");
        }
        if(conflitoHorario(a.getDataHora())){
            throw new AgendamentoException("Já existe um agendamento marcado nesse horário!");
        }

        listaAgendamentos.add(a); // Enivando o agendamento para a lista
        a.setStatus("Agendado");
    }

    //Editar Agendamento
    public void editarDadosDoAgendamento(Agendamento agendamentoAtualizado){
        Agendamento agendamentoExistente = buscarPorId(agendamentoAtualizado.getIdAgendamento());

        if(agendamentoExistente == null){
            throw new AgendamentoException("Agendamento não encontrado!");
        }
        if(!agendamentoExistente.getDataHora().equals(agendamentoAtualizado.getDataHora()) && conflitoHorario(agendamentoAtualizado.getDataHora())) {
            throw new AgendamentoException("Esse horário já foi utilizado!");
        }

        agendamentoExistente.setCliente(agendamentoAtualizado.getCliente());
        agendamentoExistente.setTipo(agendamentoAtualizado.getTipo());
        agendamentoExistente.setDataHora(agendamentoAtualizado.getDataHora());
        agendamentoExistente.setStatus(agendamentoAtualizado.getStatus());
    }

    // Cancelar Agendamento
    public void cancelarAgendamento(int id){
        Agendamento agendamentoCadastrado = buscarPorId(id);
        if(agendamentoCadastrado == null){
            throw new AgendamentoException("Agendamento não encontrado!");
        }

        agendamentoCadastrado.setStatus("Cancelado");
    }

    // Busca por ID
    public Agendamento buscarPorId(int id) {
        for (Agendamento a : listaAgendamentos) {
            if (a.getIdAgendamento() == id) {
                return a;
            }
        }
        return null;
    }

    // Listar todos os Agendamentos disponíveis
    public static List<Agendamento> listarTodosOsAgendamentos(){
        return new ArrayList<>(listaAgendamentos);
    }

    // Listar Agendamentos por data
    public List<Agendamento> listarAgendamentoPorData(LocalDateTime data){
        List<Agendamento> resultado = new ArrayList<>();
        for(Agendamento a: listaAgendamentos){
            if(a.getDataHora().toLocalDate().equals(data.toLocalDate())){
                resultado.add(a);
            }
        }
        return resultado;
    }


    // Verifica se já existe um agendamento na mesma data/hora
    private static boolean conflitoHorario(LocalDateTime dataHora) {
        for (Agendamento a : listaAgendamentos) {
            if (a.getDataHora().equals(dataHora) && a.getStatus().equals("Agendado")) {
                return true;
            }
        }
        return false;
    }

}
