package gerenciamento.quadra.frameworks.service;


import gerenciamento.quadra.frameworks.dto.ReservaQuadraDTO;
import gerenciamento.quadra.frameworks.model.Quadra;
import gerenciamento.quadra.frameworks.model.Reserva;
import gerenciamento.quadra.frameworks.repository.QuadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuadraService {

    @Autowired
    private QuadraRepository repository;

    public List<Quadra> listarTodas() {
        return repository.findAll();
    }

    public Quadra buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Quadra não encontrada")
        );
    }

    public Quadra salvar(Quadra quadra) {
        return repository.save(quadra);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
