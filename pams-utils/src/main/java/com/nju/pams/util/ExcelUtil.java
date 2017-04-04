package com.nju.pams.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.transformer.XLSTransformer;

public class ExcelUtil {
	
	private static final Logger logger = Logger.getLogger(ExcelUtil.class);
	
	private final static String EXCEL_PROPERTIES_FILE = "excel.properties";
	
	private static XLSTransformer transformer = new XLSTransformer();
	
	private static Properties prop = new Properties();
	
	public static void generateExcelFile(String fileModel, Map<String, Object> dataMap,
			List<Integer> hiddenIndexSheets, Map<Integer, List<Integer>> columnMap,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//找到对应的模版excel路径
		prop.load(ExcelUtil.class.getClassLoader().getResourceAsStream(EXCEL_PROPERTIES_FILE));
		String excelPath = prop.getProperty(fileModel);
		if (null == excelPath || "".equals(excelPath)) {
			logger.info("没有根据model：" + fileModel + "找到对应的excel模版文件");
			return;
		}

		//根据excel路径，生成excel文件
	    InputStream inputStream = new FileInputStream(new File(request.getSession().getServletContext().getRealPath(excelPath)));
		Workbook wb = null;
		try {
			wb = transformer.transformXLS(inputStream, dataMap);
		} catch (Exception e) {
			logger.error("生成excel文件失败");
		}
		if (wb == null) {
			return;
		}
		
		//设置sheet和column
		hiddenExcelColumn(columnMap, wb);
		hiddenExcelSheet(hiddenIndexSheets, wb);
		
		//设置response头部信息
		String fileName = excelPath.substring(excelPath.lastIndexOf("/") + 1);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment; filename="+ fileName);
		
		//将excel文件写入输出流
		OutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}
	
	public static void hiddenExcelColumn(Map<Integer, List<Integer>> columnMap, Workbook wb) {
		if (columnMap == null || columnMap.isEmpty()) {
			return;
		}
		
		//获取excel.sheet总数
		int sheetCounts = wb.getNumberOfSheets();		
		List<Integer> allSheetIndex = new ArrayList<Integer>();
		for (int i = 0; i < sheetCounts; i++) {
			allSheetIndex.add(i);
		}
		
		List<Integer> allColumns = null;
		for (Map.Entry<Integer, List<Integer>> item : columnMap.entrySet()) {
			Integer indexSheet = item.getKey();
			List<Integer> showColumns = item.getValue();
			
			if (allSheetIndex.contains(indexSheet) && showColumns != null && ! showColumns.isEmpty()) {
				allColumns = new ArrayList<Integer>();
				Sheet sheet = wb.getSheetAt(indexSheet);
				Row row = sheet.getRow(0);			
				int columnCount = row.getPhysicalNumberOfCells();
				for (int i = 0; i < columnCount; i++) {
					allColumns.add(i);
				}				
				allColumns.removeAll(showColumns);
				for (Integer hiddenColumnIndex : allColumns) {
					sheet.setColumnHidden(hiddenColumnIndex, true);
				}
			}
		}
	}
	
	public static void hiddenExcelSheet(List<Integer> hiddenIndexSheets, Workbook wb) {
		if (hiddenIndexSheets == null || hiddenIndexSheets.isEmpty()) {
			return ;
		}
		
		//获取excel.sheet总数
		int sheetCounts = wb.getNumberOfSheets();	
		List<Integer> showSheetIndex = new ArrayList<Integer>();
		for (int i = 0; i < sheetCounts; i++) {
			showSheetIndex.add(i);
		}
		
		//删除sheet
		showSheetIndex.removeAll(hiddenIndexSheets);
		//解决sheet活跃无法隐藏
		if (showSheetIndex.size() > 0) {
			wb.setActiveSheet(showSheetIndex.get(0));
		}
		
		for (int i = 0; i < hiddenIndexSheets.size(); i++) {
			if (i == 0) {
				wb.removeSheetAt(hiddenIndexSheets.get(i));
			} 
			else {
				wb.removeSheetAt(hiddenIndexSheets.get(i) - i);
			}
		}
	}
}
	
