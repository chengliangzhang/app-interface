package com.maoding.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ExcelUtil
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2016年4月15日-下午2:11:25
 */
public class ExcelUtil {
	private static final Logger log=LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 描述：读取所有的Sheet
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:38:59
	 * @param excelFile
	 * @return
	 */
	public static Map<String, List<List<Object>>>readAllSheetFromExcel(File excelFile){
		Map<String, List<List<Object>>> dataMap = new HashMap<String, List<List<Object>>>();
		InputStream in = null;
		Workbook wb = null;
		Sheet sheet;
		try {
			in=new FileInputStream(excelFile);
			wb=WorkbookFactory.create(in);
		} catch (InvalidFormatException e) {
			log.error("文件格式错误", e);
		} catch (IOException e) {
			log.error("读取失败", e);
		}
		
		int sheetNum = wb.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			dataMap.put(sheetName, readSheet(sheet, 0));
		}
		return dataMap;
	}
	/**
	 * 描述：读取第一个sheet
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:39:41
	 * @param excelFile
	 * @return
	 */
	public static List<List<Object>>readOneSheetFromExcel(File excelFile){
		return readSheetBySheetIndexFromExcel(excelFile, 0, 0);
	}
	
	/**
	 * 描述：读取第一个sheet
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:39:41
	 * @param excelFile
	 * @return
	 */
	public static List<List<Object>>readOneSheetFromExcel(InputStream in){
		return readSheetBySheetIndexFromExcel(in, 0, 0);
	}
	/**
	 * 描述：读取指定Sheet名称，返回数据集
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:32:04
	 * @param excelFile Excel文件
	 * @param sheetName Sheet名称
	 * @param startIndex 开始行号
	 * @return CSV list
	 */
	public static List<List<Object>>readSheetBySheetNameFromExcel(File excelFile,String sheetName,int startIndex){
		InputStream in = null;
		Workbook wb = null;
		Sheet sheet;
		try {
			in=new FileInputStream(excelFile);
			wb=WorkbookFactory.create(in);
		} catch (InvalidFormatException e) {
			log.error("文件格式错误", e);
		} catch (IOException e) {
			log.error("读取失败", e);
		}
		sheet = wb.getSheet(sheetName);
		return readSheet(sheet, startIndex);
	}
	/**
	 * 描述：读取指定索引Sheet，返回数据集
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:30:49
	 * @param excelFile Excel文件
	 * @param sheetIndex Sheet索引号
	 * @param startIndex 开始行号
	 * @return CSV list
	 */
	public static List<List<Object>>readSheetBySheetIndexFromExcel(File excelFile,int sheetIndex,int startIndex){
		InputStream in = null;
		Workbook wb = null;
		Sheet sheet;
		try {
			in=new FileInputStream(excelFile);
			wb=WorkbookFactory.create(in);
		} catch (InvalidFormatException e) {
			log.error("文件格式错误", e);
		} catch (IOException e) {
			log.error("读取失败", e);
		}
		sheet = wb.getSheetAt(sheetIndex);
		return readSheet(sheet, startIndex);
	}
	
	/**
	 * 描述：读取指定索引Sheet，返回数据集
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:30:49
	 * @param excelFile Excel文件
	 * @param sheetIndex Sheet索引号
	 * @param startIndex 开始行号
	 * @return CSV list
	 */
	public static List<List<Object>>readSheetBySheetIndexFromExcel(InputStream in,int sheetIndex,int startIndex){
		Workbook wb = null;
		Sheet sheet;
		try {
			wb=WorkbookFactory.create(in);
		} catch (InvalidFormatException e) {
			log.error("文件格式错误", e);
		} catch (IOException e) {
			log.error("读取失败", e);
		}
		sheet = wb.getSheetAt(sheetIndex);
		return readSheet(sheet, startIndex);
	}
	/**
	 * 读取指定sheet
	 * @param sheet
	 * @param startIndex 开始行号
	 * @return list
	 */
	private  static List<List<Object>> readSheet(Sheet sheet,int startIndex){
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		Row row;
		int rowNum = sheet.getPhysicalNumberOfRows();
		int firstRowColNum=-1;
		row=sheet.getRow(0);
		if(row!=null){
			firstRowColNum=row.getPhysicalNumberOfCells();
			if(startIndex==0){
				for(int i=startIndex;i<rowNum;i++){
					row=sheet.getRow(i);
					List<Object> rowDataList = new ArrayList<Object>();
					for(int j=0;j<firstRowColNum;j++){
						rowDataList.add(readCellValu(row.getCell(j)));
					}
					dataList.add(rowDataList);
				}
			}else if(startIndex>0&&startIndex<=rowNum){
				//读取第一行数据
				row = sheet.getRow(0);
				if(row!=null){
					List<Object> rowDataList = new ArrayList<Object>();
					for(int j=0;j<firstRowColNum;j++){
						rowDataList.add(readCellValu(row.getCell(j)));
					}
					dataList.add(rowDataList);
				}
				//从startIndex处开始读取数据
				for(int i=startIndex;i<rowNum;i++){
					row=sheet.getRow(i);
					List<Object> rowDataList = new ArrayList<Object>();
					for(int j=0;j<firstRowColNum;j++){
						rowDataList.add(readCellValu(row.getCell(j)));
					}
					dataList.add(rowDataList);
				}
			}
		}
		return dataList;
	}
	/**
	 * 描述：读取Excel格子中的值
	 * 作者：Chenxj
	 * 日期：2016年3月27日 - 上午9:16:31
	 * @param c Excel中的格子对象
	 * @return Object
	 */
	private  static Object readCellValu(Cell c) {
		if(c==null){
			return "";
		}else{
			switch (c.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				return "";
			case Cell.CELL_TYPE_BOOLEAN:
				return c.getBooleanCellValue();
			case Cell.CELL_TYPE_ERROR:
				return c.getErrorCellValue();
			case Cell.CELL_TYPE_FORMULA:
				return c.getCellFormula().replaceAll("\"", "");
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(c)) {
					return c.getDateCellValue();
				}
				java.text.DecimalFormat df = new DecimalFormat("#");
				return df.format(c.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return c.getStringCellValue();
			}
			return "Unknown Cell Type:" + c.getCellType();
		}
	}
}
