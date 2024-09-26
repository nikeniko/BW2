package Back_end.BW2.repositories;

import Back_end.BW2.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClientiRepository extends JpaRepository<Cliente, UUID> {

    List<Cliente> findByFatturatoAnnuale(int fatturatoAnnuale);

    List<Cliente> findByDataInserimento(LocalDate dataInserimento);

    List<Cliente> findByDataUltimoContatto(LocalDate dataUltimoContatto);


}
