package org.takeback.util.export;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class ExcelUtil {

    private static HSSFWorkbook wb;

    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体
    private static CellStyle dateStyle; // 日期行样式
    private static Font dateFont; // 日期行字体
    private static CellStyle headStyle; // 表头行样式
    private static Font headFont; // 表头行字体
    private static CellStyle contentStyle; // 内容行样式
    private static Font contentFont; // 内容行字体

    private static CellStyle infoStyle; // 表头行样式
    /**
     * 导出文件
     * @param setInfo
     * @param outputExcelFileName
     * @return
     * @throws IOException
     */
    public static boolean export2File(ExcelExportData setInfo,
                                      String outputExcelFileName) throws Exception {
        return FileUtil.write(outputExcelFileName, export2ByteArray(setInfo),
                true, true);
    }

    /**
     * 导出到byte数组
     *
     * @param setInfo
     * @return
     * @throws Exception
     */
    public static byte[] export2ByteArray(ExcelExportData setInfo)
            throws Exception {
        return export2Stream(setInfo).toByteArray();
    }

    /**
     * 导出到流
     *
     * @param setInfo
     * @return
     * @throws Exception
     */
    public static ByteArrayOutputStream export2Stream(ExcelExportData setInfo)
            throws Exception {
        init();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Set<Entry<String, List<?>>> set = setInfo.getDataMap().entrySet();
        String[] sheetNames = new String[setInfo.getDataMap().size()];
        int sheetNameNum = 0;
        for (Entry<String, List<?>> entry : set) {
            sheetNames[sheetNameNum] = entry.getKey();
            sheetNameNum++;
        }
        HSSFSheet[] sheets = getSheets(setInfo.getDataMap().size(), sheetNames);
        int sheetNum = 0;
        for (Entry<String, List<?>> entry : set) {
            // Sheet
            List<?> objs = entry.getValue();

            // 标题行
            createTableTitleRow(setInfo, sheets, sheetNum);

            // 日期行
//            createTableDateRow(setInfo, sheets, sheetNum);

            //基本信息行
            if(setInfo.getInfo()!= null && setInfo.getInfo().size() != 0){
                createTableInfoRow(setInfo, sheets, sheetNum);
            }

            // 表头
            creatTableHeadRow(setInfo, sheets, sheetNum);

            // 表体
            String[] fieldNames = setInfo.getFieldNames().get(sheetNum);

            int rowNum = 4;
            for (Object obj : objs) {
                HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
                contentRow.setHeight((short) 300);
                HSSFCell[] cells = getCells(contentRow, setInfo.getFieldNames()
                        .get(sheetNum).length);
                int cellNum = 1; // 去掉一列序号，因此从1开始
                if (fieldNames != null) {
                    for (int num = 0; num < fieldNames.length; num++) {
                        Object value = null;
                        if(obj instanceof Map){
                            value = ((Map) obj).get(fieldNames[num]);
                        }else{
                            value = ReflectionUtil.invokeGetterMethod(obj,
                                    fieldNames[num]);
                        }

                        cells[cellNum].setCellValue(value == null ? "" : value
                                .toString());
                        cellNum++;
                    }
                }
                rowNum++;
            }

            // 总计部分
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(setInfo.getStatistics())) {
                creatTableStatisticsRow(setInfo, sheets, sheetNum, rowNum);
            }

            adjustColumnSize(sheets, sheetNum, fieldNames); // 自动调整列宽
            sheetNum++;
        }
        wb.write(outputStream);
        return outputStream;
    }

    /**
     * @Description: 初始化
     */
    private static void init() {
        wb = new HSSFWorkbook();

        titleFont = wb.createFont();
        titleStyle = wb.createCellStyle();
        dateStyle = wb.createCellStyle();
        dateFont = wb.createFont();
        headStyle = wb.createCellStyle();
        headFont = wb.createFont();
        contentStyle = wb.createCellStyle();
        contentFont = wb.createFont();
        infoStyle = wb.createCellStyle();

        initTitleCellStyle();
        initTitleFont();
        initDateCellStyle();
        initDateFont();
        initHeadCellStyle();
        initHeadFont();
        initContentCellStyle();
        initContentFont();
        initInfoCellStyle();
    }

    /**
     * @Description: 自动调整列宽
     */
    private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum,
                                         String[] fieldNames) {
        for (int i = 0; i < fieldNames.length + 1; i++) {
            sheets[sheetNum].autoSizeColumn(i, true);
        }
    }

    /**
     * @Description: 创建标题行(需合并单元格)
     */
    private static void createTableTitleRow(ExcelExportData setInfo,
                                            HSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, setInfo
                .getFieldNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(titleRange);
        HSSFRow titleRow = sheets[sheetNum].createRow(0);
        titleRow.setHeight((short) 800);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
    }

    /**
     * @Description: 创建日期行(需合并单元格)
     */
    private static void createTableDateRow(ExcelExportData setInfo,
                                           HSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, setInfo
                .getFieldNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(dateRange);
        HSSFRow dateRow = sheets[sheetNum].createRow(1);
        dateRow.setHeight((short) 350);
        HSSFCell dateCell = dateRow.createCell(0);
        dateCell.setCellStyle(dateStyle);
        dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date()));
    }


    /**
     * @Description: 创建基本信息(写死的)
     */
    private static void createTableInfoRow(ExcelExportData setInfo,
                                           HSSFSheet[] sheets, int sheetNum) {

        HSSFRow dateRow = sheets[sheetNum].createRow(1);

        for(int i =0 ;i < 6 ; i++){
            if(i == 3) {
                dateRow = sheets[sheetNum].createRow(2);
            }
            for(int j =0 ;j < 2; j++) {
                CellRangeAddress dateRange = new CellRangeAddress((i+3)/3, (i+3)/3, (i%3)*4+j, (i%3)*4+(j*3));
                sheets[sheetNum].addMergedRegion(dateRange);//0:0 0  0:
                dateRow.setHeight((short) 600);
                HSSFCell dateCell = dateRow.createCell((i%3)*4+j);
                dateCell.setCellValue(setInfo.getInfo().get(i).get(j == 0 ? "text" : "value"));
                dateCell.setCellStyle(infoStyle);
            }

        }
    }


    /**
     * @Description: 创建表头行(需合并单元格)
     */
    private static void creatTableHeadRow(ExcelExportData setInfo,
                                          HSSFSheet[] sheets, int sheetNum) {
        // 表头
        HSSFRow headRow = sheets[sheetNum].createRow(3);
        headRow.setHeight((short) 350);
        // 序号列
        HSSFCell snCell = headRow.createCell(0);
        snCell.setCellStyle(headStyle);
        snCell.setCellValue("序号");
        // 列头名称
        for (int num = 1, len = setInfo.getColumnNames().get(sheetNum).length; num <= len; num++) {
            HSSFCell headCell = headRow.createCell(num);
            headCell.setCellStyle(headStyle);
            headCell.setCellValue(setInfo.getColumnNames().get(sheetNum)[num - 1]);
        }
    }

    /**
     * @Description: 创建所有的Sheet
     */
    private static HSSFSheet[] getSheets(int num, String[] names) {
        HSSFSheet[] sheets = new HSSFSheet[num];
        for (int i = 0; i < num; i++) {
            sheets[i] = wb.createSheet(names[i]);
        }
        return sheets;
    }

    /**
     * @Description: 创建内容行的每一列(附加一列序号)
     */
    private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
        HSSFCell[] cells = new HSSFCell[num + 1];

        for (int i = 0, len = cells.length; i < len; i++) {
            cells[i] = contentRow.createCell(i);
            cells[i].setCellStyle(contentStyle);
        }
        // 设置序号列值，因为出去标题行和日期行，所有-2
        cells[0].setCellValue(contentRow.getRowNum() - 3);
        return cells;
    }

    /**
     * 表格底部总计
     */
    private static void creatTableStatisticsRow(ExcelExportData setInfo,
                                                HSSFSheet[] sheets, int sheetNum,int num){
        CellRangeAddress dateRange = new CellRangeAddress(num, num, 0, setInfo
                .getFieldNames().get(sheetNum).length);

        sheets[sheetNum].addMergedRegion(dateRange);

        HSSFRow dateRow = sheets[sheetNum].createRow(num);
        dateRow.setHeight((short) 400);
        HSSFCell dateCell = dateRow.createCell(0);
        dateCell.setCellStyle(infoStyle);
        dateCell.setCellValue(setInfo.getStatistics());
    }

    /**
     * @Description: 初始化标题行样式
     */
    private static void initTitleCellStyle() {
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
    }

    /**
     * @Description: 初始化日期行样式
     */
    private static void initDateCellStyle() {
        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateStyle.setFont(dateFont);
        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
    }

    /**
     * @Description: 初始化表头行样式
     */
    private static void initHeadCellStyle() {
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headStyle.setFont(headFont);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLUE.index);
        headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
        headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
        headStyle.setRightBorderColor(IndexedColors.BLUE.index);
    }


    private static void initInfoCellStyle() {
        infoStyle.setAlignment(CellStyle.ALIGN_CENTER);
        infoStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        infoStyle.setFont(headFont);
        infoStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
        infoStyle.setWrapText(true); // 字段换行
    }

    /**
     * @Description: 初始化内容行样式
     */
    private static void initContentCellStyle() {
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
        contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
        contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
        contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
        contentStyle.setWrapText(true); // 字段换行
    }

    /**
     * @Description: 初始化标题行字体
     */
    private static void initTitleFont() {
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化日期行字体
     */
    private static void initDateFont() {
        dateFont.setFontName("隶书");
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        dateFont.setCharSet(Font.DEFAULT_CHARSET);
        dateFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setCharSet(Font.DEFAULT_CHARSET);
        headFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * @Description: 初始化内容行字体
     */
    private static void initContentFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setCharSet(Font.DEFAULT_CHARSET);
        contentFont.setColor(IndexedColors.BLUE_GREY.index);
    }

    /**
     * Excel导出数据类
     *
     * @author jimmy
     *
     */
    public static class ExcelExportData {

        /**
         * 导出数据 key:String 表示每个Sheet的名称 value:List<?> 表示每个Sheet里的所有数据行
         */
        private LinkedHashMap<String, List<?>> dataMap;

        /**
         * 每个Sheet里的顶部大标题
         */
        private String[] titles;

        /**
         * 单个sheet里的数据列标题
         */
        private List<String[]> columnNames;

        /**
         * 基本信息
         */
        private List<HashMap<String,String>> info;


        private String statistics;
        /**
         * 单个sheet里每行数据的列对应的对象属性名称
         */
        private List<String[]> fieldNames;

        public List<String[]> getFieldNames() {
            return fieldNames;
        }

        public void setFieldNames(List<String[]> fieldNames) {
            this.fieldNames = fieldNames;
        }

        public String[] getTitles() {
            return titles;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public List<String[]> getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(List<String[]> columnNames) {
            this.columnNames = columnNames;
        }

        public LinkedHashMap<String, List<?>> getDataMap() {
            return dataMap;
        }

        public void setDataMap(LinkedHashMap<String, List<?>> dataMap) {
            this.dataMap = dataMap;
        }

        public List<HashMap<String,String>> getInfo() {
            return info;
        }

        public void setInfo(List<HashMap<String,String>> info) {
            this.info = info;
        }

        public String getStatistics() {
            return statistics;
        }

        public void setStatistics(String statistics) {
            this.statistics = statistics;
        }
    }
}