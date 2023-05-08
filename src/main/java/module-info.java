module com.team_kuestenflunder.exam_desktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.team_kuestenflunder.exam_desktop to javafx.fxml;
    exports com.team_kuestenflunder.exam_desktop;
}