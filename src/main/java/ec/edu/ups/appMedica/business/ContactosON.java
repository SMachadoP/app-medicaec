package ec.edu.ups.appMedica.business;

import java.util.List;

import ec.edu.ups.paw.appMedica.dao.EspecialidadDAO;
import ec.edu.ups.paw.appMedica.dao.UsuarioDAO;
import ec.edu.ups.paw.appMedica.model.Especialidad;
import ec.edu.ups.paw.appMedica.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ContactosON {

	@Inject
	private UsuarioDAO daoUsuario;
	
	@Inject
	private EspecialidadDAO daoEspecialidad;
	
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
	    System.out.println("=== DEBUG ContactosON.actualizarUsuarioPorCorreo ===");
	    System.out.println("Correo: " + correo);
	    System.out.println("Datos actualizados: " + datosActualizados);
	    
	    if (correo == null || correo.trim().isEmpty()) {
	        throw new Exception("Correo no puede estar vacío");
	    }
	    
	    if (datosActualizados == null) {
	        throw new Exception("Datos actualizados no pueden ser nulos");
	    }
	    
	    List<Usuario> usuarios = daoUsuario.getUsuarioPorCorreo(correo);
	    System.out.println("Usuarios encontrados: " + usuarios.size());
	    
	    if (usuarios == null || usuarios.isEmpty()) {
	        throw new Exception("Usuario no encontrado con correo: " + correo);
	    }

	    Usuario usuarioBD = usuarios.get(0);
	    System.out.println("Usuario encontrado en BD: " + usuarioBD);
	    
	    // Actualizar solo los campos que no son nulos
	    if (datosActualizados.getNombre() != null) {
	        usuarioBD.setNombre(datosActualizados.getNombre());
	        System.out.println("Actualizando nombre: " + datosActualizados.getNombre());
	    }
	    if (datosActualizados.getCedula() != null) {
	        usuarioBD.setCedula(datosActualizados.getCedula());
	        System.out.println("Actualizando cedula: " + datosActualizados.getCedula());
	    }
	    if (datosActualizados.getTelefono() != null) {
	        usuarioBD.setTelefono(datosActualizados.getTelefono());
	        System.out.println("Actualizando telefono: " + datosActualizados.getTelefono());
	    }
	    if (datosActualizados.getDireccion() != null) {
	        usuarioBD.setDireccion(datosActualizados.getDireccion());
	        System.out.println("Actualizando direccion: " + datosActualizados.getDireccion());
	    }
	    if (datosActualizados.getGenero() != null) {
	        usuarioBD.setGenero(datosActualizados.getGenero());
	        System.out.println("Actualizando genero: " + datosActualizados.getGenero());
	    }
	    
	    // IMPORTANTE: Manejar especialidad correctamente
	    if (datosActualizados.getEspecialidad() != null) {
	        System.out.println("Especialidad recibida: " + datosActualizados.getEspecialidad());
	        
	        if (datosActualizados.getEspecialidad().getId() != null) {
	            // Buscar la especialidad en la base de datos
	            Especialidad especialidadBD = daoEspecialidad.read(datosActualizados.getEspecialidad().getId());
	            if (especialidadBD == null) {
	                throw new Exception("Especialidad con ID " + datosActualizados.getEspecialidad().getId() + " no encontrada");
	            }
	            usuarioBD.setEspecialidad(especialidadBD);
	            System.out.println("Actualizando especialidad: " + especialidadBD.getNombreEspecialidad());
	        } else {
	            // Si el ID es null, establecer especialidad como null
	            usuarioBD.setEspecialidad(null);
	            System.out.println("Removiendo especialidad (establecida como null)");
	        }
	    }
	    
	    System.out.println("Usuario antes de actualizar: " + usuarioBD);
	    
	    try {
	        daoUsuario.update(usuarioBD);
	        System.out.println("Usuario actualizado exitosamente");
	    } catch (Exception e) {
	        System.err.println("Error al actualizar usuario en BD: " + e.getMessage());
	        e.printStackTrace();
	        throw new Exception("Error al actualizar usuario: " + e.getMessage());
	    }
	}
	
}
