package gerenciamento.quadra.frameworks.repository;

import gerenciamento.quadra.frameworks.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("""
    SELECT r FROM Reserva r
    WHERE r.quadra.id = :quadraId
    AND r.data = :data
    AND (
        :inicio < r.horarioFinal
        AND :fim > r.horarioInicial
    )
    """)
    List<Reserva> verificarConflito(
            Long quadraId,
            LocalDate data,
            LocalTime inicio,
            LocalTime fim
    );

    @Query("SELECT r FROM Reserva r WHERE r.quadra.esporte = :esporte")
    List<Reserva> buscarPorTipoQuadra(String esporte);

    @Query("""
    SELECT r FROM Reserva r
    WHERE r.data BETWEEN :inicio AND :fim
    """)
    List<Reserva> buscarEntreDatas(LocalDate inicio, LocalDate fim);

    @Query("""
    SELECT r FROM Reserva r
    WHERE r.responsavel = :responsavel
    """)
    List<Reserva> buscarPorResponsavel(String responsavel);

    @Query("""
    SELECT DISTINCT r.responsavel
    FROM Reserva r
    """)
    List<String> listarResponsaveis();
}
