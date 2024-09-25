package Back_end.BW2.repositories;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, UUID> {
    List<Indirizzo> findByComune(Comune comune);
}
