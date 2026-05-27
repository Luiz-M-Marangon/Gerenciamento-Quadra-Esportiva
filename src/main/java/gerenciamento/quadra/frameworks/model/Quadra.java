package gerenciamento.quadra.frameworks.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("quadra")
    private List<Reserva> reservas;

    @Override
    public String toString(){
        return "Quadra{id=" + id
                + ", nome= '+" + nome
                + "', esporte='" + esporte
                + "', Valor da Hora='" + valorHora
                + "', Localização='" + localizacao
                + "', Cobertura=" + coberta
                + "}";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEsporte() {
        return esporte;
    }

    public void setEsporte(String esporte) {
        this.esporte = esporte;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Boolean getCoberta() {
        return coberta;
    }

    public void setCoberta(Boolean coberta) {
        this.coberta = coberta;
    }
}
