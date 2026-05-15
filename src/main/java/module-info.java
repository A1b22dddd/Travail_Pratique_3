module csi.travail_pratique_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens csi.travail_pratique_3 to javafx.fxml;
    opens csi.travail_pratique_3.controleur to javafx.fxml;

    exports csi.travail_pratique_3;
}
