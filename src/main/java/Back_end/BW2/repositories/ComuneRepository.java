package Back_end.BW2.repositories;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Comune findByNomeAndProvincia(String nome, Provincia provincia);
}
