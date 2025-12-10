package co.edu.sena.tu_unidad.domain.enums;

public enum ServiceType {

    servicio_tecnico("ST"),  // Servicio Técnico
    lectura_contador("LC"),
    diagnostico("STD"),
    toma_contador("TC"),
    correctivo("C"),
    toner("TN"),
    remoto("R"),  // Agregado
    otro("O");  // Por defecto

    private final String codigo;

    ServiceType(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
