package gerenciamento.quadra.frameworks.dto;

public class ReservaRelatorioDTO {

    private String quadra;
    private String horario;
    private String servicos;

    public ReservaRelatorioDTO(String quadra, String horario, String servicos) {
        this.quadra = quadra;
        this.horario = horario;
        this.servicos = servicos;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getServicos() {
        return servicos;
    }

    public void setServicos(String servicos) {
        this.servicos = servicos;
    }
}