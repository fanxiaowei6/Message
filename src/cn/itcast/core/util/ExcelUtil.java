package cn.itcast.core.util;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.nsfw.user.entity.User;

public class ExcelUtil {
	/**
	 * 导出用户的所有信息到excel
	 * @param userList用户列表
	 * @param outputStream输出流
	 */

	public static void exportUserExcel(List<User> userList,
			ServletOutputStream outputStream) {
		// TODO Auto-generated method stub

		try {
			// 1创建工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 1.1创建合并单元格对象
			CellRangeAddress cellRangeAddressTitle = new CellRangeAddress(0, 0,
					0, 5);

			CellRangeAddress cellRangeAddressRow = new CellRangeAddress(1, 1,
					4, 5);

			// 1.2头标题样式
			HSSFCellStyle styleTitle = createCellstyle(workbook, (short) 12);

			// 1.3列标题样式
			HSSFCellStyle styleRow = createCellstyle(workbook, (short) 9);

			// 2.创建工作表
			HSSFSheet sheet = workbook.createSheet("用户列表信息");

			// 2.1加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddressTitle);
			// 设置默认列宽
			sheet.setDefaultColumnWidth(30);
			// 3创建行
			// 3.1、创建头标题行；并且设置头标题
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			// 加载单元格样式
			cell.setCellStyle(styleTitle);
			cell.setCellValue("用户列表信息");

			// 3.2、创建列标题行；并且设置列标题
			HSSFRow row1 = sheet.createRow(1);
			String[] titles = { "用户名", "账号", "所属部门", "性别", "电子邮箱" };
			HSSFCell cell1 = null;
			for (int i = 0; i < titles.length; i++) {
				sheet.addMergedRegion(cellRangeAddressRow);
				cell1 = row1.createCell(i);
				// 加载单元格样式
				cell1.setCellStyle(styleRow);
				cell1.setCellValue(titles[i]);
			}
			// 4 创建单元格，将用户信息写入excel
			int j = 0;
			if (userList != null) {
				// for(int j=0;j<userList.size();j++){
				for (User user : userList) {
					cellRangeAddressRow = new CellRangeAddress(j + 2, j + 2, 4,
							5);
					sheet.addMergedRegion(cellRangeAddressRow);
					HSSFRow row2 = sheet.createRow(j + 2);
					HSSFCell cell11 = row2.createCell(0);
					cell11.setCellValue(user.getName());

					HSSFCell cell12 = row2.createCell(1);
					cell12.setCellValue(user.getAccount());

					HSSFCell cell13 = row2.createCell(2);
					cell13.setCellValue(user.getDept());

					HSSFCell cell14 = row2.createCell(3);
					cell14.setCellValue(user.isGender() ? "男" : "女");

					HSSFCell cell15 = row2.createCell(4);
					cell15.setCellValue(user.getEmail());
					j++;
				}
			}
			// 5.输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static HSSFCellStyle createCellstyle(HSSFWorkbook workbook,
			short fontsize) {
		// TODO Auto-generated method stub
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// 1.2.1创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		font.setFontHeightInPoints(fontsize);// 设置字体大小
		// 加载字体
		style.setFont(font);
		return style;
	}

}
