package gerenciamento.quadra.frameworks.repository;

import gerenciamento.quadra.frameworks.model.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
}
