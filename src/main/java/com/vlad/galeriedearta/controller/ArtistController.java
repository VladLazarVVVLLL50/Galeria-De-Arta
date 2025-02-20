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
import java.util.List;

@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final OperaDeArtaService operaDeArtaService;

    @Autowired
    public ArtistController(ArtistService artistService, OperaDeArtaService operaDeArtaService) {
        this.artistService = artistService;
        this.operaDeArtaService = operaDeArtaService;
    }

    // Init Binder pentru conversia Date din String
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Afișează lista de artiști
    @GetMapping
    public String getAllArtists(Model model) {
        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("artists", artists);
        return "artist/index";
    }

    // Afișează formularul pentru a adăuga un artist nou
    @GetMapping("/new")
    public String showAddArtistForm(Model model) {
        model.addAttribute("artist", new Artist());
        model.addAttribute("opere", operaDeArtaService.getAllOpereDeArta());
        return "artist/add-artist";
    }

    // Salvează un artist nou
    @PostMapping("/add")
    public String addArtist(@ModelAttribute("artist") Artist artist, @RequestParam(value = "opereIds", required = false) List<Long> opereIds, Model model) {
        try {
            artist.setOpereDeArta(opereIds != null ? opereIds : List.of());
            artistService.addArtist(artist);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "artist/add-artist";
        }
        return "redirect:/artists";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Artist artist = artistService.getArtistById(id);
        List<OperaDeArta> opereDisponibile = operaDeArtaService.getAllOpereDeArta();
        List<Long> selectedOpere = artist.getOpereDeArta() != null ? artist.getOpereDeArta() : List.of();
        model.addAttribute("artist", artist);
        model.addAttribute("opereDisponibile", opereDisponibile);
        model.addAttribute("selectedOpere", selectedOpere);
        return "artist/edit-artist";
    }

    @PostMapping("/update/{id}")
    public String updateArtist(@PathVariable("id") Long id, @ModelAttribute("artist") Artist artist, @RequestParam(value = "opere", required = false) List<Long> opereIds, Model model) {
        try {
            artist.setArtistID(id);
            artist.setOpereDeArta(opereIds != null ? opereIds : List.of());
            artistService.updateArtist(artist);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "artist/edit-artist";
        }
        return "redirect:/artists";
    }

    // Șterge un artist după ID
    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable("id") int id) {
        artistService.deleteArtist(id);
        return "redirect:/artists";
    }

    // Afișează detalii pentru un artist
    @GetMapping("/{id}")
    public String getArtistDetails(@PathVariable("id") Long id, Model model) {
        Artist artist = artistService.getArtistById(id);
        model.addAttribute("artist", artist);

        // Obține operele asociate artistului
        List<OperaDeArta> opere = operaDeArtaService.getOpereByArtistId(id);
        System.out.println("Opere pentru artist ID " + id + ": " + opere);
        model.addAttribute("opere", opere);

        return "artist/details"; // Redirecționează către pagina details.html
    }

    @PostMapping("/update")
    public String updateArtist(@ModelAttribute("artist") Artist artist, @RequestParam List<Long> addOpereIds, @RequestParam List<Long> deleteOpereIds) {
        // Adaugă operele selectate la artist
        for (Long operaId : addOpereIds) {
            OperaDeArta opera = operaDeArtaService.findById(operaId);
            artist.getOpereDeArta().add(opera.getOperaDeArtaID());
        }

        // Elimină operele selectate din artist
        for (Long operaId : deleteOpereIds) {
            OperaDeArta opera = operaDeArtaService.findById(operaId);
            artist.getOpereDeArta().remove(opera);
        }

        artistService.save(artist);
        return "redirect:/artists";
    }
}