package org.elis.progettoristorante.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.AggiungiOrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.OrdineDTO;
import org.elis.progettoristorante.dto.OrdineResponseDTO;
import org.elis.progettoristorante.dto.RigaDOrdineDTOResponse;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.RigaDOrdine;
import org.elis.progettoristorante.model.Stato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdineMapper {
	
	@Autowired
	RigaDOrdineMapper rdoMapper;
	
	public OrdineResponseDTO toOrdineResponseDTO(Ordine o) {
		OrdineResponseDTO orDTO = new OrdineResponseDTO();
		orDTO.setIdOrdine(o.getId());
		orDTO.setUsername(o.getCliente().getUsername());
		orDTO.setDataOrdine(o.getDataInvio());
		orDTO.setNomeRistorante(o.getRistorante().getNome());
		orDTO.setStato(o.getStato());
		orDTO.setRigheDOrdine(rdoMapper.toRigaDOrdineDTOResponseList(o.getRigheDOrdine()));
		
		orDTO.setUsername(o.getCliente().getUsername());
		orDTO.setNomeRistorante(o.getRistorante().getNome());
		return orDTO;
	}
	
	public List<OrdineResponseDTO> toOrdineResponseDTOList(List<Ordine> ordini){
		return ordini.stream().map(this::toOrdineResponseDTO).toList();
	}

	public List<OrdineResponseDTO> toOrdineResponseDTOListEvaERif(List <Ordine> ordini){
		return ordini.stream()
				.filter(o->o.getStato() == Stato.EVASO || o.getStato() == Stato.RIFIUTATO)
				.map(this::toOrdineResponseDTO).toList();
	}
	
	public List<OrdineResponseDTO> toOrdineResponseDTOListNonEvaNonRif(List <Ordine> ordini){
		return ordini.stream()
				.filter(o->o.getStato() == Stato.RICEVUTO || o.getStato() == Stato.IN_ELABORAZIONE)
				.map(this::toOrdineResponseDTO).toList();
	}
	
//	public AggiungiOrdineResponseDTO toAggiungiOrdineResponseDTO(Ordine o) {
//		AggiungiOrdineResponseDTO aorDTO = new AggiungiOrdineResponseDTO();
//		aorDTO.setIdOrdine(o.getId());
//		aorDTO.setNomeRistorante(o.getRistorante().getNome());
//		aorDTO.setUsername(o.getCliente().getUsername());
//		List<Long> idRigheOrdine = o.getRigheDOrdine().stream().map(RigaDOrdine::getId)
//				.collect(Collectors.toList());
//		aorDTO.setRigheDOrdine(idRigheOrdine);
//	}

}
