package Clases;
//import com.lowagie.text.Document;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


public class PdfVenta {

	   public void generarPDF(
	            String cliente,
	            String vendedor,
	            String fecha,
	            String numeroComprobante,
	            DefaultTableModel modelo,
	            String subtotal,
	            String igv,
	            String total
	    ) {

	        Document document = new Document(PageSize.A4);

	        try {
	        	//C:\Users\ronal\OneDrive\Escritorio\Ventas
	        	
	        	String ruta = System.getProperty("user.home") 
	        	        + "\\OneDrive\\Escritorio\\Ventas_" + numeroComprobante + ".pdf";

	        	PdfWriter.getInstance(
	        	        document,
	        	        new FileOutputStream(ruta)
	        	);
	        	document.open();

	            // =========================
	            // ENCABEZADO
	            // =========================
	            document.add(new Paragraph("===================================="));
	            document.add(new Paragraph("           COMPROBANTE DE VENTA"));
	            document.add(new Paragraph("===================================="));

	            document.add(new Paragraph(" "));
	            document.add(new Paragraph("Cliente: " + cliente));
	            document.add(new Paragraph("Vendedor: " + vendedor));
	            document.add(new Paragraph("Fecha: " + fecha));
	            document.add(new Paragraph("N° Comprobante: " + numeroComprobante));

	            document.add(new Paragraph(" "));
	            document.add(new Paragraph("------------------------------------"));

	            // =========================
	            // DETALLE
	            // =========================
	            document.add(new Paragraph("PRODUCTO     | CANT | PRECIO | IMPORTE"));
	            document.add(new Paragraph("------------------------------------"));

	            for (int i = 0; i < modelo.getRowCount(); i++) {

	                String fila =
	                        modelo.getValueAt(i, 1) + " | " +
	                        modelo.getValueAt(i, 2) + " | " +
	                        modelo.getValueAt(i, 3) + " | " +
	                        modelo.getValueAt(i, 4);

	                document.add(new Paragraph(fila));
	            }

	            document.add(new Paragraph("------------------------------------"));

	            // =========================
	            // TOTALES
	            // =========================
	            document.add(new Paragraph(" "));
	            document.add(new Paragraph("SUBTOTAL: S/ " + subtotal));
	            document.add(new Paragraph("IGV (18%): S/ " + igv));
	            document.add(new Paragraph("TOTAL: S/ " + total));

	            document.add(new Paragraph(" "));
	            document.add(new Paragraph("===================================="));

	            document.close();

	            JOptionPane.showMessageDialog(null,
	                    "PDF generado correctamente");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
  
}
