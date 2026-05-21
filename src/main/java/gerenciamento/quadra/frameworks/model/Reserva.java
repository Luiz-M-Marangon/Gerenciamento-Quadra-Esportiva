package gerenciamento.quadra.frameworks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String cliente;
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clientes;

    @ManyToOne
    @JoinColumn(name = "quadra_id")
    private Quadra quadra;

    @ManyToMany
    @JoinTable(
            name = "resernva_servido",
            joinColumns = @JoinColumn(name = "resernva_id"),
            inverseJoinColumns = @JoinColumn(name = "servicp_id")
    )
    private List<Servico> servicos;

    @Override
    public String toString(){
        return "Reserva{id=" + id
                + ", data='" + data
                + "', horario inicial=" + horarioInicial
                + ", horario final=" + horarioFinal
                + ", cliente=" + cliente
                + ", valor total=" + valorTotal
                + "}";
    }
}
