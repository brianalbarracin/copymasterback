package co.edu.sena.tu_unidad.service;
import org.springframework.stereotype.Service;
import java.util.List;
import co.edu.sena.tu_unidad.dto.ProductReportDto;
import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.time.LocalDate;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.Image;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.ui.RectangleInsets;
import java.util.ArrayList;



@Service
public class ReportService {
    public byte[] generateMostSoldProductsReport(List<ProductReportDto> products) throws DocumentException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        // Título
        document.add(new Paragraph("Reporte de Productos Más Vendidos"));
        document.add(new Paragraph("Fecha de generación: " + LocalDate.now()));
        document.add(Chunk.NEWLINE);

        // Tabla
        PdfPTable table = new PdfPTable(3);
        table.addCell("Producto");
        table.addCell("Cantidad Vendida");
        table.addCell("Ingresos");

        // Generar gráfico
        Image chartImage = createBarChartImage(products);
        document.add(chartImage);  // Añadir imagen del gráfico al PDF
        document.add(Chunk.NEWLINE);

        for (ProductReportDto p : products) {
            table.addCell(p.getName());
            table.addCell(String.valueOf(p.getQuantitySold()));
            table.addCell("$" + p.getTotalRevenue());
        }

        document.add(table);
        List<ProductReportDto> productosPorIngresos = new ArrayList<>(products);
        productosPorIngresos.sort((a, b) -> b.getTotalRevenue().compareTo(a.getTotalRevenue()));

        // Separación
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Productos Ordenados por Ingresos"));
        document.add(Chunk.NEWLINE);

        // Tabla por ingresos
        PdfPTable tableIngresos = new PdfPTable(3);
        tableIngresos.addCell("Producto");
        tableIngresos.addCell("Cantidad Vendida");
        tableIngresos.addCell("Ingresos");

        for (ProductReportDto p : productosPorIngresos) {
            tableIngresos.addCell(p.getName());
            tableIngresos.addCell(String.valueOf(p.getQuantitySold()));
            tableIngresos.addCell("$" + p.getTotalRevenue());
        }
        document.add(tableIngresos);
        document.close();

        return out.toByteArray();
    }

    private Image createBarChartImage(List<ProductReportDto> products) throws IOException, DocumentException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ProductReportDto p : products) {
            dataset.addValue(p.getQuantitySold(), "Cantidad Vendida", p.getName());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Productos Más Vendidos",
                "Producto",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        // Ajustar márgenes y formato del eje
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setOutlineVisible(false);
        plot.setInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0)); // Márgenes

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90); // Vertical

        BufferedImage bufferedImage = barChart.createBufferedImage(600, 400);
        ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(chartOut, bufferedImage);

        return Image.getInstance(chartOut.toByteArray());
    }
}
