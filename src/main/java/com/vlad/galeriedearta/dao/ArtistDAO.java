package com.vlad.galeriedearta.dao;

import com.vlad.galeriedearta.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

        // Get the generated artist ID
        Long artistID = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // Insert associated artworks
        String insertOpereSql = "INSERT INTO artist_operadearta (artistID, operaDeArtaID) VALUES (?, ?)";
        for (Long operaId : artist.getOpereDeArta()) {
            jdbcTemplate.update(insertOpereSql, artistID, operaId);
        }
    }

    // Select artist by ID
    public Artist selectArtistById(Long id) {
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
        jdbcTemplate.update(sql, artist.getNume(), artist.getPrenume(), artist.getDataNastere(), artist.getDataDeces(), artist.getNationalitate(), artist.getBiografie(), artist.getEmail(), artist.getNumarTelefon(), artist.getParola(), artist.getArtistID());

        // Delete existing artist's artworks
        String deleteOpereSql = "DELETE FROM artist_operadearta WHERE artistID = ?";
        jdbcTemplate.update(deleteOpereSql, artist.getArtistID());

        // Add new artworks for the artist
        String insertOpereSql = "INSERT INTO artist_operadearta (artistID, operaDeArtaID) VALUES (?, ?)";
        for (Long operaId : artist.getOpereDeArta()) {
            jdbcTemplate.update(insertOpereSql, artist.getArtistID(), operaId);
        }
    }

    // Delete artist
    public void deleteArtist(int id) {
        String sql = "DELETE FROM artist WHERE artistID = ?";
        jdbcTemplate.update(sql, id);
    }

    public Artist getArtistById(Long id) {
        String sql = "SELECT * FROM artist WHERE artistID = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, rs -> {
            if (rs.next()) {
                Artist artist = new Artist();
                artist.setArtistID(rs.getLong("artistID"));
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
            return null;
        });
    }

    public List<Artist> getArtistsByOperaId(Long operaDeArtaID) {
        String sql = """
                    SELECT a.* FROM Artist a
                    INNER JOIN Artist_OperaDeArta ao ON a.artistID = ao.artistID
                    WHERE ao.operaDeArtaID = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{operaDeArtaID}, (rs, rowNum) -> {
            Artist artist = new Artist();
            artist.setArtistID(rs.getLong("artistID"));
            artist.setNume(rs.getString("nume"));
            artist.setPrenume(rs.getString("prenume"));
            artist.setDataNastere(rs.getDate("dataNastere"));
            artist.setNationalitate(rs.getString("nationalitate"));
            return artist;
        });
    }

    public void addOpereToArtist(Long artistID, List<Long> opereIDs) {
        String sql = "INSERT INTO Artist_OperaDeArta (artistID, operaDeArtaID) VALUES (?, ?)";
        for (Long operaID : opereIDs) {
            jdbcTemplate.update(sql, artistID, operaID);
        }
    }

    public void deleteOpereFromArtist(Long artistID) {
        String sql = "DELETE FROM Artist_OperaDeArta WHERE artistID = ?";
        jdbcTemplate.update(sql, artistID);
    }

    public List<Integer> getOperaIdsByArtistId(Long artistID) {
        String sql = "SELECT operaDeArtaID FROM Artist_OperaDeArta WHERE artistID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, artistID);
    }

    public void removeOpereFromArtist(Long artistID, List<Integer> opereDeSters) {
        String sql = "DELETE FROM Artist_OperaDeArta WHERE artistID = ? AND operaDeArtaID = ?";
        for (Integer operaID : opereDeSters) {
            jdbcTemplate.update(sql, artistID, operaID);
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM artist WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean phoneNumberExists(String numarTelefon) {
        String sql = "SELECT COUNT(*) FROM artist WHERE numarTelefon = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, numarTelefon);
        return count != null && count > 0;
    }

    // Custom RowMapper for mapping ResultSet to Artist object
    private static class ArtistRowMapper implements RowMapper<Artist> {
        @Override
        public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Artist artist = new Artist();
            artist.setArtistID(rs.getLong("artistID"));
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
