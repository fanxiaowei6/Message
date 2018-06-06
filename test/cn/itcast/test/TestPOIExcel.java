package cn.itcast.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;


public class TestPOIExcel {
	
	/*03版本*/
	@Test
	public void  testWrite03Excel() throws Exception{
		//创建工作簿
	HSSFWorkbook workbook=new HSSFWorkbook();
		//创建工作表
	HSSFSheet sheet = workbook.createSheet("测试");//指定工作表名
		//创建行
	HSSFRow row=sheet.createRow(3);	
		//创建单元格
	HSSFCell cell=row.createCell(3);	
	cell.setCellValue("测试");
	
	//指定输出路径
	FileOutputStream outputStream=new FileOutputStream("F:\\itcast\\xls\\ceshi.xls");
	//把excel输出到具体地址
	workbook.write(outputStream);
	workbook.close();
	outputStream.close();
	}

	@Test
	public void testWrite03readExecl() throws Exception{
		FileInputStream input=new FileInputStream("F:\\itcast\\xls\\ceshi.xls");
		//读取工作薄
		HSSFWorkbook workbook=new HSSFWorkbook(input);
		//读取工作表
		HSSFSheet sheet=workbook.getSheetAt(0);
		//读取行
		HSSFRow row=sheet.getRow(3);
		//读取单元格
		HSSFCell cell=row.getCell(3);
		System.out.println("第四行第四列单元格输出："+cell.getStringCellValue());
		workbook.close();
		input.close();
	}
	
	
	
	/*excel07版本*/
	
	@Test
	public void  testWrite07Excel() throws Exception{
		//创建工作簿
	XSSFWorkbook workbook=new XSSFWorkbook();
		//创建工作表
	XSSFSheet sheet = workbook.createSheet("测试");//指定工作表名
		//创建行
	XSSFRow row=sheet.createRow(3);	
		//创建单元格
	XSSFCell cell=row.createCell(3);	
	cell.setCellValue("测试");
	
	//指定输出路径
	FileOutputStream outputStream=new FileOutputStream("F:\\itcast\\xls\\ceshi.xlsx");
	//把excel输出到具体地址
	workbook.write(outputStream);
	workbook.close();
	outputStream.close();
	}

	@Test
	public void testWrite07readExecl() throws Exception{
		FileInputStream input=new FileInputStream("F:\\itcast\\xls\\ceshi.xlsx");
		//读取工作薄
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		//读取工作表
		XSSFSheet sheet=workbook.getSheetAt(0);
		//读取行
		XSSFRow row=sheet.getRow(3);
		//读取单元格
		XSSFCell cell=row.getCell(3);
		System.out.println("第四行第四列单元格输出："+cell.getStringCellValue());
		workbook.close();
		input.close();
	}
	
	/*03~07版本*/
	@Test
	public void testRead03And07Excel() throws Exception {
		String fileName = "F:\\itcast\\xls\\ceshi.xls";
		if(fileName.lastIndexOf(".") !=-1){
			String suf = "";
			Workbook workbook = null;
			FileInputStream inputStream = new FileInputStream(fileName);
			
			int index = fileName.lastIndexOf(".");
			
			suf = fileName.substring(index+1);
			System.out.println(suf);
			
			if("xlsx".equals(suf)){
				workbook = new XSSFWorkbook(inputStream);
			}else if("xls".equals(suf)){
				workbook = new HSSFWorkbook(inputStream);
			}
			if(null!=workbook){
				Sheet sheet = workbook.getSheetAt(0);
				//3、读取行；读取第3行
				Row row = sheet.getRow(3);
				//4、读取单元格；读取第3行第3列
				Cell cell = row.getCell(3);
				System.out.println("第4行第4列单元格的内容为：" + cell.getStringCellValue());
				
				workbook.close();
			}
			inputStream.close();
		}
		
	}
	
	//样式
	@Test
	public void testStyleExcel() throws Exception{
		
		//1、创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//1.1、创建合并单元格对象;合并第3行的第3列到第5列
		CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 4, 2, 4);//起始行号，结束行号，起始列号，结束列号
		//创建单元格样式
		HSSFCellStyle style=workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//水平居中
		//创建字体
		HSSFFont font=workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontHeightInPoints((short) 8);//设置字体大小		
		//设置单元格背景
		//设置背景填充模式
		style.setFillPattern(HSSFCellStyle.DIAMONDS);
		//设置填充的背景色
		style.setFillBackgroundColor(HSSFColor.RED.index);
		//设置填充的前景色
		style.setFillForegroundColor(HSSFColor.GREEN.index);
		
		
		//2、创建工作表
		HSSFSheet sheet = workbook.createSheet("青春演绎的谁的悲伤");//指定工作表名
		//2.1、加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//加载字体
		style.setFont(font);
		
		//3、创建行；创建第3行
		HSSFRow row = sheet.createRow(2);
		//4、创建单元格；创建第3行第3列
		HSSFCell cell = row.createCell(2);
		//加载样式
		cell.setCellStyle(style);

		cell.setCellValue("青春演绎的谁的悲伤!");
		
		//输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("F:\\itcast\\xls\\ceshi.xls");
		//把excel输出到具体的地址
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();	
		
	}
	
}