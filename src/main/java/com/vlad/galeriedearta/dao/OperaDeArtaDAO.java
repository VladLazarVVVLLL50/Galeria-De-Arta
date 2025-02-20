package com.vlad.galeriedearta.dao;

import com.vlad.galeriedearta.model.OperaDeArta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OperaDeArtaDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OperaDeArtaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert opera de arta
    public void insertOperaDeArta(OperaDeArta operaDeArta) {
        String sql = "INSERT INTO OperaDeArta (titlu, tip, dimensiune, dataCreare) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, operaDeArta.getTitlu(), operaDeArta.getTip(),
                operaDeArta.getDimensiune(), operaDeArta.getDataCreare());

        Long operaDeArtaID = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        insertOperaArtists(operaDeArtaID, operaDeArta.getArtisti());
    }

    // Insert artists for an opera
    private void insertOperaArtists(Long operaDeArtaID, List<Long> artisti) {
        String sql = "INSERT INTO Artist_OperaDeArta (operaDeArtaID, artistID) VALUES (?, ?)";
        for (Long artistID : artisti) {
            jdbcTemplate.update(sql, operaDeArtaID, artistID);
        }
    }

    // Select opera de arta by ID
    public OperaDeArta selectOperaDeArtaById(Long id) {
        String sql = "SELECT * FROM OperaDeArta WHERE operaDeArtaID = ?";
        OperaDeArta operaDeArta = jdbcTemplate.queryForObject(sql, new Object[]{id}, new OperaDeArtaRowMapper());
        operaDeArta.setArtisti(selectArtistsByOperaId(id));
        return operaDeArta;
    }

    // Select artists by opera ID
    private List<Long> selectArtistsByOperaId(Long operaDeArtaID) {
        String sql = "SELECT artistID FROM Artist_OperaDeArta WHERE operaDeArtaID = ?";
        return jdbcTemplate.query(sql, new Object[]{operaDeArtaID}, (rs, rowNum) -> rs.getLong("artistID"));
    }

    // Delete artists for an opera
    private void deleteOperaArtists(Long operaDeArtaID) {
        String sql = "DELETE FROM Artist_OperaDeArta WHERE operaDeArtaID = ?";
        jdbcTemplate.update(sql, operaDeArtaID);
    }

    // Select all opere de arta
    public List<OperaDeArta> selectAllOpereDeArta() {
        String sql = "SELECT * FROM OperaDeArta";
        return jdbcTemplate.query(sql, new OperaDeArtaRowMapper());
    }

    // Update opera de arta
    public void updateOperaDeArta(OperaDeArta operaDeArta) {
        String sql = "UPDATE OperaDeArta SET titlu = ?, tip = ?, dimensiune = ?, dataCreare = ? WHERE operaDeArtaID = ?";
        jdbcTemplate.update(sql, operaDeArta.getTitlu(), operaDeArta.getTip(),
                operaDeArta.getDimensiune(), operaDeArta.getDataCreare(), operaDeArta.getOperaDeArtaID());

        deleteOperaArtists(operaDeArta.getOperaDeArtaID());
        insertOperaArtists(operaDeArta.getOperaDeArtaID(), operaDeArta.getArtisti());
    }

    // Delete opera de arta
    public void deleteOperaDeArta(Long id) {
        String sql = "DELETE FROM OperaDeArta WHERE operaDeArtaID = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<OperaDeArta> getOpereByArtistId(Long artistID) {
        String sql = """
                    SELECT o.* FROM OperaDeArta o
                    INNER JOIN Artist_OperaDeArta ao ON o.operaDeArtaID = ao.operaDeArtaID
                    WHERE ao.artistID = ?
                """;
        List<OperaDeArta> opere = jdbcTemplate.query(sql, new Object[]{artistID}, (rs, rowNum) -> {
            OperaDeArta opera = new OperaDeArta();
            opera.setOperaDeArtaID(rs.getLong("operaDeArtaID"));
            opera.setTitlu(rs.getString("titlu"));
            opera.setTip(rs.getString("tip"));
            opera.setDimensiune(rs.getString("dimensiune"));
            opera.setDataCreare(rs.getDate("dataCreare"));
            return opera;
        });
        System.out.println(" DAO Opere asociate artistului " + artistID + ": " + opere);
        return opere;
    }

    public OperaDeArta getOperaDeArtaByTitlu(String titlu) {
        String sql = "SELECT * FROM OperaDeArta WHERE titlu = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{titlu}, new OperaDeArtaRowMapper());
    }


    // Custom RowMapper for mapping ResultSet to OperaDeArta object
    private static class OperaDeArtaRowMapper implements RowMapper<OperaDeArta> {
        @Override
        public OperaDeArta mapRow(ResultSet rs, int rowNum) throws SQLException {
            OperaDeArta operaDeArta = new OperaDeArta();
            operaDeArta.setOperaDeArtaID(rs.getLong("operaDeArtaID"));
            operaDeArta.setTitlu(rs.getString("titlu"));
            operaDeArta.setTip(rs.getString("tip"));
            operaDeArta.setDimensiune(rs.getString("dimensiune"));
            operaDeArta.setDataCreare(rs.getDate("dataCreare"));
            return operaDeArta;
        }
    }

    public List<OperaDeArta> getOpereNeasociate(List<Integer> opereAsociate) {
        if (opereAsociate == null || opereAsociate.isEmpty()) {
            // Dacă lista este goală, returnăm toate operele
            String sql = "SELECT * FROM OperaDeArta";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                OperaDeArta operaDeArta = new OperaDeArta();
                operaDeArta.setOperaDeArtaID(rs.getLong("operaDeArtaID"));
                operaDeArta.setTitlu(rs.getString("titlu"));
                operaDeArta.setTip(rs.getString("tip"));
                operaDeArta.setDimensiune(rs.getString("dimensiune"));
                operaDeArta.setDataCreare(rs.getDate("dataCreare"));
                return operaDeArta;
            });
        }

        // Construim șirul de ID-uri separate prin virgulă
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < opereAsociate.size(); i++) {
            inClause.append(opereAsociate.get(i));
            if (i < opereAsociate.size() - 1) {
                inClause.append(",");
            }
        }

        // Construim interogarea SQL
        String sql = "SELECT * FROM OperaDeArta WHERE operaDeArtaID NOT IN (" + inClause.toString() + ")";

        // Executăm interogarea și mapăm manual rezultatele
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            OperaDeArta operaDeArta = new OperaDeArta();
            operaDeArta.setOperaDeArtaID(rs.getLong("operaDeArtaID"));
            operaDeArta.setTitlu(rs.getString("titlu"));
            operaDeArta.setTip(rs.getString("tip"));
            operaDeArta.setDimensiune(rs.getString("dimensiune"));
            operaDeArta.setDataCreare(rs.getDate("dataCreare"));
            return operaDeArta;
        });
    }
}
