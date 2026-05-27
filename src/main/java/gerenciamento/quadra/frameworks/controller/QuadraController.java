package gerenciamento.quadra.frameworks.controller;

import gerenciamento.quadra.frameworks.model.Quadra;
import gerenciamento.quadra.frameworks.service.QuadraService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/quadras")
public class QuadraController {

    @Autowired
    private QuadraService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("quadras", service.listarTodas());
        return "quadras/list";
    }

    @GetMapping("/nova")
    public String novaQuadra(Model model) {
        model.addAttribute("quadra", new Quadra());
        return "quadras/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Quadra quadra) {
        service.salvar(quadra);
        return "redirect:/quadras";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("quadra", service.buscarPorId(id));
        return "quadras/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/quadras";
    }
}
