package com.vlad.galeriedearta.service;

import com.vlad.galeriedearta.dao.ExpozitieDAO;
import com.vlad.galeriedearta.model.Expozitie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpozitieService {

    private final ExpozitieDAO expozitieDAO;

    @Autowired
    public ExpozitieService(ExpozitieDAO expozitieDAO) {
        this.expozitieDAO = expozitieDAO;
    }

    // Adaugă o expoziție
    public void addExpozitie(Expozitie expozitie) {
        expozitieDAO.insertExpozitie(expozitie);
    }

    // Găsește o expoziție după ID
    public Expozitie getExpozitieById(int id) {
        return expozitieDAO.selectExpozitieById(id);
    }

    // Obține toate expozițiile
    public List<Expozitie> getAllExpozitii() {
        return expozitieDAO.selectAllExpozitii();
    }

    // Actualizează o expoziție
    public void updateExpozitie(Expozitie expozitie) {
        expozitieDAO.updateExpozitie(expozitie);
    }

    // Șterge o expoziție după ID
    public void deleteExpozitie(int id) {
        expozitieDAO.deleteExpozitie(id);
    }
}
