package ec.edu.ups.appMedica.business;

import java.util.List;

import ec.edu.ups.paw.appMedica.dao.UsuarioDAO;
import ec.edu.ups.paw.appMedica.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ContactosON {

	@Inject
	private UsuarioDAO daoUsuario;
	
	public void guardarUsuario(Usuario u) {
		 if (u.getId() == null) {
		        daoUsuario.insert(u);
		    } else {
		        Usuario aux = daoUsuario.read(u.getId());
		        if (aux == null)
		            daoUsuario.insert(u);
		        else
		            daoUsuario.update(u);
		    }
	}

	public List<Usuario> getContactos(){
		return daoUsuario.getAll();
	}
	
	
	public Usuario getContacto(Integer id) throws Exception {
	    if (id == null || id <= 0) {
	        throw new Exception("ID incorrecto");
	    }
	    return daoUsuario.read(id);
	}
	
	

	public List<Usuario> getMedicoPorEspecialidad(Integer id) throws Exception {
	    if (id == null || id <= 0) {
	        throw new Exception("ID incorrecto");
	    }
	    return daoUsuario.getMedicoPorEspecialidad(id);
	}
	public List<Usuario> getContactoPorNombre(String nombre) throws Exception {
		if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Nombre inválido");
        }
		return daoUsuario.getUsuarioPorNombre(nombre);
	}
	
	public List<Usuario> getContactoPorCorreo(String correo) throws Exception {
		if (correo == null || correo.trim().isEmpty()) {
            throw new Exception("Correo inválido");
        }
		return daoUsuario.getUsuarioPorCorreo(correo);
	}

	public List<Usuario> getContactoPorCedula(String cedula) throws Exception {
		if (cedula.length() != 10) {
            throw new Exception("Cédula inválida");
        }
		return daoUsuario.getUsuarioPorCedula(cedula);
	}
	
	
	
	public void actualizarUsuarioPorCorreo(String correo, Usuario datosActualizados) throws Exception {
        List<Usuario> usuarios = daoUsuario.getUsuarioPorCorreo(correo);
        if (usuarios == null || usuarios.isEmpty()) {
            throw new Exception("Usuario no encontrado con correo: " + correo);
        }
        
        Usuario usuarioBD = usuarios.get(0);
        
        usuarioBD.setNombre(datosActualizados.getNombre());
        usuarioBD.setCedula(datosActualizados.getCedula());
        usuarioBD.setTelefono(datosActualizados.getTelefono());
        usuarioBD.setDireccion(datosActualizados.getDireccion());
        usuarioBD.setGenero(datosActualizados.getGenero());
        // setea más campos si hay

        daoUsuario.update(usuarioBD);
    }
	
}
