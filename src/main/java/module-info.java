module com.kurz.javafxtarefas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.kurz.javafxtarefas to javafx.fxml;
    exports com.kurz.javafxtarefas;
}