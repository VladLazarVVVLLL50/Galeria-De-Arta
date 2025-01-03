package com.vlad.galeriedearta.controller;
import com.vlad.galeriedearta.model.Artist;
import com.vlad.galeriedearta.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.vlad.galeriedearta.model.OperaDeArta;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import com.vlad.galeriedearta.service.OperaDeArtaService;
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
        return "artist/add-artist";
    }

    // Salvează un artist nou
    @PostMapping
    public String addArtist(@ModelAttribute("artist") Artist artist) {
        artistService.addArtist(artist);
        return "redirect:/artists";
    }

    // Afișează formularul pentru a edita un artist existent
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Artist artist = artistService.getArtistById(id);
        model.addAttribute("artist", artist);
        return "artist/edit-artist";
    }

    // Actualizează un artist existent
    @PostMapping("/update/{id}")
    public String updateArtist(@PathVariable("id") int id, @ModelAttribute("artist") Artist artist) {
        artist.setArtistID(id);
        artistService.updateArtist(artist);
        return "redirect:/artists";
    }

    // Șterge un artist după ID
    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable("id") int id) {
        artistService.deleteArtist(id);
        return "redirect:/artists";
    }

    @GetMapping("/{id}")
    public String getArtistById(@PathVariable("id") int id, Model model) {
        Artist artist = artistService.getArtistById(id);
        model.addAttribute("artist", artist);
        List<OperaDeArta> opere = operaDeArtaService.getOpereByArtistId(id);
        model.addAttribute("opere", opere);
        return "artist/details";
    }


}
