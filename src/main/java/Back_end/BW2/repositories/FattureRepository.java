package Back_end.BW2.repositories;

import Back_end.BW2.entities.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FattureRepository extends JpaRepository<Fattura, UUID> {

    @Query("SELECT MAX(f.numeroFattura) FROM Fattura f")
    Optional<Long> findMaxNumeroFattura();
    
}
