package com.vlad.galeriedearta.controller;

import com.vlad.galeriedearta.model.Artist;
import com.vlad.galeriedearta.model.OperaDeArta;
import com.vlad.galeriedearta.service.ArtistService;
import com.vlad.galeriedearta.service.OperaDeArtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/opere")
public class OperaDeArtaController {

    private final OperaDeArtaService operaDeArtaService;
    private final ArtistService artistService;

    @Autowired
    public OperaDeArtaController(OperaDeArtaService operaDeArtaService, ArtistService artistService) {
        this.operaDeArtaService = operaDeArtaService;
        this.artistService = artistService;
    }

    // Init Binder pentru conversia Date din String
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Afișează lista de opere de artă
    @GetMapping
    public String getAllOpereDeArta(Model model) {
        List<OperaDeArta> opere = operaDeArtaService.getAllOpereDeArta();

        Map<Long, List<Artist>> artistsByOpera = new HashMap<>();
        for (OperaDeArta opera : opere) {
            List<Artist> artists = artistService.getArtistsByOperaId(opera.getOperaDeArtaID());
            artistsByOpera.put(opera.getOperaDeArtaID(), artists);
        }

        model.addAttribute("opere", opere);
        model.addAttribute("artistsByOpera", artistsByOpera);
        return "opera/index";
    }

    // Afișează formularul pentru a adăuga o operă de artă nouă
    @GetMapping("/new")
    public String showAddOperaForm(Model model) {
        model.addAttribute("opera", new OperaDeArta());
        model.addAttribute("artisti", artistService.getAllArtists());
        return "opera/add-opera";
    }

    // Salvează o operă de artă nouă
    @PostMapping
    public String addOperaDeArta(@ModelAttribute("opera") OperaDeArta operaDeArta) {
        operaDeArtaService.addOperaDeArta(operaDeArta);
        return "redirect:/opere";
    }

    // Afișează formularul pentru a edita o operă de artă existentă
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        OperaDeArta operaDeArta = operaDeArtaService.getOperaDeArtaById(id);
        model.addAttribute("opera", operaDeArta);
        model.addAttribute("artisti", artistService.getAllArtists());
        return "opera/edit-opera";
    }

    // Actualizează o operă de artă existentă
    @PostMapping("/update/{id}")
    public String updateOperaDeArta(@PathVariable("id") Long id, @ModelAttribute("opera") OperaDeArta operaDeArta) {
        operaDeArta.setOperaDeArtaID(id);
        operaDeArtaService.updateOperaDeArta(operaDeArta);
        return "redirect:/opere";
    }

    // Șterge o operă de artă după ID
    @GetMapping("/delete/{id}")
    public String deleteOperaDeArta(@PathVariable("id") Long id) {
        operaDeArtaService.deleteOperaDeArta(id);
        return "redirect:/opere";
    }

    // Afișează operele asociate unui artist
    @GetMapping("/artist/{id}")
    public String getOpereByArtist(@PathVariable("id") Long artistID, Model model) {
        List<OperaDeArta> opere = operaDeArtaService.getOpereByArtistId(artistID);
        model.addAttribute("opere", opere);
        model.addAttribute("artistID", artistID); // Trimitem și ID-ul artistului pentru context
        System.out.println("Număr de opere pentru artistID=" + artistID + ": " + opere.size());

        return "opera/artist";
    }
}
