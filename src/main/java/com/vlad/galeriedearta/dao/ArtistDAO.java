package com.vlad.galeriedearta.dao;

import com.vlad.galeriedearta.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.vlad.galeriedearta.model.OperaDeArta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArtistDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArtistDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert artist
    public void insertArtist(Artist artist) {
        String sql = "INSERT INTO artist (nume, prenume, dataNastere, dataDeces, nationalitate, biografie, email, numarTelefon, parola) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, artist.getNume(), artist.getPrenume(),
                artist.getDataNastere(), artist.getDataDeces(), artist.getNationalitate(),
                artist.getBiografie(), artist.getEmail(), artist.getNumarTelefon(), artist.getParola());
    }

    // Select artist by ID
    public Artist selectArtistById(int id) {
        String sql = "SELECT * FROM artist WHERE artistID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ArtistRowMapper());
    }

    // Select all artists
    public List<Artist> selectAllArtists() {
        String sql = "SELECT * FROM artist";
        return jdbcTemplate.query(sql, new ArtistRowMapper());
    }

    // Update artist
    public void updateArtist(Artist artist) {
        String sql = "UPDATE artist SET nume = ?, prenume = ?, dataNastere = ?, dataDeces = ?, nationalitate = ?, biografie = ?, email = ?, numarTelefon = ?, parola = ? WHERE artistID = ?";
        jdbcTemplate.update(sql, artist.getNume(), artist.getPrenume(),
                artist.getDataNastere(), artist.getDataDeces(), artist.getNationalitate(),
                artist.getBiografie(), artist.getEmail(), artist.getNumarTelefon(), artist.getParola(),
                artist.getArtistID());
    }

    // Delete artist
    public void deleteArtist(int id) {
        String sql = "DELETE FROM artist WHERE artistID = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Artist> getArtistsByOperaId(int operaDeArtaID) {
        String sql = """
        SELECT a.* FROM Artist a
        INNER JOIN Artist_OperaDeArta ao ON a.artistID = ao.artistID
        WHERE ao.operaDeArtaID = ?
    """;
        return jdbcTemplate.query(sql, new Object[]{operaDeArtaID}, (rs, rowNum) -> {
            Artist artist = new Artist();
            artist.setArtistID(rs.getInt("artistID"));
            artist.setNume(rs.getString("nume"));
            artist.setPrenume(rs.getString("prenume"));
            artist.setDataNastere(rs.getDate("dataNastere"));
            artist.setNationalitate(rs.getString("nationalitate"));
            return artist;
        });
    }





    // Custom RowMapper for mapping ResultSet to Artist object
    private static class ArtistRowMapper implements RowMapper<Artist> {
        @Override
        public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Artist artist = new Artist();
            artist.setArtistID(rs.getInt("artistID"));
            artist.setNume(rs.getString("nume"));
            artist.setPrenume(rs.getString("prenume"));
            artist.setDataNastere(rs.getDate("dataNastere"));
            artist.setDataDeces(rs.getDate("dataDeces"));
            artist.setNationalitate(rs.getString("nationalitate"));
            artist.setBiografie(rs.getString("biografie"));
            artist.setEmail(rs.getString("email"));
            artist.setNumarTelefon(rs.getString("numarTelefon"));
            artist.setParola(rs.getString("parola"));
            return artist;
        }
    }
}
