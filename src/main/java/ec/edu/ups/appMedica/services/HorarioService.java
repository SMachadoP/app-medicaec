package ec.edu.ups.appMedica.services;

import java.util.List;

import ec.edu.ups.appMedica.business.HorarioON;
import ec.edu.ups.paw.appMedica.model.Horario;
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

@Path("/horarios")
public class HorarioService {
	
	@Inject
	private HorarioON onHorario;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addHorario(Horario h) {
	    try {
	        onHorario.guardarHorario(h);
	        return Response.ok("guardado satisfactorio").build();
	    } catch (Exception e) {
	        e.printStackTrace(); // <--- Para ver la traza del error en consola
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("Error interno: " + e.getMessage())
	                       .build();
	    }
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarios(){
		List<Horario> listado = onHorario.getHorarios();
		return Response.ok(listado).build();
	}
	
	@GET
	@Path("/idmedico/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("id") Integer id) {
		try {
	        List<Horario> horarios = onHorario.getHorarioPorIdMedico(id);
	        if (horarios.isEmpty()) {
	            return Response.status(Response.Status.NOT_FOUND).entity(new Respuesta("error", "No se encontraron citas con ese nombre")).build();
	        }
	        return Response.ok(horarios).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Respuesta("error", e.getMessage())).build();
	    }
	}	


	@POST
	@Path("/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response agregarHorario(Horario horario) {
	    try {
	    	onHorario.guardarHorario(horario);
	        return Response.ok().build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("Error al guardar el horario").build();
	    }
	}

	
	@POST
	@Path("/generar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response generarHorariosDefecto() {
	    try {
	        onHorario.generarHorariosPorDefecto();
	        return Response.ok(new Respuesta("ok", "Horarios generados correctamente")).build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity(new Respuesta("error", e.getMessage())).build();
	    }
	}

}
