package gerenciamento.quadra.frameworks.repository;

import gerenciamento.quadra.frameworks.model.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
    @Query("SELECT DISTINCT q.esporte FROM Quadra q")
    List<String> listarEsportes();
}
