package com.vlad.galeriedearta.service;

import com.vlad.galeriedearta.dao.ArtistDAO;
import com.vlad.galeriedearta.dao.OperaDeArtaDAO;
import com.vlad.galeriedearta.model.OperaDeArta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperaDeArtaService {

    private final OperaDeArtaDAO operaDeArtaDAO;
    private final ArtistDAO artistDAO; // Adăugăm artistDAO

    @Autowired
    public OperaDeArtaService(OperaDeArtaDAO operaDeArtaDAO, ArtistDAO artistDAO) {
        this.operaDeArtaDAO = operaDeArtaDAO;
        this.artistDAO = artistDAO; // Injectăm artistDAO
    }

    // Adaugă o operă de artă
    public void addOperaDeArta(OperaDeArta operaDeArta) {
        operaDeArtaDAO.insertOperaDeArta(operaDeArta);
    }

    // Găsește o operă de artă după ID
    public OperaDeArta getOperaDeArtaById(Long id) {
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

    public List<OperaDeArta> getOpereByArtistId(Long artistID) {
        return operaDeArtaDAO.getOpereByArtistId(artistID);
    }

    // Șterge o operă de artă după ID
    public void deleteOperaDeArta(Long id) {
        operaDeArtaDAO.deleteOperaDeArta(id);
    }

    public OperaDeArta getOperaDeArtaByTitlu(String titlu) {
        return operaDeArtaDAO.getOperaDeArtaByTitlu(titlu);
    }

    public List<OperaDeArta> getOpereDisponibilePentruArtist(Long artistID) {
        // Apelăm metoda pe instanța artistDAO
        List<Integer> opereAsociate = artistDAO.getOperaIdsByArtistId(artistID);
        return operaDeArtaDAO.getOpereNeasociate(opereAsociate);
    }

    // Găsește o operă de artă după ID
    public OperaDeArta findById(Long id) {
        // Implementarea metodei pentru a găsi o operă de artă după ID
        return operaDeArtaDAO.selectOperaDeArtaById((long) id.intValue());
    }

    public List<OperaDeArta> findAllById(List<Long> ids) {
        return ids.stream().map(this::findById).collect(Collectors.toList());
    }
}
