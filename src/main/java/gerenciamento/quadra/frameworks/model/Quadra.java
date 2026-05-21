package gerenciamento.quadra.frameworks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quadra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String esporte;
    private double valorHora;
    private String localizacao;
    private Boolean coberta;

    @OneToMany(mappedBy = "quadra")
    private List<Reserva> reservas;
}
