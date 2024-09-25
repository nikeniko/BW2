package Back_end.BW2.repositories;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComuneRepository extends JpaRepository<Comune, UUID> {

    List<Comune> findByNome(String nome);

    Comune findByNomeAndProvincia(String nome, Provincia provincia);
}
