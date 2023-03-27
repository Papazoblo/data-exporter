package studio.medvedev.dataproexporterstarter.dto;


import studio.medvedev.dataproexporterstarter.enums.CellDataType;

import java.util.function.Function;

public class ColumnInfo<DATA> {

    private String code;
    private String header;
    private int width = 100;
    private Integer maxWidth;
    private boolean autoWidth = false;
    private CellDataType cellDataType = CellDataType.GENERAL;
    private ColumnCellStyle headerCellStyle;
    private ColumnCellStyle dataCellStyle;
    private Function<DATA, Boolean> hideInfoFunction;
    private Function<DATA, String> dataGetter;

    public boolean isHide(DATA data) {
        if (hideInfoFunction == null) {
            return false;
        }
        return hideInfoFunction.apply(data);
    }

    public String getData(DATA data) {
        return dataGetter.apply(data);
    }

    public CellDataType getCellDataType() {
        return cellDataType;
    }

    public ColumnCellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }

    public ColumnCellStyle getDataCellStyle() {
        return dataCellStyle;
    }

    public String getCode() {
        return code;
    }

    public String getHeader() {
        return header;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public boolean isAutoWidth() {
        return autoWidth;
    }

    public static <DATA> ColumnInfoBuilder<DATA> builder() {
        return new ColumnInfoBuilder<>();
    }

    private ColumnInfo() {
        //закрытый конструктор
    }

    public static class ColumnInfoBuilder<DATA> {

        private final ColumnInfo<DATA> columnInfo;

        private ColumnInfoBuilder() {
            this.columnInfo = new ColumnInfo<>();
        }

        public ColumnInfoBuilder<DATA> code(String code) {
            this.columnInfo.code = code;
            return this;
        }

        public ColumnInfoBuilder<DATA> header(String header) {
            this.columnInfo.header = header;
            return this;
        }

        public ColumnInfoBuilder<DATA> width(int width) {
            this.columnInfo.width = width;
            return this;
        }

        public ColumnInfoBuilder<DATA> maxWidth(int maxWidth) {
            this.columnInfo.maxWidth = maxWidth;
            return this;
        }

        public ColumnInfoBuilder<DATA> autoWidth(boolean autoWidth) {
            this.columnInfo.autoWidth = autoWidth;
            return this;
        }

        public ColumnInfoBuilder<DATA> cellDataType(CellDataType dataType) {
            this.columnInfo.cellDataType = dataType;
            return this;
        }

        public ColumnInfoBuilder<DATA> headerCellStyle(ColumnCellStyle value) {
            this.columnInfo.headerCellStyle = value;
            return this;
        }

        public ColumnInfoBuilder<DATA> dataCellStyle(ColumnCellStyle value) {
            this.columnInfo.dataCellStyle = value;
            return this;
        }

        public ColumnInfoBuilder<DATA> hideInfoFunction(Function<DATA, Boolean> value) {
            this.columnInfo.hideInfoFunction = value;
            return this;
        }

        public ColumnInfoBuilder<DATA> dataGetter(Function<DATA, String> value) {
            this.columnInfo.dataGetter = value;
            return this;
        }

        public ColumnInfo<DATA> build() {
            return this.columnInfo;
        }
    }
}
