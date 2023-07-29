package org.elis.progettoristorante.repository.custom;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.filtri.FiltraPiattoRequest;
import org.elis.progettoristorante.model.Piatto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.Tuple;
import org.elis.progettoristorante.model.Categoria;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Ingrediente;

@Repository
public class PiattoCustomRepository {
	
	@Autowired
	EntityManager manager;
	
	public List<Piatto> getPiattiFiltrati(FiltraPiattoRequest request){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
		Root<Piatto> root = query.from(Piatto.class);
		List<Predicate> pred = new ArrayList<>();
		if(request.getNome() != null) pred.add(builder.like(root.get("nome"), "%"+request.getNome()+"%")); 
		if(request.getNomeCategoria() != null) {
			Join<Piatto, Categoria> joinCat = root.join("categoria");
			pred.add(builder.like(joinCat.get("nome"), "%"+request.getNomeCategoria()+"%"));
			query.multiselect(root, joinCat);
		}
		if(request.getNomeRistorante() != null) {
			Join<Piatto, Ristorante> joinRist = root.join("ristorante");
			pred.add(builder.like(joinRist.get("nome"), "%"+request.getNomeRistorante()+"%"));
			query.multiselect(root, joinRist);
		}
		if(request.getNomeIngrediente() != null) {
			Join<Piatto, Ingrediente> joinIng = root.join("ingredienti_piatto");
			pred.add(builder.like(joinIng.get("nome"), "%"+request.getNomeIngrediente()+"%"));
			query.multiselect(root, joinIng);
		} 
		Predicate [] arrPred = pred.toArray(new Predicate[pred.size()]);
		query.where(arrPred);
		List<Tuple> result = manager.createQuery(query).getResultList();
		return result.stream().map(t->t.get(0, Piatto.class)).toList();
	}
}
