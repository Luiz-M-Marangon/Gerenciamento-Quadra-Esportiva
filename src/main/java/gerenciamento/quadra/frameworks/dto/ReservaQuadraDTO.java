package gerenciamento.quadra.frameworks.dto;

public class ReservaQuadraDTO {

    private String esporte;
    private Long total;

    public ReservaQuadraDTO(String esporte, Long total) {
        this.esporte = esporte;
        this.total = total;
    }

    public String getEsporte() {
        return esporte;
    }

    public Long getTotal() {
        return total;
    }
}