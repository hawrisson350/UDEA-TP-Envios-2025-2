package entidades;

public class Envio {
    private String codigo;
    private String cliente;
    private double pesoKg;
    private double distanciaKm;
    private MedioTransporte medio;
    private double tarifa;

    public Envio(String codigo, String cliente, double pesoKg, double distanciaKm, MedioTransporte medio, double tarifa) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.pesoKg = pesoKg;
        this.distanciaKm = distanciaKm;
        this.medio = medio;
        this.tarifa = tarifa;
    }

    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public double getPesoKg() { return pesoKg; }
    public double getDistanciaKm() { return distanciaKm; }
    public MedioTransporte getMedio() { return medio; }
    public double getTarifa() { return tarifa; }

    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }
    public void setMedio(MedioTransporte medio) { this.medio = medio; }
    public void setTarifa(double tarifa) { this.tarifa = tarifa; }
}
