package com.vlad.galeriedearta.service;

import com.vlad.galeriedearta.dao.ArtistDAO;
import com.vlad.galeriedearta.model.Artist;
import com.vlad.galeriedearta.model.OperaDeArta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class ArtistService {

    private final ArtistDAO artistDAO;

    @Autowired
    public ArtistService(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    // Adaugă un artist
    public void addArtist(Artist artist) {
        artistDAO.insertArtist(artist);
    }

    // Găsește un artist după ID
    public Artist getArtistById(int id) {
        return artistDAO.selectArtistById(id);
    }

    // Obține toți artiștii
    public List<Artist> getAllArtists() {
        return artistDAO.selectAllArtists();
    }

    // Actualizează un artist
    public void updateArtist(Artist artist) {
        artistDAO.updateArtist(artist);
    }

    // Șterge un artist după ID
    public void deleteArtist(int id) {
        artistDAO.deleteArtist(id);
    }

    public List<Artist> getArtistsByOperaId(int operaDeArtaID) {
        return artistDAO.getArtistsByOperaId(operaDeArtaID);
    }



}
