package com.vlad.galeriedearta.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StatisticiDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatisticiDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getStatistica1() {
        String sql = "SELECT COUNT(*) AS totalArtists FROM Artist";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getStatistica2() {
        String sql = "SELECT AVG(YEAR(CURDATE()) - YEAR(dataNastere)) AS averageAge FROM Artist";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getStatistica3() {
        String sql = "SELECT nationalitate, COUNT(*) AS count FROM Artist GROUP BY nationalitate";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getStatistica4() {
        String sql = "SELECT a.nume, COUNT(o.operaDeArtaID) AS opereCount " +
                "FROM Artist a " +
                "JOIN Artist_OperaDeArta ao ON a.artistID = ao.artistID " +
                "JOIN OperaDeArta o ON ao.operaDeArtaID = o.operaDeArtaID " +
                "GROUP BY a.nume";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getArtistWithMostArtworks() {
        String sql = "SELECT a.nume, COUNT(o.operaDeArtaID) AS opereCount " +
                "FROM Artist a " +
                "JOIN Artist_OperaDeArta ao ON a.artistID = ao.artistID " +
                "JOIN OperaDeArta o ON ao.operaDeArtaID = o.operaDeArtaID " +
                "GROUP BY a.nume " +
                "ORDER BY opereCount DESC " +
                "LIMIT 1";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getExhibitionsWithMostArtworks() {
        String sql = "SELECT e.nume, COUNT(o.operaDeArtaID) AS opereCount " +
                "FROM Expozitie e " +
                "JOIN Expozitie_OperaDeArta eo ON e.expozitieID = eo.expozitieID " +
                "JOIN OperaDeArta o ON eo.operaDeArtaID = o.operaDeArtaID " +
                "GROUP BY e.nume " +
                "ORDER BY opereCount DESC";
        return jdbcTemplate.queryForList(sql);
    }

    // complexa 1
    public List<Map<String, Object>> getMostRecentArtworkByArtist() {
        String sql = "SELECT a.nume, a.prenume, ( " +
                "    SELECT o.titlu " +
                "    FROM OperaDeArta o " +
                "    JOIN Artist_OperaDeArta ao ON o.operaDeArtaID = ao.operaDeArtaID " +
                "    WHERE ao.artistID = a.artistID " +
                "    ORDER BY o.dataCreare DESC " +
                "    LIMIT 1 " +
                ") AS newestArtwork " +
                "FROM Artist a";
        return jdbcTemplate.queryForList(sql);
    }


    // complexa 2
    public List<Map<String, Object>> getTotalArtworksPerArtist() {
        String sql = "SELECT a.nume, a.prenume, ( " +
                "    SELECT COUNT(*) " +
                "    FROM Artist_OperaDeArta " +
                "    WHERE artistID = a.artistID " +
                ") AS opereCount " +
                "FROM Artist a";
        return jdbcTemplate.queryForList(sql);
    }

    // complexa 3
    public List<Map<String, Object>> getArtistsByArtworkType(String artworkType) {
        String sql = "SELECT DISTINCT a.nume, a.prenume " +
                "FROM Artist a " +
                "WHERE artistID IN ( " +
                "    SELECT artistID " +
                "    FROM Artist_OperaDeArta " +
                "    WHERE operaDeArtaID IN ( " +
                "        SELECT operaDeArtaID " +
                "        FROM OperaDeArta " +
                "        WHERE tip = ? " +
                "    ) " +
                ")";
        return jdbcTemplate.queryForList(sql, artworkType);
    }

    // complexa 4
    public List<Map<String, Object>> getOldestArtworkByArtist() {
        String sql = "SELECT a.nume, a.prenume, ( " +
                "    SELECT MIN(dataCreare) " +
                "    FROM OperaDeArta o " +
                "    JOIN Artist_OperaDeArta ao ON o.operaDeArtaID = ao.operaDeArtaID " +
                "    WHERE ao.artistID = a.artistID " +
                ") AS oldestArtwork " +
                "FROM Artist a";
        return jdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> getCuratorWithMostExhibitions() {
        String sql = "SELECT c.nume, COUNT(e.expozitieID) AS expozitiiCount " +
                "FROM Curator c " +
                "JOIN Expozitie e ON c.curatorID = e.curatorID " +
                "GROUP BY c.nume " +
                "ORDER BY expozitiiCount DESC " +
                "LIMIT 1";
        return jdbcTemplate.queryForList(sql);
    }
}