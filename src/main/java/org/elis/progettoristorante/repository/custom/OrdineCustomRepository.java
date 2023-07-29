package org.elis.progettoristorante.repository.custom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.progettoristorante.filtri.FiltraOrdiniRequest;
import org.elis.progettoristorante.model.Ordine;
import org.elis.progettoristorante.model.Utente;
import org.elis.progettoristorante.model.Ristorante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Repository
public class OrdineCustomRepository {

	@Autowired
	EntityManager manager;
	
//	public List<Ordine> filtraOrdini(FiltraOrdiniRequest request){
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
//		Root<Ordine> root = query.from(Ordine.class);
//		List<Predicate> pred = new ArrayList<>();
//		if(request.getDataInvio() != null) pred.add(builder.between(root.get("dataInvio"),request.getDataInvio(),LocalDateTime.now()));
//		if(request.getNomeRistorante() != null) {
//			Join<Ordine, Ristorante> joinOrdRist = root.join("ristorante");
//			pred.add(builder.like(joinOrdRist.get("nome"), "%"+request.getNomeRistorante()+"%"));
//			query.multiselect(root, joinOrdRist);
//		}
//		if(request.getUsername() != null) {
//			Join<Ordine, Utente> joinOrdUtente = root.join("cliente");
//			pred.add(builder.like(joinOrdUtente.get("username"), "%"+request.getUsername()+"%"));
//			query.multiselect(root, joinOrdUtente);
//		}
//		Predicate[] arrPred = pred.toArray(new Predicate[pred.size()]);
////		query.where(arrPred);
//		List<Tuple> result  = manager.createQuery(query).getResultList();
//		return result.stream().map(t->t.get(0, Ordine.class)).toList();
//	}
}
