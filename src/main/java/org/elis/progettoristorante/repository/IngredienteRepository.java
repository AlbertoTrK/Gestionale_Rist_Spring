package org.elis.progettoristorante.repository;

import java.util.List;

import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Piatto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>{
//	@Query("select ing from Ingrediente ing join ing.ingredienti_piatto piatto where piatto.id = :idPiatto")
//	public List<Ingrediente> findAllIngredienteByPiattoId(@Param("idPiatto") long idPiatto);
	
	public List<Ingrediente> findAllIngredienteByPiattoId(long idPiatto);
	public Ingrediente findIngredienteByNome(String nome);
}
