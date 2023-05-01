package studio.medvedev.dataproexporterstarter.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExportModel {

    private static final int MAX_LIST_ROWS = 1_000_000;
    private List<ListInfo<?>> list = new ArrayList<>();
    private int maxListRows = MAX_LIST_ROWS;

    public static ExportModelBuilder builder() {
        return new ExportModelBuilder();
    }

    private ExportModel() {

    }

    public List<ListInfo<?>> getList() {
        return list;
    }

    public static class ExportModelBuilder {

        private final ExportModel model;
        private final List<TableInfo<?>> tableList = new ArrayList<>();

        private ExportModelBuilder() {
            this.model = new ExportModel();
        }

        public static ExportModelBuilder builder() {
            return new ExportModelBuilder();
        }

        public ExportModelBuilder addTable(TableInfo<?> table) {
            this.tableList.add(table);
            return this;
        }

        public ExportModelBuilder maxListRows(int value) {
            if (value < 0 || value > 1_048_576 - 3) {
                throw new IllegalArgumentException();
            }
            this.model.maxListRows = value;
            return this;
        }

        public ExportModel build() {
            for (TableInfo tableInfo : tableList) {
                int tableInfoDataSize = tableInfo.getData().size();
                for (int i = 0, j = 0; i < tableInfoDataSize; i += this.model.maxListRows, j++) {
                    this.model.list.add(ListInfo.builder()
                            .tableInfo(tableInfo)
                            .name(String.format("%s #%d", Optional.ofNullable(tableInfo.getListName()).orElse(""), j).trim())
                            .startIndex(i)
                            .lastIndex(Math.min((i + this.model.maxListRows), tableInfoDataSize))
                            .build());
                }
            }
            return this.model;
        }
    }
}
