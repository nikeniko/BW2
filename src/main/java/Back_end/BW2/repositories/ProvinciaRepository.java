package Back_end.BW2.repositories;

import Back_end.BW2.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    Provincia findByNome(String sigla);
}
