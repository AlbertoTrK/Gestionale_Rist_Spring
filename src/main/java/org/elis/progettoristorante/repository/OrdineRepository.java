package org.elis.progettoristorante.repository;

import java.util.List;

import org.elis.progettoristorante.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine, Long>{
	public List<Ordine> findAllByClienteId(long idUtente);
	public List<Ordine> findAllByRistoranteId(long idRistorante);
	public Ordine findByRistoranteId(long idRistorante);
}
