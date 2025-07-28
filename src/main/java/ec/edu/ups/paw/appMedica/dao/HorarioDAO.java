package ec.edu.ups.paw.appMedica.dao;

import java.util.List;

import ec.edu.ups.paw.appMedica.model.Cita;
import ec.edu.ups.paw.appMedica.model.Horario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Stateless
public class HorarioDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void insert(Horario horario) {
		em.persist(horario);
	}
	
	public void update(Horario horario) {
		em.merge(horario);
	}
	
	public Horario read(int id) {
		Horario horario = em.find(Horario.class, id);
		return horario;
	}
	
	public void delete(int id) {
		Horario horario = this.read(id);
		em.remove(horario);
	}
	
	public List<Horario> getAll(){
		String jpql = "SELECT h FROM Horario h";
		TypedQuery<Horario> q = em.createQuery(jpql, Horario.class);
		return q.getResultList();
	}
	
	public List<Horario> getHorarioPorIdMedico(Integer idMedico) {
	    String jpql = "SELECT h FROM Horario h WHERE h.medico.id = :idMedico";
	    TypedQuery<Horario> q = em.createQuery(jpql, Horario.class);
	    q.setParameter("idMedico", idMedico);
	    return q.getResultList();   
	}
	
	
}
