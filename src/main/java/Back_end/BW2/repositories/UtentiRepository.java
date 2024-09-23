package Back_end.BW2.repositories;

import Back_end.BW2.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);
    
}
