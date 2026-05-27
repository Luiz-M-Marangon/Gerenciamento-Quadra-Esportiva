package gerenciamento.quadra.frameworks.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime horarioInicial;
    private LocalTime horarioFinal;
    private String responsavel;
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "quadra_id")
    @JsonIgnoreProperties("reservas")
    private Quadra quadra;

    @ManyToMany
    @JoinTable(
            name = "reserva_servico",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    @JsonIgnoreProperties("reservas")
    private List<Servico> servicos;

    @Override
    public String toString() {
        return "Reserva{id=" + id
                + ", data='" + data
                + "', horario inicial=" + horarioInicial
                + ", horario final=" + horarioFinal
                + ", valor total=" + valorTotal
                + "}";
    }

    public void calcularValorTotal() {

        long horas = Duration.between(horarioInicial, horarioFinal).toHours();

        double total = quadra.getValorHora() * horas;

        if (servicos != null) {
            for (Servico s : servicos) {
                total += s.getValorAdicional();
            }
        }

        this.valorTotal = total;
    }
}
