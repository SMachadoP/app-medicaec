package ec.edu.ups.appMedica.services;

import java.util.List;

import ec.edu.ups.appMedica.business.ContactosON;
import ec.edu.ups.paw.appMedica.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class UsuarioService {
	
	@Inject
	private ContactosON onContactos;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUsuario(Usuario u) {
		try {
			onContactos.guardarUsuario(u);
			return Response.ok("{\"status\":\"success\",\"mensaje\":\"Usuario guardado correctamente\"}").build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
					.build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarios(){
		List<Usuario> listado = onContactos.getContactos();
		return Response.ok(listado).build();
	}
	
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("id") Integer id) {
	    try {
	        Usuario u = onContactos.getContacto(id);
	        if (u == null) {
	            return Response.status(Response.Status.NOT_FOUND)
	            		.entity("{\"status\":\"error\",\"mensaje\":\"Usuario no existe\"}")
	            		.build();
	        }
	        return Response.ok(u).build();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	        		.entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
	        		.build();
	    }
	}
	
	@GET
	@Path("/nombre/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorNombre(@PathParam("nombre") String nombre) {
	    try {
	        List<Usuario> usuarios = onContactos.getContactoPorNombre(nombre);
	        if (usuarios.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND)
	            		.entity("{\"status\":\"error\",\"mensaje\":\"No se encontraron usuarios con ese nombre\"}")
	            		.build();
	        }
	        return Response.ok(usuarios).build();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	        		.entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
	        		.build();
	    }
	}
	
	@GET
	@Path("/correo/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorCorreo(@PathParam("correo") String correo) {
	    try {
	        List<Usuario> usuarios = onContactos.getContactoPorCorreo(correo);
	        if (usuarios.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND)
	            		.entity("{\"status\":\"error\",\"mensaje\":\"No se encontraron usuarios con ese correo\"}")
	            		.build();
	        }
	        return Response.ok(usuarios).build();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	        		.entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
	        		.build();
	    }
	}
	
	// ¡CORRECCIÓN IMPORTANTE! Agregar la barra diagonal que faltaba
	@PUT
	@Path("/correo/{correo}")  // <-- ERA "correo/{correo}" sin la barra inicial
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)  // <-- AGREGADO para devolver JSON
	public Response actualizarPorCorreo(@PathParam("correo") String correo, Usuario u) {
	    try {
	        System.out.println("=== DEBUG ACTUALIZAR USUARIO ===");
	        System.out.println("Correo recibido: " + correo);
	        System.out.println("Usuario recibido: " + u);
	        System.out.println("Datos del usuario: " + (u != null ? u.toString() : "null"));
	        
	        if (correo == null || correo.trim().isEmpty()) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                    .entity("{\"status\":\"error\",\"mensaje\":\"Correo no puede estar vacío\"}")
	                    .build();
	        }
	        
	        if (u == null) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                    .entity("{\"status\":\"error\",\"mensaje\":\"Datos de usuario no pueden estar vacíos\"}")
	                    .build();
	        }
	        
	        onContactos.actualizarUsuarioPorCorreo(correo, u);
	        System.out.println("Usuario actualizado exitosamente");
	        
	        return Response.ok()
	                .entity("{\"status\":\"success\",\"mensaje\":\"Usuario actualizado correctamente\"}")
	                .build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error al actualizar usuario: " + e.getMessage());
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
	                       .build();
	    }
	}
	
	@GET
	@Path("/medicoespecialidad/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedicosPorEspecialidad(@PathParam("id") int idEspecialidad) {
	    try {
	        List<Usuario> medicos = onContactos.getMedicoPorEspecialidad(idEspecialidad);
	        if (medicos.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND)
	                           .entity("{\"status\":\"error\",\"mensaje\":\"No se encontraron médicos con esa especialidad\"}")
	                           .build();
	        }
	        return Response.ok(medicos).build();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
	                       .build();
	    }
	}

	@GET
	@Path("/cedula/{cedula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorCedula(@PathParam("cedula") String cedula) {
	    try {
	        List<Usuario> usuarios = onContactos.getContactoPorCedula(cedula);
	        if (usuarios.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND)
	            		.entity("{\"status\":\"error\",\"mensaje\":\"No se encontraron usuarios con esa cédula\"}")
	            		.build();
	        }
	        return Response.ok(usuarios).build();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	        		.entity("{\"status\":\"error\",\"mensaje\":\"" + e.getMessage() + "\"}")
	        		.build();
	    }
	}
}
