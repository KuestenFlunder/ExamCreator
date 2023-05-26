module com.team_kuestenflunder.exam_desktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.guice;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.pdfbox;



    opens com.team_kuestenflunder.exam_desktop to javafx.fxml;
    exports com.team_kuestenflunder.exam_desktop;
    exports com.team_kuestenflunder.exam_desktop.entity;
    exports com.team_kuestenflunder.exam_desktop.repository;
    exports com.team_kuestenflunder.exam_desktop.controller;
    exports com.team_kuestenflunder.exam_desktop.services;
    opens com.team_kuestenflunder.exam_desktop.controller to javafx.fxml;

}