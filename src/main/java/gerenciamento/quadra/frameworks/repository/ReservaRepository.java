package gerenciamento.quadra.frameworks.repository;

import gerenciamento.quadra.frameworks.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
