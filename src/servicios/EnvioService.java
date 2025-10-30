package servicios;

import java.util.List;
import java.util.UUID;

import datos.EnvioRepository;
import entidades.Envio;
import entidades.MedioTransporte;

public class EnvioService {

    private final EnvioRepository repo;
    private final CalculadoraTarifa calculadora;

    public EnvioService(EnvioRepository repo, CalculadoraTarifa calculadora) {
        this.repo = repo;
        this.calculadora = calculadora;
    }

    public void crearEnvio(String codigo, String cliente, String pesoStr, String distanciaStr, MedioTransporte medio) {
        // Validaciones amigables (para 3er semestre)
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        double peso = parsePositivo(pesoStr, "peso (kg)");
        double distancia = parsePositivo(distanciaStr, "distancia (km)");

        double tarifa = calculadora.calcular(peso, distancia, medio);
        String finalCodigo = (codigo == null || codigo.trim().isEmpty())
                ? UUID.randomUUID().toString().substring(0, 8)
                : codigo.trim();

        // validar unicidad
        if (repo.existsByCodigo(finalCodigo)) {
            throw new IllegalArgumentException("Ya existe un envío con ese código.");
        }

        Envio envio = new Envio(finalCodigo, cliente.trim(), peso, distancia, medio, tarifa);
        repo.add(envio);
    }

    public boolean eliminar(String codigo) {
        if (codigo == null || codigo.isBlank()) return false;
        return repo.removeByCodigo(codigo);
    }

    public List<Envio> listar() {
        return repo.findAll();
    }

    private double parsePositivo(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        try {
            double valor = Double.parseDouble(texto.trim().replace(',', '.'));
            if (valor <= 0) {
                throw new IllegalArgumentException("El " + nombreCampo + " debe ser mayor que cero.");
            }
            return valor;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser numérico.");
        }
    }
}
