package org.elis.progettoristorante.repository;

import java.util.List;

import org.elis.progettoristorante.model.Piatto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PiattoRepository extends JpaRepository<Piatto, Long> {
	public List<Piatto> findAllPiattoByRistorante_id(long idRistorante);
	public Piatto findPiattoByRistorante_id(long idRistorante);
	
//	public List<Piatto> findAllPiattoByIngredienteId(long idIngrediente);
//	public List<Piatto> findAllPiattoById(long idIngrediente);
	
	@Query("select p from Piatto p join p.ingredienti_piatto i where i.id = :idIngrediente")
	public List<Piatto> findAllPiattoByIngredienteId(@Param("idIngrediente") long idIngrediente);
	public Piatto findPiattoByNome(String nome);
//	public Piatto findById(long idPiatto);
}