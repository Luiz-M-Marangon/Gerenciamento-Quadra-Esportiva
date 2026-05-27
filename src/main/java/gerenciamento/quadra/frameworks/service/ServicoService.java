package gerenciamento.quadra.frameworks.service;

import gerenciamento.quadra.frameworks.model.Servico;
import gerenciamento.quadra.frameworks.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoAdicionalService {

    @Autowired
    private ServicoRepository repository;

    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Serviço não encontrado")
        );
    }

    public Servico salvar(Servico servico) {
        return repository.save(servico);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}