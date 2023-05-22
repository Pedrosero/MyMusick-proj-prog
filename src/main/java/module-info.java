module proyectoprog.nuevoproy {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires java.xml.bind;
	requires java.desktop;
	requires javafx.base;

    opens util to java.xml.bind;
    opens proyectoprog.nuevoproy to javafx.fxml;
    
    exports proyectoprog.nuevoproy;
}
