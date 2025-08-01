package ec.edu.ups.paw.appMedica.dao;

import java.util.Date;
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
	
	public List<Cita> getCitaPorIdPaciente(Integer idPaciente) {
	    String jpql = "SELECT c FROM Cita c WHERE c.paciente.id = :idPaciente";
	    TypedQuery<Cita> q = em.createQuery(jpql, Cita.class);
	    q.setParameter("idPaciente", idPaciente);
	    return q.getResultList();   
	}
	
	public List<Cita> findByFilters(
		    Integer pacienteId,
		    Integer medicoId,
		    Date from,
		    Date to,
		    Integer especialidadId,
		    String estado
		) {
		    StringBuilder jpql = new StringBuilder("SELECT c FROM Cita c WHERE 1=1");
		    if (pacienteId != null)      jpql.append(" AND c.paciente.id = :pid");
		    if (medicoId   != null)      jpql.append(" AND c.medico.id = :mid");
		    if (from        != null)     jpql.append(" AND c.fecha   >= :from");
		    if (to          != null)     jpql.append(" AND c.fecha   <= :to");
		    if (especialidadId != null)  jpql.append(" AND c.especialidad.id = :esp");
		    if (estado      != null && !estado.isBlank()) jpql.append(" AND c.estado = :est");
		    
		    TypedQuery<Cita> q = em.createQuery(jpql.toString(), Cita.class);
		    if (pacienteId   != null) q.setParameter("pid", pacienteId);
		    if (medicoId     != null) q.setParameter("mid", medicoId);
		    if (from          != null) q.setParameter("from", from);
		    if (to            != null) q.setParameter("to", to);
		    if (especialidadId != null) q.setParameter("esp", especialidadId);
		    if (estado        != null && !estado.isBlank()) q.setParameter("est", estado);
		    return q.getResultList();
		}
	
	public List<Cita> findByMedicoEspecialidadFecha(
            Integer medicoId, Integer especialidadId, Date desde, Date hasta) {

        String jpql = "SELECT c FROM Cita c "
                    + "WHERE c.medico.id = :medicoId "
                    + "  AND c.especialidad.id = :espId "
                    + "  AND c.fecha BETWEEN :desde AND :hasta "
                    + "ORDER BY c.fecha";

        TypedQuery<Cita> q = em.createQuery(jpql, Cita.class);
        q.setParameter("medicoId", medicoId);
        q.setParameter("espId", especialidadId);
        q.setParameter("desde", desde);
        q.setParameter("hasta", hasta);
        return q.getResultList();
    }


}
