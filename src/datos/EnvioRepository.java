package datos;

import java.util.List;
import entidades.Envio;

public interface EnvioRepository {
    void add(Envio envio);
    boolean removeByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Envio> findAll();
}
