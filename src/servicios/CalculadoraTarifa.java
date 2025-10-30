package servicios;

import entidades.Envio;
import entidades.MedioTransporte;

public interface CalculadoraTarifa {
    double calcular(double pesoKg, double distanciaKm, MedioTransporte medio);
}
