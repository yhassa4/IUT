package mvc.vues.batiment;

import java.awt.Container;

import mvc.controleurs.ControleurBatiment;

public interface InterfaceVueBatiment {
    void redessiner();
    void setEcouteur(ControleurBatiment controleurBatiment);
    Container getPanel();
}
