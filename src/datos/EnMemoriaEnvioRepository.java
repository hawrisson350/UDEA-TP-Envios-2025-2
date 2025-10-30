package datos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Envio;

public class EnMemoriaEnvioRepository implements EnvioRepository {

    private final List<Envio> envios = new ArrayList<>();

    @Override
    public void add(Envio envio) {
        // evitar duplicados por código
        for (Envio e : envios) {
            if (e.getCodigo().equalsIgnoreCase(envio.getCodigo())) {
                throw new IllegalArgumentException("Ya existe un envío con ese código.");
            }
        }
        envios.add(envio);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        if (codigo == null) return false;
        for (Envio e : envios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        return false;
    }

    @Override
    public boolean removeByCodigo(String codigo) {
        Iterator<Envio> it = envios.iterator();
        while (it.hasNext()) {
            if (it.next().getCodigo().equalsIgnoreCase(codigo)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Envio> findAll() {
        return new ArrayList<>(envios);
    }
}
