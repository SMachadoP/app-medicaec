package ec.edu.ups.paw.appMedica.dao;

import java.util.List;

import ec.edu.ups.paw.appMedica.model.Cita;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class CitaDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void insert(Cita cita) {
		em.persist(cita);
	}
	
	public void update(Cita cita) {
		em.merge(cita);
	}
	
	public Cita read(int id) {
		Cita cita = em.find(Cita.class, id);
		return cita;
	}
	
	public void delete(int id) {
		Cita cita = this.read(id);
		em.remove(cita);
	}
	
	public List<Cita> getAll(){
		String jpql = "SELECT c FROM Cita c";
		TypedQuery<Cita> q = em.createQuery(jpql, Cita.class);
		return q.getResultList();
	}

}
