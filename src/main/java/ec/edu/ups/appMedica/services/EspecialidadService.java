package ec.edu.ups.appMedica.services;

import java.util.List;

import ec.edu.ups.appMedica.business.EspecialidadON;
import ec.edu.ups.paw.appMedica.model.Especialidad;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/especialidades")
public class EspecialidadService {
	
	@Inject
	private EspecialidadON onEspecialidad;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCita(Especialidad s) {
		try {
			onEspecialidad.guardarEspecialidad(s);
			return Response.ok("guardado satisfactorio").build();
		}catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).tag("Error").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarios(){
		List<Especialidad> listado = onEspecialidad.getEspecialidades();
		return Response.ok(listado).build();
	}
	
	/*@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("id") Integer id) {
		try {
	        List<Especialidad> citas = onEspecialidad.getCitaPorIdPaciente(id);
	        if (citas.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "No se encontraron citas con ese nombre")).build();
	        }
	        return Response.ok(citas).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}*/
	
}
