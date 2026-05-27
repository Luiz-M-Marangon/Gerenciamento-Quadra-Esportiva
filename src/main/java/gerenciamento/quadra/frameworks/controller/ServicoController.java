package gerenciamento.quadra.frameworks.controller;

import gerenciamento.quadra.frameworks.model.Servico;
import gerenciamento.quadra.frameworks.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicos", service.listarTodos());
        return "servicos/list";
    }

    @GetMapping("/novo")
    public String novoServico(Model model) {
        model.addAttribute("servico", new Servico());
        return "servicos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Servico servico) {
        service.salvar(servico);
        return "redirect:/servicos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("servico", service.buscarPorId(id));
        return "servicos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/servicos";
    }
}
