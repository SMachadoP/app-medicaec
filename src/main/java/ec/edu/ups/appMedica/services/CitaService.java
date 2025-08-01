package ec.edu.ups.appMedica.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ec.edu.ups.appMedica.business.CitaON;
import ec.edu.ups.paw.appMedica.model.Cita;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
//import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/citas")
public class CitaService {
	
	@Inject
	private CitaON onCitas;
	private static final SimpleDateFormat ISO_FMT = new SimpleDateFormat("yyyy-MM-dd");
	
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
	
	@GET
	@Path("/historial")
	@Produces(MediaType.APPLICATION_JSON)
	public Response historial(
	    @QueryParam("pacienteId") Integer pid,
	    @QueryParam("medicoId")   Integer mid,
	    @QueryParam("from")       String fromStr,
	    @QueryParam("to")         String toStr,
	    @QueryParam("especialidad") Integer esp,
	    @QueryParam("estado")     String estado
	) {
	    try {
	      Date from = (fromStr != null) ? ISO_FMT.parse(fromStr) : null;
	      Date to   = (toStr   != null) ? ISO_FMT.parse(toStr)   : null;
	      List<Cita> lista = onCitas.getHistorial(pid, mid, from, to, esp, estado);
	      return Response.ok(lista).build();
	    } catch (ParseException e) {
	      return Response.status(Response.Status.BAD_REQUEST)
	                     .entity("Fechas con formato inválido (yyyy-MM-dd)")
	                     .build();
	    }
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCita(@PathParam("id") Integer id, Cita citaActualizada) {
	    try {
	        Cita citaExistente = onCitas.getCitas().stream()
	            .filter(c -> c.getId().equals(id))
	            .findFirst()
	            .orElse(null);
	            
	        if (citaExistente == null) {
	            return Response.status(Response.Status.NOT_FOUND)
	                .entity(new Respuesta("error", "Cita no encontrada"))
	                .build();
	        }
	        
	        // Actualizar solo los campos que pueden cambiar
	        if (citaActualizada.getEstado() != null) {
	            citaExistente.setEstado(citaActualizada.getEstado());
	        }
	        
	        onCitas.guardarCita(citaExistente);
	        return Response.ok(new Respuesta("success", "Cita actualizada correctamente")).build();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	            .entity(new Respuesta("error", e.getMessage()))
	            .build();
	    }
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCita(@PathParam("id") Integer id) {
	    try {
	        // Necesitarás agregar un método deleteCita en CitaON
	        onCitas.deleteCita(id);
	        return Response.ok(new Respuesta("success", "Cita eliminada correctamente")).build();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	            .entity(new Respuesta("error", e.getMessage()))
	            .build();
	    }
	}
	
}
