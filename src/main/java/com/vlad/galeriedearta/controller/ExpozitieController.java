package com.vlad.galeriedearta.controller;

import com.vlad.galeriedearta.model.Expozitie;
import com.vlad.galeriedearta.service.ExpozitieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/expozitii")
public class ExpozitieController {

    private final ExpozitieService expozitieService;

    @Autowired
    public ExpozitieController(ExpozitieService expozitieService) {
        this.expozitieService = expozitieService;
    }

    // Init Binder pentru conversia Date din String
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // Afișează lista de expoziții
    @GetMapping
    public String getAllExpozitii(Model model) {
        List<Expozitie> expozitii = expozitieService.getAllExpozitii();
        model.addAttribute("expozitii", expozitii);
        return "expozitie/index";
    }

    // Afișează formularul pentru a adăuga o expoziție nouă
    @GetMapping("/new")
    public String showAddExpozitieForm(Model model) {
        model.addAttribute("expozitie", new Expozitie());
        return "expozitie/add-expozitie";
    }

    // Salvează o expoziție nouă
    @PostMapping
    public String addExpozitie(@ModelAttribute("expozitie") Expozitie expozitie) {
        expozitieService.addExpozitie(expozitie);
        return "redirect:/expozitii";
    }

    // Afișează formularul pentru a edita o expoziție existentă
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Expozitie expozitie = expozitieService.getExpozitieById(id);
        model.addAttribute("expozitie", expozitie);
        return "expozitie/edit-expozitie";
    }

    // Actualizează o expoziție existentă
    @PostMapping("/update/{id}")
    public String updateExpozitie(@PathVariable("id") int id, @ModelAttribute("expozitie") Expozitie expozitie) {
        expozitie.setExpozitieID(id);
        expozitieService.updateExpozitie(expozitie);
        return "redirect:/expozitii";
    }

    // Șterge o expoziție după ID
    @GetMapping("/delete/{id}")
    public String deleteExpozitie(@PathVariable("id") int id) {
        expozitieService.deleteExpozitie(id);
        return "redirect:/expozitii";
    }
}
