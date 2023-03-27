package studio.medvedev.dataproexporterstarter.dto;

import org.apache.poi.ss.usermodel.*;

public class ColumnCellStyle {

    public static ColumnCellStyle DEFAULT_STYLE;

    static {
        DEFAULT_STYLE = ColumnCellStyle.builder()
                .dataFormat("")
                .fillForeground(IndexedColors.WHITE.getIndex())
                .fillPatternType(FillPatternType.SOLID_FOREGROUND)
                .borderBottom(BorderStyle.THIN)
                .borderTop(BorderStyle.THIN)
                .borderLeft(BorderStyle.THIN)
                .borderRight(BorderStyle.THIN)
                .horizontalAlignment(HorizontalAlignment.LEFT)
                .verticalAlignment(VerticalAlignment.CENTER)
                .fontStyle(FontStyle.DEFAULT_STYLE)
                .build();
    }

    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
    private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
    private boolean wrapText = false;
    private short height = 100;
    private BorderStyle borderBottom = BorderStyle.NONE;
    private BorderStyle borderTop = BorderStyle.NONE;
    private BorderStyle borderLeft = BorderStyle.NONE;
    private BorderStyle borderRight = BorderStyle.NONE;
    private FillPatternType fillPatternType;
    private short fillForegroundColor;
    private FontStyle fontStyle;
    private String dataFormat;

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public boolean isWrapText() {
        return wrapText;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public BorderStyle getBorderBottom() {
        return borderBottom;
    }

    public BorderStyle getBorderTop() {
        return borderTop;
    }

    public BorderStyle getBorderLeft() {
        return borderLeft;
    }

    public BorderStyle getBorderRight() {
        return borderRight;
    }

    public FillPatternType getFillPatternType() {
        return fillPatternType;
    }

    public short getFillForegroundColor() {
        return fillForegroundColor;
    }

    public short getHeight() {
        return height;
    }

    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public static ColumnCellStyleBuilder builder() {
        return new ColumnCellStyleBuilder();
    }

    private ColumnCellStyle() {
        //закрытый конструктор
    }

    public static class ColumnCellStyleBuilder {

        private final ColumnCellStyle style;

        private ColumnCellStyleBuilder() {
            this.style = new ColumnCellStyle();
        }

        public ColumnCellStyleBuilder horizontalAlignment(HorizontalAlignment value) {
            this.style.horizontalAlignment = value;
            return this;
        }

        public ColumnCellStyleBuilder verticalAlignment(VerticalAlignment value) {
            this.style.verticalAlignment = value;
            return this;
        }

        public ColumnCellStyleBuilder wrapText(boolean value) {
            this.style.wrapText = value;
            return this;
        }

        public ColumnCellStyleBuilder borderBottom(BorderStyle value) {
            this.style.borderBottom = value;
            return this;
        }

        public ColumnCellStyleBuilder borderTop(BorderStyle value) {
            this.style.borderTop = value;
            return this;
        }

        public ColumnCellStyleBuilder borderLeft(BorderStyle value) {
            this.style.borderLeft = value;
            return this;
        }

        public ColumnCellStyleBuilder borderRight(BorderStyle value) {
            this.style.borderRight = value;
            return this;
        }

        public ColumnCellStyleBuilder fillPatternType(FillPatternType value) {
            this.style.fillPatternType = value;
            return this;
        }

        public ColumnCellStyleBuilder fillForeground(short value) {
            this.style.fillForegroundColor = value;
            return this;
        }

        public ColumnCellStyleBuilder dataFormat(String format) {
            this.style.dataFormat = format;
            return this;
        }

        public ColumnCellStyleBuilder height(short value) {
            this.style.height = value;
            return this;
        }

        public ColumnCellStyleBuilder fontStyle(FontStyle value) {
            this.style.fontStyle = value;
            return this;
        }

        public ColumnCellStyle build() {
            return this.style;
        }
    }
}
