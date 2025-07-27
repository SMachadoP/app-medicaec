package ec.edu.ups.paw.appMedica.dao;

import java.util.List;

import ec.edu.ups.paw.appMedica.model.Especialidad;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Stateless
public class EspecialidadDAO {

	
	@PersistenceContext
	private EntityManager em;
	
	public void insert(Especialidad especialidad) {
		em.persist(especialidad);
	}
	
	public void update(Especialidad especialidad) {
		em.merge(especialidad);
	}
	
	public Especialidad read(int id) {
		Especialidad especialidad = em.find(Especialidad.class, id);
		return especialidad;
	}
	
	public void delete(int id) {
		Especialidad especialidad = this.read(id);
		em.remove(especialidad);
	}
	
	public List<Especialidad> getAll(){
		String jpql = "SELECT e FROM Especialidad e";
		TypedQuery<Especialidad> q = em.createQuery(jpql, Especialidad.class);
		return q.getResultList();
	}

	

}
