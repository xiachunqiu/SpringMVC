package com.x.y.common;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewExcel extends AbstractExcelView {
    public static final String MODEL_ATTR_NAME = "modelname";

    @Override
    protected void buildExcelDocument(Map<String, Object> obj, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExcelViewModel viewMode = (ExcelViewModel) obj.get(MODEL_ATTR_NAME);
        for (ExcelViewModelSheetData sheetData : viewMode.sheets) {
            int rowIndex = 0;
            HSSFSheet sheet = workbook.createSheet(sheetData.name); // sheet
            if (sheetData.titles != null) {
                for (int i = 0; i < sheetData.titles.length; i++) {
                    HSSFCell cell = getCell(sheet, rowIndex, i);
                    cell.setCellValue(sheetData.titles[i]);
                }
                rowIndex++;
            }
            for (int i = 0, size = sheetData.list.size(); i < size; i++) {
                Object domain = sheetData.list.get(i);
                String[] rowValues = sheetData.cb.writeRowData(i, domain);
                for (int cellidx = 0, cellsize = rowValues.length; cellidx < cellsize; cellidx++) {
                    HSSFCell cell = getCell(sheet, rowIndex, cellidx);
                    cell.setCellValue(rowValues[cellidx]);
                }
                rowIndex++;
            }
            if (sheetData.titles != null) {
                for (int i = 0, len = sheetData.titles.length; i < len; i++) {
                    sheet.autoSizeColumn(i);
                }
            }
        }
        String filename = viewMode.filename;
        if (filename == null) {
            filename = System.currentTimeMillis() + ".xls";
        }
        if (!StringUtils.endsWith(filename, ".xls")) {
            filename = filename + ".xls";
        }
        filename = encodeFilename(filename, request);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private String encodeFilename(String filename, HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (agent.contains("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (agent.contains("Mozilla"))) {
                return MimeUtility.encodeText(filename, "UTF-8", "B");
            }
            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }

    public static class ExcelViewModel {
        private final List<ExcelViewModelSheetData> sheets = new ArrayList<>();
        private String filename;

        public ExcelViewModel filename(String filename) {
            this.filename = filename;
            return this;
        }

        public ExcelViewModelSheetData addSheet(String sheetName, List list, RowDataCallback cb) {
            ExcelViewModelSheetData sheet = new ExcelViewModelSheetData();
            sheet.name = sheetName;
            sheet.list = list;
            sheet.cb = cb;
            sheets.add(sheet);
            return sheet;
        }
    }

    public static class ExcelViewModelSheetData {
        private String name;
        private List list;
        private String[] titles;
        private RowDataCallback cb;

        public void titles(String[] titles) {
            this.titles = titles;
        }
    }

    public interface RowDataCallback {
        String[] writeRowData(int idx, Object o);
    }
}