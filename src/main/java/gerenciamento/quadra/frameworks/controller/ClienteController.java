package gerenciamento.quadra.frameworks.controller;

import gerenciamento.quadra.frameworks.model.Cliente;
import gerenciamento.quadra.frameworks.service.QuadraService;
import gerenciamento.quadra.frameworks.service.ReservaService;
import gerenciamento.quadra.frameworks.service.ServicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import gerenciamento.quadra.frameworks.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.ArrayList;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private QuadraService quadraService;
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public String listarClientes(@RequestParam(defaultValue = "0")int page, Model model){
        Pageable pageable = PageRequest.of(page, 20, Sort.by("id").ascending());
        Page<Cliente> pagina = clienteService.listarPaginado(pegeable);
        model.addAttribute("clientes", pagina);
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novoCliente(Model model){
        Cliente cliente = new Cliente();
        cliente.setReservas(new ArrayList<>());
        model.addAttribute("cliente", cliente);
        model.addAttribute("quadras", quadraService.listarTodos());
        model.addAttribute("serviço", servicoService.listarTodos();
        model.addAttribute("reserva", reservaService.listarTodos());
        return "clientes/form";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Long id, Model model){
        Cliente cliente = clienteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        model.addAttribute("cliente", cliente);
        model.addAttribute("quadras", quadraService.listarTodos());
        model.addAttribute("serviço", servicoService.listarTodos());
        model.addAttribute("reserva", reservaService.listarTodos());
        return "clientes/form";
    }
}
