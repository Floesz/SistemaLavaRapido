package controller;

import dao.TipoServicoDAO;
import exceptions.ServicoException;
import model.TipoServico;
import services.TipoServices;

import java.util.List;

public class TipoServicoController {

    private TipoServices tpServices = new TipoServices();
    private TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();

    // Adicionar um tipo de serviço novo
    public void adicionarTipoDeServico(TipoServico tp) throws ServicoException {
        tipoServicoDAO.inserir(tp);
    }
    // Editar um tipo de serviço
    public void editarTipoDeServico(TipoServico tp) throws ServicoException{
        tipoServicoDAO.atualizar(tp);
    }
    // Excluir um tipo de serviço
    public void excluirTipoDeServico(int id) throws ServicoException{
        tipoServicoDAO.excluir(id);
    }
    // Buscar por ID
    public TipoServico buscarPorId(int id){
        return tipoServicoDAO.buscarPorId(id);
    }

    // Listagem de todos os tipos de serviço
    public List<TipoServico> listarTodos(){
        return tipoServicoDAO.listarTodos();
    }

}
