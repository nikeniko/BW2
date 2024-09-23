package Back_end.BW2.repositories;

import Back_end.BW2.entities.Fatture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FattureRepository extends JpaRepository<Fatture, UUID> {
}
