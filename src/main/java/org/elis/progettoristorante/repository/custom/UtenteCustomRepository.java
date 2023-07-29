package org.elis.progettoristorante.repository.custom;

import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.filtri.FiltraUtentiRequest;
import org.elis.progettoristorante.model.Ristorante;
import org.elis.progettoristorante.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class UtenteCustomRepository {
	
	@Autowired
	EntityManager manager;
	
	public List<Utente> getUtentiFiltrati(FiltraUtentiRequest request){
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
		Root<Utente> root = query.from(Utente.class);
		
		List<Predicate> pred = new ArrayList<>();
		if(request.getNome() != null) pred.add(builder.like(root.get("nome"), "%"+request.getNome()+"%"));
		if(request.getCognome() != null) pred.add(builder.like(root.get("cognome"), "%"+request.getCognome()+"%"));
		if(request.getUsername() != null) pred.add(builder.like(root.get("username"), "%"+request.getUsername()+"%"));
		if(request.getNomeRistorante() != null) {
			Join<Utente, Ristorante> join = root.join("ristorante");
			pred.add(builder.like(join.get("nome"), "%"+request.getNomeRistorante()+"%"));
			query.multiselect(root, join);
		}
		Predicate[] arrPred = pred.toArray(new Predicate[pred.size()]);
		query.where(arrPred);
		List<Tuple> result  = manager.createQuery(query).getResultList();
		return result.stream().map(t->t.get(0, Utente.class)).toList();
	}

}
