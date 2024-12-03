package com.example.blacknoise.service;

import com.example.blacknoise.model.Usuario;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportService {

    public ByteArrayOutputStream generarPdfUsuarios(List<Usuario> usuarios, String titulo) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Título del documento
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph tituloPdf = new Paragraph(titulo, titleFont);
        tituloPdf.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloPdf);

        // Espacio
        document.add(Chunk.NEWLINE);

        // Crear tabla
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Encabezados
        String[] headers = {"ID", "Nombre", "Correo", "Rol"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
        }

        // Datos de usuarios
        for (Usuario usuario : usuarios) {
            table.addCell(usuario.getId());
            table.addCell(usuario.getNombre());
            table.addCell(usuario.getCorreo());
            table.addCell(usuario.getRol().toString());
        }

        document.add(table);
        document.close();

        return outputStream;
    }
}