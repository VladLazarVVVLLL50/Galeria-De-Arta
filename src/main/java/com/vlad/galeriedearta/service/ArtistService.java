package com.vlad.galeriedearta.service;

import com.vlad.galeriedearta.dao.ArtistDAO;
import com.vlad.galeriedearta.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistDAO artistDAO;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArtistService(ArtistDAO artistDAO, JdbcTemplate jdbcTemplate) {
        this.artistDAO = artistDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Adaugă un artist
    public void addArtist(Artist artist) {
        if (artistDAO.emailExists(artist.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + artist.getEmail());
        }
        if (artistDAO.phoneNumberExists(artist.getNumarTelefon())) {
            throw new IllegalArgumentException("Phone number already exists: " + artist.getNumarTelefon());
        }
        artistDAO.insertArtist(artist);
    }

    // Găsește un artist după ID
    public Artist getArtistById(Long id) {
        return artistDAO.selectArtistById(id);
    }

    // Obține toți artiștii
    public List<Artist> getAllArtists() {
        return artistDAO.selectAllArtists();
    }

    // Actualizează un artist
    public void updateArtist(Artist artist) {
        Artist existingArtist = artistDAO.getArtistById(artist.getArtistID());

        if (existingArtist == null) {
            throw new IllegalArgumentException("Artist not found: " + artist.getArtistID());
        }

        if (artist.getEmail() != null && (existingArtist.getEmail() == null || !existingArtist.getEmail().equals(artist.getEmail())) && artistDAO.emailExists(artist.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + artist.getEmail());
        } else {
            existingArtist.setEmail(artist.getEmail());
        }

        if (artist.getNumarTelefon() != null && (existingArtist.getNumarTelefon() == null || !existingArtist.getNumarTelefon().equals(artist.getNumarTelefon())) && artistDAO.phoneNumberExists(artist.getNumarTelefon())) {
            throw new IllegalArgumentException("Phone number already exists: " + artist.getNumarTelefon());
        } else {
            existingArtist.setNumarTelefon(artist.getNumarTelefon());
        }

        if (!existingArtist.getNume().equals(artist.getNume())) {
            existingArtist.setNume(artist.getNume());
        }
        if (!existingArtist.getPrenume().equals(artist.getPrenume())) {
            existingArtist.setPrenume(artist.getPrenume());
        }
        if (!existingArtist.getDataNastere().equals(artist.getDataNastere())) {
            existingArtist.setDataNastere(artist.getDataNastere());
        }
        if (!existingArtist.getNationalitate().equals(artist.getNationalitate())) {
            existingArtist.setNationalitate(artist.getNationalitate());
        }
        if (!existingArtist.getBiografie().equals(artist.getBiografie())) {
            existingArtist.setBiografie(artist.getBiografie());
        }

        if (existingArtist.getParola() == null || !existingArtist.getParola().equals(artist.getParola())) {
            existingArtist.setParola(artist.getParola());
        }

        existingArtist.setOpereDeArta(artist.getOpereDeArta() != null ? artist.getOpereDeArta() : List.of());

        artistDAO.updateArtist(existingArtist);
    }

    // Șterge un artist după ID
    public void deleteArtist(int id) {
        artistDAO.deleteArtist(id);
    }

    public List<Artist> getArtistsByOperaId(Long operaDeArtaID) {
        return artistDAO.getArtistsByOperaId(operaDeArtaID);
    }

    public void addArtistWithOpere(Artist artist, List<Long> opereIDs) {
        artistDAO.insertArtist(artist);
        Long artistID = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        artistDAO.addOpereToArtist(artistID, opereIDs);
    }

    public void updateArtistWithOpere(Artist artist, List<Long> opereIDs) {
        // Actualizează artistul
        artistDAO.updateArtist(artist);

        // Obține operele curente asociate artistului
        List<Integer> opereCurente = artistDAO.getOperaIdsByArtistId(artist.getArtistID());

        // Determină operele noi și operele de șters
        List<Long> opereNoi = opereIDs.stream()
                .filter(operaID -> !opereCurente.contains(operaID))
                .toList();

        List<Integer> opereDeSters = opereCurente.stream()
                .filter(operaID -> !opereIDs.contains(operaID))
                .toList();

        // Adaugă operele noi
        if (!opereNoi.isEmpty()) {
            artistDAO.addOpereToArtist(artist.getArtistID(), opereNoi);
        }

        // Șterge operele care nu mai sunt asociate
        if (!opereDeSters.isEmpty()) {
            artistDAO.removeOpereFromArtist(artist.getArtistID(), opereDeSters);
        }
    }

    public void addOpereToArtist(Long artistID, List<Long> opereIDs) {
        artistDAO.addOpereToArtist(artistID, opereIDs);
    }

    public void save(Artist artist) {
        if (artist.getArtistID() == 0) {
            artistDAO.insertArtist(artist);
        } else {
            artistDAO.updateArtist(artist);
        }
    }
}
