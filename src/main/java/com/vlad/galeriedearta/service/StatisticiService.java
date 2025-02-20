package com.vlad.galeriedearta.service;

import com.vlad.galeriedearta.dao.StatisticiDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticiService {

    private final StatisticiDAO statisticiDAO;

    @Autowired
    public StatisticiService(StatisticiDAO statisticiDAO) {
        this.statisticiDAO = statisticiDAO;
    }

    public List<Map<String, Object>> getStatistica1() {
        return statisticiDAO.getStatistica1();
    }

    public List<Map<String, Object>> getStatistica2() {
        return statisticiDAO.getStatistica2();
    }

    public List<Map<String, Object>> getStatistica3() {
        return statisticiDAO.getStatistica3();
    }

    public List<Map<String, Object>> getStatistica4() {
        return statisticiDAO.getStatistica4();
    }

    public List<Map<String, Object>> getArtistWithMostArtworks() {
        return statisticiDAO.getArtistWithMostArtworks();
    }

    public List<Map<String, Object>> getExhibitionsWithMostArtworks() {
        return statisticiDAO.getExhibitionsWithMostArtworks();
    }

    public List<Map<String, Object>> getMostRecentArtworkByArtist(){
        return statisticiDAO.getMostRecentArtworkByArtist();
    }


    public List<Map<String, Object>> getTotalArtworksPerArtist() {
        return statisticiDAO.getTotalArtworksPerArtist();
    }

    public List<Map<String, Object>> getArtistsByArtworkType(String artworkType) {
        return statisticiDAO.getArtistsByArtworkType(artworkType);
    }

    public List<Map<String, Object>> getOldestArtworkByArtist() {
        return statisticiDAO.getOldestArtworkByArtist();
    }


    public List<Map<String, Object>> getCuratorWithMostExhibitions() {
        return statisticiDAO.getCuratorWithMostExhibitions();
    }
}