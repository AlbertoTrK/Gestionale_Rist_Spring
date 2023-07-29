package org.elis.progettoristorante.mapper;

import java.util.List;

import org.elis.progettoristorante.dto.CategoriaDTO;
import org.elis.progettoristorante.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
	
	public CategoriaDTO toCategoriaDTO(Categoria c) {
		CategoriaDTO cDTO = new CategoriaDTO();
		cDTO.setNome(c.getNome());
		cDTO.setPiattiInCategoria(c.getPiatti());
		return cDTO;
	}
	
	public List<CategoriaDTO> toCategoriaDTOList(List<Categoria> categorie){
		return categorie.stream().map(this::toCategoriaDTO).toList();
	}

}
