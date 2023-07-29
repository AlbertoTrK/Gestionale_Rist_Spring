package org.elis.progettoristorante.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.elis.progettoristorante.dto.IngredienteDTO;
import org.elis.progettoristorante.model.Ingrediente;
import org.springframework.stereotype.Component;

@Component
public class IngredienteMapper {
	
	public IngredienteDTO toIngredienteDTO(Ingrediente i) {
		IngredienteDTO ingDTO = new IngredienteDTO();
		ingDTO.setNome(i.getNome());
		return ingDTO;
	}
	
	public List<IngredienteDTO> toIngredienteDTOList(List<Ingrediente> ingredienti){
		return ingredienti.stream().map(this::toIngredienteDTO).toList();
	}

}
