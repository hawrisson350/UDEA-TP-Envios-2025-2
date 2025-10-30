package servicios;

import entidades.MedioTransporte;

public class CalculadoraTarifaBase implements CalculadoraTarifa {

    private final double baseKmTerrestre = 1500;
    private final double baseKmAereo = 5000;
    private final double baseKmMaritimo = 800;

    private final double recargoKgTerrestre = 2000;
    private final double recargoKgAereo = 4000;
    private final double recargoKgMaritimo = 1000;

    @Override
    public double calcular(double pesoKg, double distanciaKm, MedioTransporte medio) {
        double total = 0;
        switch (medio) {
            case TERRESTRE:
                total = distanciaKm * baseKmTerrestre + pesoKg * recargoKgTerrestre;
                break;
            case AEREO:
                total = distanciaKm * baseKmAereo + pesoKg * recargoKgAereo;
                break;
            case MARITIMO:
                total = distanciaKm * baseKmMaritimo + pesoKg * recargoKgMaritimo;
                break;
        }
        return total;
    }
}
