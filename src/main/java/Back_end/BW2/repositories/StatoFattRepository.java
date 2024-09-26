package Back_end.BW2.repositories;

import Back_end.BW2.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatoFattRepository extends JpaRepository<StatoFattura, UUID> {

    @Query("SELECT s FROM StatoFattura s WHERE s IN :lista ORDER BY s.id ASC")
    StatoFattura findFirstInList(@Param("lista") List<StatoFattura> lista);

    Optional<StatoFattura> findByStato(String stato);

}
