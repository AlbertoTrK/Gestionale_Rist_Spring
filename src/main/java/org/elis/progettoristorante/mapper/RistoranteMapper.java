package org.elis.progettoristorante.mapper;

import java.util.List;

import org.elis.progettoristorante.dto.RistoranteDto;
import org.elis.progettoristorante.model.Ristorante;
import org.springframework.stereotype.Component;

@Component
public class RistoranteMapper {
	
	public RistoranteDto toRistDTO(Ristorante r) {
		RistoranteDto risDTO = new RistoranteDto();
		risDTO.setNome(r.getNome());
		return risDTO;
	}
	
	public List<RistoranteDto> toRistoranteDTOList(List<Ristorante> ristoranti){
		return ristoranti.stream().map(this::toRistDTO).toList();
	}

}
