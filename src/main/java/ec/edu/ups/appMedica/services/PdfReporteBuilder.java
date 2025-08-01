package ec.edu.ups.appMedica.services;

import java.io.OutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import ec.edu.ups.paw.appMedica.model.Cita;

public class PdfReporteBuilder {

  private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  public static void buildPdf(
	      List<Cita> citas,
	      OutputStream out,
	      String medicoNombre,
	      String especialidadNombre,
	      java.time.LocalDate desde,
	      java.time.LocalDate hasta
	  ) throws Exception {
	    PdfDocument pdf = new PdfDocument(new PdfWriter(out));
	    Document doc = new Document(pdf);

	    // Título
	    doc.add(new Paragraph("Reporte de Citas").setFontSize(16).setBold());
	    doc.add(new Paragraph(
	        String.format("Médico: %s    Especialidad: %s", medicoNombre, especialidadNombre)
	    ));
	    doc.add(new Paragraph(
	        String.format("Desde: %s    Hasta: %s", desde, hasta)
	    ).setMarginBottom(20));

	    // Cabecera
	    float[] widths = { 120, 100, 100, 80, 80 };
	    Table table = new Table(widths);
	    table.addHeaderCell(new Cell().add(new Paragraph("Paciente")));
	    table.addHeaderCell(new Cell().add(new Paragraph("Fecha")));
	    table.addHeaderCell(new Cell().add(new Paragraph("Especialidad")));
	    table.addHeaderCell(new Cell().add(new Paragraph("Estado")));
	    table.addHeaderCell(new Cell().add(new Paragraph("Medico")));

	    // Filas
	    citas.forEach(c -> {
	      table.addCell(c.getPaciente().getNombre());
	      table.addCell(
	        c.getFecha()
	         .toInstant()
	         .atZone(ZoneId.systemDefault())
	         .toLocalDateTime()
	         .format(DTF)
	      );
	      table.addCell(c.getEspecialidad().getNombreEspecialidad());
	      table.addCell(c.getEstado());
	      table.addCell(c.getMedico().getNombre());
	    });

	    doc.add(table);
	    doc.close();
	  }
}

