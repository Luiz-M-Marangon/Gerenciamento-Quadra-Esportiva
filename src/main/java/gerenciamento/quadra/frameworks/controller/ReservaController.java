package gerenciamento.quadra.frameworks.controller;


import gerenciamento.quadra.frameworks.model.Reserva;
import gerenciamento.quadra.frameworks.service.QuadraService;
import gerenciamento.quadra.frameworks.service.ReservaService;
import gerenciamento.quadra.frameworks.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private QuadraService quadraService;

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "reservas/list";
    }

    @GetMapping("/nova")
    public String novaReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("quadras", quadraService.listarTodas());
        model.addAttribute("servicos", servicoService.listarTodos());
        return "reservas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Reserva reserva, Model model){
        try {
            reservaService.salvar(reserva);
            return "redirect:/reservas";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("quadras", quadraService.listarTodas());
            model.addAttribute("servicos", servicoService.listarTodos());
            return "reservas/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", reservaService.buscarPorId(id));
        model.addAttribute("quadras", quadraService.listarTodas());
        model.addAttribute("servicos", servicoService.listarTodos());
        return "reservas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        reservaService.excluir(id);
        return "redirect:/reservas";
    }
}
