package gerenciamento.quadra.frameworks.service;

import gerenciamento.quadra.frameworks.model.Reserva;
import gerenciamento.quadra.frameworks.model.Servico;
import gerenciamento.quadra.frameworks.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import gerenciamento.quadra.frameworks.dto.ReservaRelatorioDTO;
import java.time.Duration;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public Page<Reserva> listarPaginado(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return repository.findAll(pageable);
    }

    public Reserva buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Reserva não encontrada")
        );
    }

    public Reserva salvar(Reserva reserva) {

        List<Reserva> conflitos = repository.verificarConflito(
                reserva.getQuadra().getId(),
                reserva.getData(),
                reserva.getHorarioInicial(),
                reserva.getHorarioFinal()
        );

        if (!conflitos.isEmpty()) {
            throw new RuntimeException("Já existe reserva nesse horário nesta mesma quadra.");
        }

        calcularValorTotal(reserva);

        return repository.save(reserva);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private void calcularValorTotal(Reserva reserva) {

        long horas = Duration.between(
                reserva.getHorarioInicial(),
                reserva.getHorarioFinal()
        ).toHours();

        double valorQuadra =
                horas * reserva.getQuadra().getValorHora();

        double valorServicos = 0;

        if (reserva.getServicos() != null) {

            valorServicos = reserva.getServicos()
                    .stream()
                    .mapToDouble(servico -> servico.getValorAdicional())
                    .sum();
        }

        reserva.setValorTotal(valorQuadra + valorServicos);
    }


    public List<ReservaRelatorioDTO> gerarRelatorioReservas() {

        List<Reserva> reservas = repository.findAll();

        return reservas.stream().map(reserva -> {

            String servicos = reserva.getServicos()
                    .stream()
                    .map(Servico::getNome)
                    .collect(Collectors.joining(", "));

            String horario = reserva.getHorarioInicial()
                    + " - " +
                    reserva.getHorarioFinal();

            return new ReservaRelatorioDTO(
                    reserva.getQuadra().getNome(),
                    horario,
                    servicos
            );

        }).toList();
    }

    public List<Reserva> buscarPorTipoQuadra(String esporte) {
        return repository.buscarPorTipoQuadra(esporte);
    }

    public List<Reserva> buscarEntreDatas(LocalDate inicio, LocalDate fim) {
        return repository.buscarEntreDatas(inicio, fim);
    }

    public List<Reserva> buscarPorResponsavel(String responsavel) {
        return repository.buscarPorResponsavel(responsavel);
    }

    public List<String> listarResponsaveis() {
        return repository.listarResponsaveis();
    }
}
