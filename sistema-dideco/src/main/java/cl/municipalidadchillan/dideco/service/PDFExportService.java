package cl.municipalidadchillan.dideco.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.DeviceRgb;

import cl.municipalidadchillan.dideco.model.Actividad;

@Service
public class PDFExportService {
    
    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DeviceRgb HEADER_COLOR = new DeviceRgb(63, 169, 219);
    private static final DeviceRgb BACKGROUND_COLOR = new DeviceRgb(240, 240, 240);

    public byte[] generarPDFActividades(List<Actividad> actividades) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título principal
            String titulo = actividades.size() == 1 ? 
                "Reporte de Actividad: " + actividades.get(0).getNombreActividad() :
                "Reporte de Actividades";

            Paragraph tituloPrincipal = new Paragraph(titulo)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(HEADER_COLOR)
                .setBold()
                .setMarginBottom(20);
            document.add(tituloPrincipal);

            // Subtítulo con el programa si es una sola actividad
            if (actividades.size() == 1) {
                Paragraph subtitulo = new Paragraph("Programa: " + actividades.get(0).getPrograma().getNombrePrograma())
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMarginBottom(30);
                document.add(subtitulo);
            }

            // Crear tabla
            Table tabla = new Table(UnitValue.createPercentArray(new float[]{2, 3, 2, 2, 2, 2}))
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);

            // Estilo de encabezados
            String[] headers = {"Actividad", "Descripción", "Fecha Inicio", "Fecha Término", "Programa", "Responsable"};
            for (String header : headers) {
                Cell headerCell = new Cell()
                    .add(new Paragraph(header))
                    .setBackgroundColor(HEADER_COLOR)
                    .setFontColor(ColorConstants.WHITE)
                    .setBold()
                    .setPadding(10)
                    .setTextAlignment(TextAlignment.CENTER);
                tabla.addHeaderCell(headerCell);
            }

            // Datos con estilo alternado
            boolean isAlternateRow = false;
            for (Actividad actividad : actividades) {
                Cell[] cells = {
                    createCell(actividad.getNombreActividad()),
                    createCell(actividad.getDescripcion()),
                    createCell(actividad.getFechaInicio().format(FECHA_FORMATTER)),
                    createCell(actividad.getFechaTermino() != null ? 
                        actividad.getFechaTermino().format(FECHA_FORMATTER) : "No definida"),
                    createCell(actividad.getPrograma().getNombrePrograma()),
                    createCell(actividad.getResponsable() != null ? 
                        actividad.getResponsable() : "No asignado")
                };

                for (Cell cell : cells) {
                    if (isAlternateRow) {
                        cell.setBackgroundColor(BACKGROUND_COLOR);
                    }
                    tabla.addCell(cell);
                }
                isAlternateRow = !isAlternateRow;
            }

            document.add(tabla);

            // Pie de página
            Paragraph footer = new Paragraph("Generado el " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    private Cell createCell(String content) {
        return new Cell()
            .add(new Paragraph(content))
            .setPadding(8)
            .setTextAlignment(TextAlignment.CENTER);
    }
}