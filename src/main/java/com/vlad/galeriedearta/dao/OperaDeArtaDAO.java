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
    }

    // Select opera de arta by ID
    public OperaDeArta selectOperaDeArtaById(int id) {
        String sql = "SELECT * FROM OperaDeArta WHERE operaDeArtaID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new OperaDeArtaRowMapper());
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
    }

    // Delete opera de arta
    public void deleteOperaDeArta(int id) {
        String sql = "DELETE FROM OperaDeArta WHERE operaDeArtaID = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<OperaDeArta> getOpereByArtistId(int artistID) {
        String sql = """
        SELECT o.* FROM OperaDeArta o
        INNER JOIN Artist_OperaDeArta ao ON o.operaDeArtaID = ao.operaDeArtaID
        WHERE ao.artistID = ?
    """;
        List<OperaDeArta> opere = jdbcTemplate.query(sql, new Object[]{artistID}, (rs, rowNum) -> {
            OperaDeArta opera = new OperaDeArta();
            opera.setOperaDeArtaID(rs.getInt("operaDeArtaID"));
            opera.setTitlu(rs.getString("titlu"));
            opera.setTip(rs.getString("tip"));
            opera.setDimensiune(rs.getString("dimensiune"));
            opera.setDataCreare(rs.getDate("dataCreare"));
            return opera;
        });

        return opere;
    }



    // Custom RowMapper for mapping ResultSet to OperaDeArta object
    private static class OperaDeArtaRowMapper implements RowMapper<OperaDeArta> {
        @Override
        public OperaDeArta mapRow(ResultSet rs, int rowNum) throws SQLException {
            OperaDeArta operaDeArta = new OperaDeArta();
            operaDeArta.setOperaDeArtaID(rs.getInt("operaDeArtaID"));
            operaDeArta.setTitlu(rs.getString("titlu"));
            operaDeArta.setTip(rs.getString("tip"));
            operaDeArta.setDimensiune(rs.getString("dimensiune"));
            operaDeArta.setDataCreare(rs.getDate("dataCreare"));
            return operaDeArta;
        }
    }
}
