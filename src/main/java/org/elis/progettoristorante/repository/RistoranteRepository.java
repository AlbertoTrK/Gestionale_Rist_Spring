package org.elis.progettoristorante.repository;

import java.util.List;

import org.elis.progettoristorante.model.Ristorante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RistoranteRepository extends JpaRepository<Ristorante, Long>{
	public Ristorante findByUtenteId(long idUtente);
	public List<Ristorante> findAllByUtenteId(long idUtente);
}
