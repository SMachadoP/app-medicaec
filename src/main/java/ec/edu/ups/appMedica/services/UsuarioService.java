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
//import jakarta.ws.rs.QueryParam;
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
			return Response.ok("guardado satisfactorio").build();
		}catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).tag("Error").build();
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
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "Usuario no existe")).build();
	        }
	        return Response.ok(u).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}
	
	@GET
	@Path("/nombre/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorNombre(@PathParam("nombre") String nombre) {
	    try {
	        List<Usuario> usuarios = onContactos.getContactoPorNombre(nombre);
	        if (usuarios.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "No se encontraron usuarios con ese nombre")).build();
	        }
	        return Response.ok(usuarios).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}
	
	@GET
	@Path("/correo/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioPorCorreo(@PathParam("correo") String correo) {
	    try {
	        List<Usuario> usuarios = onContactos.getContactoPorCorreo(correo);
	        if (usuarios.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "No se encontraron usuarios con ese correo")).build();
	        }
	        return Response.ok(usuarios).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}
	
	@PUT
	@Path("correo/{correo}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarPorCorreo(@PathParam("correo") String correo, Usuario u) {
	    try {
	        onContactos.actualizarUsuarioPorCorreo(correo, u);
	        return Response.ok("Usuario actualizado correctamente").build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity(new Respuesta("error", e.getMessage()))
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
	                           .entity(new Respuesta("error", "No se encontraron médicos con esa especialidad"))
	                           .build();
	        }
	        return Response.ok(medicos).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity(new Respuesta("error", e.getMessage()))
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
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "No se encontraron usuarios con esa cédula")).build();
	        }
	        return Response.ok(usuarios).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}
}
