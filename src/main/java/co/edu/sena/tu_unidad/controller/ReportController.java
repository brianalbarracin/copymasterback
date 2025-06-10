package co.edu.sena.tu_unidad.controller;

import co.edu.sena.tu_unidad.repository.SaleDetailRepository;
import co.edu.sena.tu_unidad.service.ReportService;
import co.edu.sena.tu_unidad.repository.SaleDetailRepository;
import co.edu.sena.tu_unidad.dto.ProductReportDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SaleDetailRepository productoRepository;

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<byte[]> descargarReporte() {
        try {
            // Obtener los productos más vendidos (DTOs)
            List<ProductReportDto> productos = productoRepository.findMostSoldProducts();

            // Generar PDF con el servicio
            byte[] pdf = reportService.generateMostSoldProductsReport(productos);

            // Configurar headers para la respuesta HTTP (PDF)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reporte-productos.pdf");

            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
