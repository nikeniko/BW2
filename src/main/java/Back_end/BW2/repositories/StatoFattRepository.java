package Back_end.BW2.repositories;

import Back_end.BW2.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatoFattRepository extends JpaRepository<StatoFattura, UUID> {

    StatoFattura findFirst(List<StatoFattura> statoFattura);
}
