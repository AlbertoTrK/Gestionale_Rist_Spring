package org.elis.progettoristorante.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.PiattoDTO;
import org.elis.progettoristorante.dto.PiattoDTOResponse;
import org.elis.progettoristorante.model.Ingrediente;
import org.elis.progettoristorante.model.Piatto;
import org.springframework.stereotype.Component;

@Component
public class PiattoMapper {
	
	public PiattoDTO toPiattoDTO(Piatto p) {
		PiattoDTO piattoDTO = new PiattoDTO();
		piattoDTO.setNome(p.getNome());
		piattoDTO.setDescrizione(p.getDescrizione());
		piattoDTO.setPrezzo(p.getPrezzo());
		piattoDTO.setId_categoria(p.getCategoria().getId());
		piattoDTO.setId_ristorante(p.getRistorante().getId());
		
		List<Long> idIngredienti = p.getIngredienti_piatto().stream()
        .map(Ingrediente::getId)
        .collect(Collectors.toList());
		
		piattoDTO.setId_ingredienti(idIngredienti);
		
		return piattoDTO;
	}
	
	public List<PiattoDTO> toPiattoDTOList(List<Piatto> piatti){
		return piatti.stream().map(this::toPiattoDTO).toList();
	}
	
	public PiattoDTOResponse toPiattoDTOResponse(Piatto p) {
		PiattoDTOResponse piattoDTOResponse = new PiattoDTOResponse();
		piattoDTOResponse.setNome(p.getNome());
		piattoDTOResponse.setDescrizione(p.getDescrizione());
		piattoDTOResponse.setPrezzo(p.getPrezzo());
		piattoDTOResponse.setNomeCategoria(p.getCategoria().getNome());
		piattoDTOResponse.setNomeRistorante(p.getRistorante().getNome());
		
		
		List<String> nomiIngredienti = p.getIngredienti_piatto().stream()
        .map(Ingrediente::getNome)
        .collect(Collectors.toList());
		
		piattoDTOResponse.setNomiIngredienti(nomiIngredienti);
		
		return piattoDTOResponse;
	}
	
	public List<PiattoDTOResponse> toPiattoDTOResponseList(List<Piatto> piatti){
		return piatti.stream().map(this::toPiattoDTOResponse).toList();
	}
	
}
