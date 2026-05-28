package gerenciamento.quadra.frameworks.controller;


import gerenciamento.quadra.frameworks.dto.ReservaRelatorioDTO;
import gerenciamento.quadra.frameworks.model.Reserva;
import gerenciamento.quadra.frameworks.service.QuadraService;
import gerenciamento.quadra.frameworks.service.ReservaService;
import gerenciamento.quadra.frameworks.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    public String listar(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model){

        Page<Reserva> reservas = reservaService.listarPaginado(page, size);

        model.addAttribute("reservasPage", reservas);

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


    @GetMapping("/exportar-pdf")
    public void exportarPdf(HttpServletResponse response)
            throws IOException, DocumentException {

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=reservas.pdf"
        );

        List<ReservaRelatorioDTO> reservas =
                reservaService.gerarRelatorioReservas();

        Document document = new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );

        document.open();

        Font titulo = FontFactory.getFont(
                FontFactory.HELVETICA_BOLD,
                18
        );

        Paragraph tituloRelatorio =
                new Paragraph("Relatório de Reservas", titulo);

        tituloRelatorio.setAlignment(Element.ALIGN_CENTER);

        document.add(tituloRelatorio);

        document.add(new Paragraph(" "));

        PdfPTable tabela = new PdfPTable(3);

        tabela.setWidthPercentage(100);


        tabela.addCell("Quadra");
        tabela.addCell("Horário");
        tabela.addCell("Serviços");

        for (ReservaRelatorioDTO reserva : reservas) {

            tabela.addCell(reserva.getQuadra());
            tabela.addCell(reserva.getHorario());
            tabela.addCell(reserva.getServicos());
        }

        document.add(tabela);

        document.close();
    }

    @GetMapping("/buscar-tipo")
    public String buscarTipo(@RequestParam(required = false) String esporte, Model model) {
        model.addAttribute("esportes", quadraService.listarEsportes());
        if (esporte != null && !esporte.isEmpty()) {
            model.addAttribute("reservas", reservaService.buscarPorTipoQuadra(esporte));
        }

        return "reservas/busca-tipo";
    }

    @GetMapping("/buscar-data")
    public String buscarData(@RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fim, Model model) {
        if (inicio != null && fim != null) {
            model.addAttribute("reservas", reservaService.buscarEntreDatas(inicio, fim));
        }
        return "reservas/busca-data";
    }

    @GetMapping("/buscar-cliente")
    public String buscarCliente(@RequestParam(required = false) String responsavel, Model model) {
        model.addAttribute("responsaveis", reservaService.listarResponsaveis());
        if (responsavel != null && !responsavel.isEmpty()) {
            model.addAttribute("reservas", reservaService.buscarPorResponsavel(responsavel));
        }
        return "reservas/busca-cliente";
    }
}
