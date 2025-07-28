package ec.edu.ups.appMedica.services;

import java.util.List;

import ec.edu.ups.appMedica.business.CitaON;
import ec.edu.ups.paw.appMedica.model.Cita;
import ec.edu.ups.paw.appMedica.model.Usuario;
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

@Path("/citas")
public class CitaService {
	
	@Inject
	private CitaON onCitas;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCita(Cita c) {
	    try {
	        onCitas.guardarCita(c);
	        return Response.ok("guardado satisfactorio").build();
	    } catch (Exception e) {
	        e.printStackTrace(); // imprime en consola servidor el error real
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity(new Respuesta("error", e.getMessage()))
	                       .build();
	    }
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCitas(){
		List<Cita> listado = onCitas.getCitas();
		return Response.ok(listado).build();
	}
	
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCita(@PathParam("id") Integer id) {
		try {
	        List<Cita> citas = onCitas.getCitaPorIdPaciente(id);
	        if (citas.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "No se encontraron citas con ese nombre")).build();
	        }
	        return Response.ok(citas).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}
	
}
