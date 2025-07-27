package ec.edu.ups.paw.appMedica.dao;

import java.util.List;

import ec.edu.ups.paw.appMedica.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;


@Stateless
public class UsuarioDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void insert(Usuario usuario) {
		em.persist(usuario);
	}
	
	public void update(Usuario usuario) {
		em.merge(usuario);
	}
	
	public Usuario read(Integer id) {
		Usuario usuario = em.find(Usuario.class, id);
		return usuario;
	}
	
	public void delete(Integer id) {
		Usuario usuario = this.read(id);
		em.remove(usuario);
	}
	
	public List<Usuario> getAll(){
		String jpql = "SELECT u FROM Usuario u";
		TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
		return q.getResultList();
	}
	
	public List<Usuario> getUsuarioPorNombre(String nombre) {
	    String jpql = "SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE :nombre";
	    TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
	    q.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
	    return q.getResultList();
	}
	
	public List<Usuario> getUsuarioPorCorreo(String correo) {
	    String jpql = "SELECT u FROM Usuario u WHERE LOWER(u.correo) LIKE :correo";
	    TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
	    q.setParameter("correo", "%" + correo.toLowerCase() + "%");
	    return q.getResultList();
	}

	public List<Usuario> getUsuarioPorCedula(String cedula) {
	    String jpql = "SELECT u FROM Usuario u WHERE u.cedula LIKE :cedula";
	    TypedQuery<Usuario> q = em.createQuery(jpql, Usuario.class);
	    q.setParameter("cedula", cedula + "%");
	    return q.getResultList();
	}
	

}
