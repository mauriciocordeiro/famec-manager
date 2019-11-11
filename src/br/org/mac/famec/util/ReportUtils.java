package br.org.mac.famec.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.HashMap;

import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import sol.dao.ResultSetMap;
import sol.util.Result;

public class ReportUtils {
	
	private static final String REPORT_PATH = "/reports/#name.jrxml";
	
	public static Result generate(String reportName, HashMap<String, Object> parameters, ResultSetMap rsmDataSource) {
		try {			

			String report = REPORT_PATH.replaceAll("#name", reportName);
			
			URL url = ReportUtils.class.getResource(report);
			File file = new File(url.getPath());
			
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(rsmDataSource.getLines());	
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);	
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			JRPdfExporter exporter = new JRPdfExporter();
	    	exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	    	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
	    	SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	    	configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
	    	exporter.setConfiguration(configuration);
	    	exporter.exportReport();
					
			return new Result(1, "", "PDF_BYTES", out.toByteArray());
		} catch(JRException jrex) {
			jrex.printStackTrace(System.out);
			return new Result(-1, "Erro ao gerar.", "PDF_BYTES", null);
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
//	public static void main(String[] args) throws JRException, IOException {
//		 
//       // Compile jrxml file.
//       JasperReport jasperReport = JasperCompileManager
//               .compileReport("C:/jasperreport/StyledTextReport/StyledTextReport.jrxml");
// 
//       // Parameters for report
//       Map<String, Object> parameters = new HashMap<String, Object>();
// 
//       // DataSource
//       // This is simple example, no database.
//       // then using empty datasource.
//       JRDataSource dataSource = new JREmptyDataSource();
//       
// 
//       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
//               parameters, dataSource);
// 
//    
//       // Make sure the output directory exists.
//       File outDir = new File("C:/jasperoutput");
//       outDir.mkdirs();
// 
//       // Export to PDF.
//       JasperExportManager.exportReportToPdfFile(jasperPrint,
//               "C:/jasperoutput/StyledTextReport.pdf");
//        
//       System.out.println("Done!");
//	}

}
