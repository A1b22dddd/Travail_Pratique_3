module csi.travail_pratique_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Ouvre les packages contenant des FXML ou des contrôleurs
    opens csi.travail_pratique_3 to javafx.fxml;
    opens csi.travail_pratique_3.utilitaire to javafx.fxml;
    opens csi.travail_pratique_3.controleur to javafx.fxml;

    // Exporte les packages utilisés ailleurs
    exports csi.travail_pratique_3;
    exports csi.travail_pratique_3.utilitaire;
    exports csi.travail_pratique_3.controleur;
}
