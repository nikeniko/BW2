package Back_end.BW2.repositories;

import Back_end.BW2.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {

    List<Provincia> findByNome(String nome);
}
