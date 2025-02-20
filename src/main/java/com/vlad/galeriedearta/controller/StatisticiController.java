package com.vlad.galeriedearta.controller;

import com.vlad.galeriedearta.service.StatisticiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticiController {

    private final StatisticiService statisticiService;

    @Autowired
    public StatisticiController(StatisticiService statisticiService) {
        this.statisticiService = statisticiService;
    }

    @GetMapping("/statistici")
    public String getStatistici(Model model) {
        model.addAttribute("statistica1", statisticiService.getStatistica1());
        model.addAttribute("statistica2", statisticiService.getStatistica2());
        model.addAttribute("statistica3", statisticiService.getStatistica3());
        model.addAttribute("statistica4", statisticiService.getStatistica4());
        model.addAttribute("artistWithMostArtworks", statisticiService.getArtistWithMostArtworks());
        model.addAttribute("exhibitionsWithMostArtworks", statisticiService.getExhibitionsWithMostArtworks());

        model.addAttribute("mostRecentArtworkByArtist", statisticiService.getMostRecentArtworkByArtist());
        model.addAttribute("totalArtworksPerArtist", statisticiService.getTotalArtworksPerArtist());
        model.addAttribute("artistsByArtworkType", statisticiService.getArtistsByArtworkType("Pictura"));
        model.addAttribute("oldestArtworkByArtist", statisticiService.getOldestArtworkByArtist());

        model.addAttribute("curatorWithMostExhibitions", statisticiService.getCuratorWithMostExhibitions());
        return "statistici";
    }
}