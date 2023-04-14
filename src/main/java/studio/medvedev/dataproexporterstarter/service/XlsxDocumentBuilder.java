package studio.medvedev.dataproexporterstarter.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import studio.medvedev.dataproexporterstarter.dto.ColumnCellStyle;
import studio.medvedev.dataproexporterstarter.dto.ColumnInfo;
import studio.medvedev.dataproexporterstarter.dto.ListInfo;
import studio.medvedev.dataproexporterstarter.dto.TableInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static studio.medvedev.dataproexporterstarter.utils.SheetUtils.of;
import static studio.medvedev.dataproexporterstarter.utils.StringUtils.changeCharset;


public class XlsxDocumentBuilder {

    private static final int WINDOW_SIZE = 100;

    public byte[] export(List<ListInfo<?>> listInfo) throws IOException {

        SXSSFWorkbook workbook = new SXSSFWorkbook(WINDOW_SIZE);
        //todo вопросики
        workbook.setCompressTempFiles(true);

        if (listInfo.isEmpty()) {
            throw new IllegalStateException("Sheet list is empty");
        }
        listInfo.forEach(list -> buildSheetList(workbook, list));
        return buildOutput(workbook);
    }

    private void buildSheetList(SXSSFWorkbook workbook, ListInfo<?> listInfo) {
        SXSSFSheet sheet = workbook.createSheet(changeCharset(listInfo.getName()));
        TableInfo tableInfo = listInfo.getTableInfo();

        if (tableInfo.isWithFilter()) {
            sheet.setAutoFilter(new CellRangeAddress(tableInfo.isWithTitle() ? 1 : 0,
                    tableInfo.getData().size() + (tableInfo.isWithTitle() ? 1 : 0) + 1,
                    0, tableInfo.getColumnInfoList().size() - 1));
        }

        try {
            buildTitleRow(workbook, sheet, tableInfo);
            buildHeaderRow(workbook, sheet, tableInfo);
            buildDataRow(workbook, sheet, tableInfo);
        } catch (Exception ex) {
            throw new IllegalStateException("Error build xlsx document", ex);
        }
    }

    private static void buildTitleRow(SXSSFWorkbook workbook, SXSSFSheet sheet, TableInfo tableInfo) {
        if (!tableInfo.isWithTitle()) {
            return;
        }

        Row title = sheet.createRow(0);
        Cell titleCell = title.createCell(0);
        titleCell.setCellValue(changeCharset(tableInfo.getTitle()));
        titleCell.setCellStyle(of(workbook, ColumnCellStyle.DEFAULT_STYLE));
    }

    private static void buildHeaderRow(SXSSFWorkbook workbook, SXSSFSheet sheet, TableInfo tableInfo) {
        Row header = sheet.createRow(tableInfo.isWithTitle() ? 1 : 0);
        List<ColumnInfo> columns = tableInfo.getColumnInfoList();
        for (int i = 0; i < columns.size(); i++) {
            ColumnInfo column = columns.get(i);
            if (column.isAutoWidth()) {
                sheet.trackColumnForAutoSizing(i);
            }
            sheet.setColumnWidth(i, column.getWidth() * 100);
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(changeCharset(columns.get(i).getHeader()));
            headerCell.setCellStyle(of(workbook, columns.get(i).getHeaderCellStyle()));
            if (i == 0) {
                header.setHeight(columns.get(i).getHeaderCellStyle().getHeight());
            }
        }
    }

    private static void buildDataRow(SXSSFWorkbook workbook, SXSSFSheet sheet, TableInfo tableInfo) throws IOException {
        if (tableInfo.getData().isEmpty()) {
            return;
        }

        int startStartNum = tableInfo.isWithTitle() ? 2 : 1;
        for (int rowNum = startStartNum; rowNum < tableInfo.getData().size() + startStartNum; rowNum++) {

            Object data = tableInfo.getData().get(rowNum - startStartNum);
            Row row = sheet.createRow(rowNum);
            List<ColumnInfo> columns = tableInfo.getColumnInfoList();
            Map<Integer, CellStyle> cellStyleMap = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                ColumnInfo columnInfo = columns.get(i);
                Cell cell = row.createCell(i);
                CellStyle cellStyle = Optional.ofNullable(cellStyleMap.get(i)).orElseGet(() ->
                        of(workbook, columnInfo.getDataCellStyle()));

                if (columnInfo.isHide(data)) {
                    cell.setCellValue("hidden");
                } else {
                    String value = columnInfo.getData(data);
                    switch (columnInfo.getCellDataType()) {
                        case NUMERIC:
                            try {
                                Optional.ofNullable(value).ifPresent(s ->
                                        cell.setCellValue(Double.parseDouble(s.replace(',', '.'))));
                            } catch (NumberFormatException ex) {
                                cell.setCellValue("");
                            }
                            break;
                        case GENERAL:
                            cell.setCellValue(changeCharset(value));
                            break;
                    }
                }
                cell.setCellStyle(cellStyle);
                if (!cellStyleMap.containsKey(i)) {
                    cellStyleMap.put(i, cellStyle);
                }
            }

            if (rowNum % WINDOW_SIZE == 0) {
                sheet.flushRows(WINDOW_SIZE);
            }
        }

        for (int colNum = 0; colNum < tableInfo.getColumnInfoList().size(); colNum++) {
            ColumnInfo columnInfo = ((ColumnInfo) tableInfo.getColumnInfoList().get(colNum));
            if (columnInfo.isAutoWidth()) {
                sheet.autoSizeColumn(colNum);
            } else {
                if (sheet.getColumnWidth(colNum) > columnInfo.getMaxWidth() * 100) {
                    sheet.setColumnWidth(colNum, columnInfo.getMaxWidth() * 100);
                }
            }
        }
    }

    private static byte[] buildOutput(SXSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.dispose();
        workbook.close();
        return bos.toByteArray();
    }

}
