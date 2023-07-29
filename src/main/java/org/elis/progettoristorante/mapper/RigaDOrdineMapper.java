package org.elis.progettoristorante.mapper;

import java.util.List;

import org.elis.progettoristorante.dto.RigaDOrdineDTOResponse;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.springframework.stereotype.Component;

@Component
public class RigaDOrdineMapper {
	
	public RigaDOrdineDTOResponse toRigaDOrdineDTOResponse(RigaDOrdine riga) {
		
		RigaDOrdineDTOResponse rdoDTOResponse = new RigaDOrdineDTOResponse();
		rdoDTOResponse.setIdOrdine(riga.getOrdine().getId());
		rdoDTOResponse.setNomePiatto(riga.getPiatto().getNome());
		rdoDTOResponse.setQuantità(riga.getQuantità());
		return rdoDTOResponse;
	}
	
	public List<RigaDOrdineDTOResponse> toRigaDOrdineDTOResponseList(List<RigaDOrdine> righe){
		return righe.stream().map(this::toRigaDOrdineDTOResponse).toList();
	}

}
