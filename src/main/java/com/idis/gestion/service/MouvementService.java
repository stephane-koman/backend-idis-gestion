package com.idis.gestion.service;

import com.idis.gestion.entities.Employe;
import com.idis.gestion.entities.Facture;
import com.idis.gestion.entities.Mouvement;
import com.idis.gestion.service.pagination.PageFacture;
import com.idis.gestion.service.pagination.PageMouvement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface MouvementService {
    public Facture saveFacture(Facture f);

    public Facture updateFacture(Facture f);

    public PageFacture listFacture(
            String numero,
            String referenceColis,
            String nomTypeFacture,
            String codeSite,
            int enable,
            Pageable pageable
    );

    List<Facture> findAllFactures(Employe employe, int enable);

    List<Facture> findFacturesByNumeroFacture(String numeroFacture, Employe employe, int enable);

    public Facture getFactureByNumeroFacture(String numeroFacture);

    public void disableFacture(Long id, Date date);
    public void enableFacture(Long id, Date date);
    public void removeFactureById(Long id);

    public JasperPrint exportFacturePdf(String numeroFacture) throws SQLException;
}
