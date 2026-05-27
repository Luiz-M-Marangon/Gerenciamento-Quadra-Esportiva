package gerenciamento.quadra.frameworks.repository;

import gerenciamento.quadra.frameworks.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
