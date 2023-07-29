package org.elis.progettoristorante.repository;

import java.util.List;

import org.elis.progettoristorante.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	public Categoria findCategoriaByNome(String nome);
}
