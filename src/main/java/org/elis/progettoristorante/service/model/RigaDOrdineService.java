package org.elis.progettoristorante.service.model;

import org.elis.progettoristorante.model.RigaDOrdine;

public interface RigaDOrdineService {
	public void save(long idPiatto, long idOrdine, int quantità);
	public RigaDOrdine findById(Long id);
}
