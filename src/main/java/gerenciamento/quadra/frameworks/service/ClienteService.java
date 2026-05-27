package gerenciamento.quadra.frameworks.service;

import gerenciamento.quadra.frameworks.model.Cliente;
import gerenciamento.quadra.frameworks.model.Quadra;
import gerenciamento.quadra.frameworks.repository.ClienteRepository;
import gerenciamento.quadra.frameworks.repository.QuadraRepository;
import gerenciamento.quadra.frameworks.repository.ReservaRepository;
import gerenciamento.quadra.frameworks.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private QuadraRepository quadraRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    public Cliente salvar(Cliente cliente){
        if (cliente.getQuadra() != null && cliente.getQuadra().getId() != null){
            Quadra quadra = quadraRepository.findById(cliente.getQuadra().getId())
                    .orElseThrow(() -> new RuntimeException("Quadra não encontrado: " + cliente.getQuadra().getId()));
            cliente.setQuadra(quadra);
        } else{
            cliente.setQuadra(null);
        }
    }
}
