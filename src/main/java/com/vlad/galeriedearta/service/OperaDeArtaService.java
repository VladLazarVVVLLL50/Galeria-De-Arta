package com.vlad.galeriedearta.service;

import com.vlad.galeriedearta.dao.OperaDeArtaDAO;
import com.vlad.galeriedearta.model.OperaDeArta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperaDeArtaService {

    private final OperaDeArtaDAO operaDeArtaDAO;

    @Autowired
    public OperaDeArtaService(OperaDeArtaDAO operaDeArtaDAO) {
        this.operaDeArtaDAO = operaDeArtaDAO;
    }

    // Adaugă o operă de artă
    public void addOperaDeArta(OperaDeArta operaDeArta) {
        operaDeArtaDAO.insertOperaDeArta(operaDeArta);
    }

    // Găsește o operă de artă după ID
    public OperaDeArta getOperaDeArtaById(int id) {
        return operaDeArtaDAO.selectOperaDeArtaById(id);
    }

    // Obține toate operele de artă
    public List<OperaDeArta> getAllOpereDeArta() {
        return operaDeArtaDAO.selectAllOpereDeArta();
    }

    // Actualizează o operă de artă
    public void updateOperaDeArta(OperaDeArta operaDeArta) {
        operaDeArtaDAO.updateOperaDeArta(operaDeArta);
    }

    public List<OperaDeArta> getOpereByArtistId(int artistID) {
        return operaDeArtaDAO.getOpereByArtistId(artistID);
    }

    // Șterge o operă de artă după ID
    public void deleteOperaDeArta(int id) {
        operaDeArtaDAO.deleteOperaDeArta(id);
    }
}
