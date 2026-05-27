package gerenciamento.quadra.frameworks.repository;

import gerenciamento.quadra.frameworks.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
