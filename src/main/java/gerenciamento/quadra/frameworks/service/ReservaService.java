package gerenciamento.quadra.frameworks.service;

import gerenciamento.quadra.frameworks.model.Reserva;
import gerenciamento.quadra.frameworks.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public List<Reserva> listarTodas() {
        return repository.findAll();
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
            throw new RuntimeException("Já existe reserva nesse horário.");
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
}
