module com.gamecastle {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.gamecastle to javafx.fxml, com.google.gson;
    opens com.gamecastle.Models to com.google.gson;
    opens com.gamecastle.Management to com.google.gson;
    opens com.gamecastle.Controllers to javafx.fxml, com.google.gson;

    exports com.gamecastle;
    exports com.gamecastle.Models;
    exports com.gamecastle.Management;
    exports com.gamecastle.Controllers;
}
