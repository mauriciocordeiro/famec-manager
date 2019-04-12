package br.org.mac.famec.util;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sol.dao.ResultSetMap;
import sol.util.Result;

public class ReportUtils {
	
	private static final String REPORT_PATH = "/report/#name.jrxml";
	
	public static Result generate(String reportName, HashMap<String, Object> parameters, ResultSetMap rsmDataSource) {
		try {
			
			String report = REPORT_PATH.replaceAll("#name", reportName);
			
			JasperReport jasperReport = JasperCompileManager.compileReport(report);
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(rsmDataSource.getLines());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);		
		
			return new Result(1, "", "PDF_BYTES", JasperExportManager.exportReportToPdf(jasperPrint));
		} catch(JRException jrex) {
			return new Result(-1, "Erro ao gerar.", "PDF_BYTES", null);
		} catch(Exception e) {
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
