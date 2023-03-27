package studio.medvedev.dataproexporterstarter.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import studio.medvedev.dataproexporterstarter.dto.ColumnCellStyle;
import studio.medvedev.dataproexporterstarter.dto.FontStyle;

public class SheetUtils {

    public static CellStyle of(SXSSFWorkbook workbook, ColumnCellStyle style) {

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(style.isWrapText());
        cellStyle.setAlignment(style.getHorizontalAlignment());
        cellStyle.setVerticalAlignment(style.getVerticalAlignment());
        cellStyle.setBorderBottom(style.getBorderBottom());
        cellStyle.setBorderLeft(style.getBorderLeft());
        cellStyle.setBorderRight(style.getBorderRight());
        cellStyle.setBorderTop(style.getBorderTop());
        cellStyle.setFont(of(workbook, style.getFontStyle()));
        if (style.getFillPatternType() != null) {
            cellStyle.setFillForegroundColor(style.getFillForegroundColor());
            cellStyle.setFillPattern(style.getFillPatternType());
        }
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat(style.getDataFormat()));
        return cellStyle;
    }

    public static Font of(SXSSFWorkbook workbook, FontStyle style) {
        Font font = workbook.createFont();
        font.setFontName(style.getFontName());
        font.setFontHeightInPoints(style.getFontSize());
        font.setBold(style.isBold());
        return font;
    }

    private SheetUtils() {
        //закрытый конструктор
    }
}
