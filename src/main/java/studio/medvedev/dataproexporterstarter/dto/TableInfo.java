package studio.medvedev.dataproexporterstarter.dto;

import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

public class TableInfo<DATA> {

    private boolean withOrder = false;
    private boolean withTitle = false;
    private boolean withFilter = false;
    private String title;
    private String listName;
    private List<ColumnInfo<DATA>> columnInfoList = new ArrayList<>();
    private Set<String> columnOrder;
    private List<DATA> data = new ArrayList<>();

    public String getTitle() {
        return this.title;
    }

    public List<ColumnInfo<DATA>> getColumnInfoList() {
        if (isWithOrder()) {
            return columnOrder.stream()
                    .map(code -> this.columnInfoList.stream()
                            .filter(column -> column.getCode() != null && column.getCode().equalsIgnoreCase(code))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return this.columnInfoList;
        }
    }

    public Set<String> getColumnOrder() {
        return this.columnOrder;
    }

    public List<DATA> getData() {
        return this.data;
    }

    public String getListName() {
        return this.listName;
    }

    public boolean isWithFilter() {
        return withFilter;
    }

    public boolean isWithTitle() {
        return withTitle;
    }

    public boolean isWithOrder() {
        return withOrder;
    }

    public static <DATA> TableInfoBuilder<DATA> builder() {
        return new TableInfoBuilder<>();
    }

    private TableInfo() {

    }

    public static class TableInfoBuilder<DATA> {

        private final TableInfo<DATA> tableInfo;

        private TableInfoBuilder() {
            this.tableInfo = new TableInfo<>();
        }

        public TableInfoBuilder<DATA> title(String value) {
            this.tableInfo.title = value;
            if (Strings.isNotBlank(value)) {
                this.tableInfo.withTitle = true;
            }
            return this;
        }

        public TableInfoBuilder<DATA> listName(String value) {
            this.tableInfo.listName = value;
            return this;
        }

        public TableInfoBuilder<DATA> data(Collection<DATA> data) {
            this.tableInfo.data.addAll(data);
            return this;
        }

        public TableInfoBuilder<DATA> addColumn(ColumnInfo<DATA> value) {
            this.tableInfo.columnInfoList.add(value);
            return this;
        }

        public TableInfoBuilder<DATA> columnOrder(Set<String> value) {
            this.tableInfo.columnOrder = value;
            this.tableInfo.withOrder = value != null;
            return this;
        }

        public TableInfoBuilder<DATA> withFilter() {
            this.tableInfo.withFilter = true;
            return this;
        }

        public TableInfo<DATA> build() {

            if (this.tableInfo.withOrder) {
                if (!this.tableInfo.columnOrder.stream()
                        .allMatch(item -> this.tableInfo.columnInfoList.stream()
                                .anyMatch(column -> column.getCode() != null
                                        && column.getCode().equalsIgnoreCase(item)))) {
                    throw new IllegalArgumentException("Column list is missing a column from the columns order");
                }
            }
            return this.tableInfo;
        }
    }
}
