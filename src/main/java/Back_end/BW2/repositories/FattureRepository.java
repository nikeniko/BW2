package Back_end.BW2.repositories;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.entities.Fattura;
import Back_end.BW2.entities.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FattureRepository extends JpaRepository<Fattura, UUID> {

    @Query("SELECT MAX(f.numeroFattura) FROM Fattura f")
    Optional<Long> findMaxNumeroFattura();

    List<Fattura> findByCliente(Cliente cliente);

    List<Fattura> findByStatoFattura(StatoFattura statoFattura);

    List<Fattura> findByData(LocalDate date);

    @Query("SELECT f FROM Fattura f WHERE EXTRACT(YEAR FROM f.data) = :anno")
    List<Fattura> findByAnno(@Param("anno") int anno);

    @Query("SELECT f FROM Fattura f WHERE f.importo BETWEEN :minImporto AND :maxImporto")
    List<Fattura> findByImportoBetween(@Param("minImporto") Double minImporto, @Param("maxImporto") Double maxImporto);

}
