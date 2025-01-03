package com.vlad.galeriedearta.dao;

import com.vlad.galeriedearta.model.Expozitie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExpozitieDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpozitieDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert expozitie
    public void insertExpozitie(Expozitie expozitie) {
        String sql = "INSERT INTO expozitie (nume, descriere, dataInceput, dataSfarsit, locatieID, curatorID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, expozitie.getNume(), expozitie.getDescriere(),
                expozitie.getDataInceput(), expozitie.getDataSfarsit(),
                expozitie.getLocatieID(), expozitie.getCuratorID());
    }

    // Select expozitie by ID
    public Expozitie selectExpozitieById(int id) {
        String sql = "SELECT * FROM expozitie WHERE expozitieID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ExpozitieRowMapper());
    }

    // Select all expozitii
    public List<Expozitie> selectAllExpozitii() {
        String sql = "SELECT * FROM expozitie";
        return jdbcTemplate.query(sql, new ExpozitieRowMapper());
    }

    // Update expozitie
    public void updateExpozitie(Expozitie expozitie) {
        String sql = "UPDATE expozitie SET nume = ?, descriere = ?, dataInceput = ?, dataSfarsit = ?, locatieID = ?, curatorID = ? WHERE expozitieID = ?";
        jdbcTemplate.update(sql, expozitie.getNume(), expozitie.getDescriere(),
                expozitie.getDataInceput(), expozitie.getDataSfarsit(),
                expozitie.getLocatieID(), expozitie.getCuratorID(), expozitie.getExpozitieID());
    }

    // Delete expozitie
    public void deleteExpozitie(int id) {
        String sql = "DELETE FROM expozitie WHERE expozitieID = ?";
        jdbcTemplate.update(sql, id);
    }





    // Custom RowMapper for mapping ResultSet to Expozitie object
    private static class ExpozitieRowMapper implements RowMapper<Expozitie> {
        @Override
        public Expozitie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Expozitie expozitie = new Expozitie();
            expozitie.setExpozitieID(rs.getInt("expozitieID"));
            expozitie.setNume(rs.getString("nume"));
            expozitie.setDescriere(rs.getString("descriere"));
            expozitie.setDataInceput(rs.getDate("dataInceput"));
            expozitie.setDataSfarsit(rs.getDate("dataSfarsit"));
            expozitie.setLocatieID(rs.getInt("locatieID"));
            expozitie.setCuratorID(rs.getInt("curatorID"));
            return expozitie;
        }
    }
}
