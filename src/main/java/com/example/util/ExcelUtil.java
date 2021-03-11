package com.example.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel 通用工具类  2020/05/26
 */
@Component
public class ExcelUtil {

//    private static Logger logger = LoggerFactory.getLogger(util.excel.ExcelUtil.class);

    /**
     * 读取excel数据
     * @param file
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static Map<String, Object> readExcel(MultipartFile file,Map<String,String[]>keyMap) throws Exception {
        checkFile(file);
        Map<String, Object> data = new HashMap<>();
        Workbook workbook = getWorkbook(file);
        if (workbook != null) {
            int sheetNumber = workbook.getNumberOfSheets();//sheet页个数
            for (int i = 0; i < sheetNumber; i++) {
                List<Map<String, Object>> sheetData = new ArrayList<>();
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                String []keyName=keyMap.get("sheet"+i);
                //循环读取除了第一行的数据
                for (int j = firstRow + 1; j <= lastRow; j++) {
                    Row row = sheet.getRow(j);
                    int columnNumber = row.getPhysicalNumberOfCells();
                    Map<String, Object> map = new HashMap<>();
                    //循环读取列数据
                    for (int k = 0; k < columnNumber; k++) {
                        Cell cell = row.getCell(k);
                        String value = getCellValue(cell);
                        map.put(keyName[k],value);
                    }
                    sheetData.add(map);
                }
                data.put("sheet"+i,sheetData);
            }
        }
        return data;
    }

    /**
     * excel 导出数据
     * @param title
     * @param fileName
     * @param headers
     * @param dataList
     * @param KeyList
     */
    public static String  exportExcel(String title,String fileName,String [] headers,List<Map<String,Object>> dataList,String []KeyList,String filePath){
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
       //获取列标题样式和单元格数据的样式；
        XSSFCellStyle headerStyle = getHeaderStyle(workbook);
        XSSFCellStyle contentStyle = getContentStyle(workbook);
        // 生成表格标题行
        XSSFRow row = sheet.createRow(0);
        int i = 0;
        for(String header:headers){
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            XSSFRichTextString text = new XSSFRichTextString(header);
            cell.setCellValue(text);
            i++;
        }
        int index = 0;
        //循环dataList，每一条对应一行
        for(Map data:dataList){
            index++;
            row = sheet.createRow(index);
            int j = 0;
            for(String key:KeyList){
                XSSFCell cell = row.createCell(j);
                cell.setCellStyle(contentStyle);
                String value=data.get(key)+"";
                XSSFRichTextString richString = new XSSFRichTextString(value);
                cell.setCellValue(richString);
                j++;
            }
        }
        //让列宽随着导出的列长自动适应
        String Path=deal(headers,sheet,workbook,fileName,filePath);
        return Path;
    }

    /**
     * 校验文件格式
     * @param file
     * @throws IOException
     */
    public static void checkFile(MultipartFile file) throws IOException {
        if (file == null) {
            throw new FileNotFoundException("文件不存在!");
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //判断文件类型
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            throw new IOException(fileName + "不是excel文件");
        }
    }

    /**
     *获取文件对象
     * @param file
     * @return
     */
    public static Workbook getWorkbook(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            InputStream in = file.getInputStream();
            if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(in);
            } else {
                workbook = new XSSFWorkbook(in);
            }
        } catch (IOException ex) {
//            logger.error(ex.getMessage());
        }
        return workbook;
    }

    /**
     * 获取单元格内容
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String value = "";
        if (cell == null) {
            return value;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC://数字
                value = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING://字符串
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN://布尔
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA://公式
                value = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK://空值
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR://故障类型
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    /**
     * 生成一个标题style
     *
     * @return style
     */
    public static XSSFCellStyle getHeaderStyle(Workbook workbook) {
        return getHeaderStyle(workbook, Color.BLUE, IndexedColors.BLACK.getIndex());
    }
    /**
     * 生成一个指定颜色的标题style
     *
     * @param workbook
     * @param foregroundColor
     * @param fontColor
     * @return
     */
    public static XSSFCellStyle getHeaderStyle(Workbook workbook, Color foregroundColor, short fontColor) {

        // 生成一个样式（用于标题）
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(new XSSFColor(foregroundColor));

        // 生成一个字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(fontColor);
        font.setFontHeightInPoints((short) 12);
        //设置字体大小
        font.setFontHeightInPoints((short) 13);
        //字体加粗.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        // 把字体应用到当前的样式
        style.setFont(font);
        //设置底边框;
        //style.setBorderBottom(BorderStyle.DASH_DOT);
        //设置底边框颜色;
        style.setBottomBorderColor(new XSSFColor(Color.BLACK));
        //设置左边框;
        // style.setBorderLeft(BorderStyle.DASH_DOT);
        //设置左边框颜色;
        style.setLeftBorderColor(new XSSFColor(Color.BLACK));
        //设置右边框;
        //style.setBorderRight(BorderStyle.DASH_DOT);
        //设置右边框颜色;
        style.setRightBorderColor(new XSSFColor(Color.BLACK));
        //设置顶边框;
        // style.setBorderTop(BorderStyle.DASH_DOT);
        //设置顶边框颜色;
        style.setTopBorderColor(new XSSFColor(Color.BLACK));
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;

    }

    /**
     * 用于生成内容格式
     * @param workbook
     * @return
     */
    public  static XSSFCellStyle getContentStyle(Workbook workbook){
        XSSFCellStyle style =(XSSFCellStyle) workbook.createCellStyle();
        style.setFillBackgroundColor(new XSSFColor(Color.gray));
//        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成一个字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontHeightInPoints((short) 12);
        //设置字体大小
        font.setFontHeightInPoints((short) 13);
        //字体加粗.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)
        //font.setBold(true);
        font.setFontHeight(12);
        //设置字体名字
        font.setFontName("Courier New");
        // 把字体应用到当前的样式
        style.setFont(font);

        return style;
    }

    /**
     * 生成excel并保存到指定位置
     * @param columnHeadings
     * @param sheet
     * @param workbook
     * @param bookName
     */
    private static String  deal(String[] columnHeadings, XSSFSheet sheet, XSSFWorkbook workbook, String bookName,String filePath) {
        //让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < columnHeadings.length; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null && !currentRow.getCell(colNum).equals("")) {
                    XSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.autoSizeColumn(colNum);
                sheet.setColumnWidth(colNum, (columnWidth + 2) * 256);
            } else {
                sheet.autoSizeColumn(colNum);
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }
        String  savePath="";
        try{
            String fileName=bookName+".xlsx";
            savePath=filePath+File.separator+fileName;
            System.out.println(savePath);
            OutputStream out=new FileOutputStream(savePath);
            workbook.write(out);
            out.flush();
        }catch (IOException e){
//            logger.error(e.getMessage());
        }
        return savePath;
    }
}
