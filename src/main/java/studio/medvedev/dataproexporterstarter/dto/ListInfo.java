package studio.medvedev.dataproexporterstarter.dto;

public class ListInfo<DATA> {

    private String name;
    private TableInfo<DATA> tableInfo;
    private int startIndex;
    private int lastIndex;

    public String getName() {
        return name;
    }

    public TableInfo<DATA> getTableInfo() {
        return tableInfo;
    }

    public static <DATA> ListInfoBuilder<DATA> builder() {
        return new ListInfoBuilder<>();
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public static class ListInfoBuilder<DATA> {

        private final ListInfo<DATA> info;

        private ListInfoBuilder() {
            info = new ListInfo<>();
        }

        public ListInfoBuilder<DATA> name(String name) {
            this.info.name = name;
            return this;
        }

        public ListInfoBuilder<DATA> tableInfo(TableInfo<DATA> tableInfo) {
            this.info.tableInfo = tableInfo;
            return this;
        }

        public ListInfoBuilder<DATA> startIndex(int index) {
            this.info.startIndex = index;
            return this;
        }

        public ListInfoBuilder<DATA> lastIndex(int index) {
            this.info.lastIndex = index;
            return this;
        }

        public ListInfo<DATA> build() {
            return this.info;
        }
    }
}
