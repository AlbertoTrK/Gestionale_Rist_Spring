package org.elis.progettoristorante.repository;

import org.elis.progettoristorante.model.RigaDOrdine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RigaDOrdineRepository extends JpaRepository<RigaDOrdine, Long>{
//	public void save(long idPiatto, long idOrdine, int quantità);
}
