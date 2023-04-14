package studio.medvedev.dataproexporterstarter.service;

import studio.medvedev.dataproexporterstarter.annotation.Column;
import studio.medvedev.dataproexporterstarter.annotation.Exported;
import studio.medvedev.dataproexporterstarter.annotation.FontInfo;
import studio.medvedev.dataproexporterstarter.annotation.WithFilter;
import studio.medvedev.dataproexporterstarter.dto.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static studio.medvedev.dataproexporterstarter.utils.StringUtils.isNotBlank;


public class XlsxDocumentAnnotationBuilder {

    public byte[] export(List<List<Object>> data) throws IOException {

        XlsxDocumentBuilder xlsxDocumentBuilder = new XlsxDocumentBuilder();
        ExportModel.ExportModelBuilder exportModelBuilder = ExportModel.builder();

        data.stream()
                .filter(list -> !list.isEmpty())
                .filter(XlsxDocumentAnnotationBuilder::isExported)
                .forEach(item -> exportModelBuilder.addTable(buildListInfo(item,
                        item.get(0).getClass().getAnnotation(Exported.class))));

        return xlsxDocumentBuilder.export(exportModelBuilder.build().getList());
    }

    private static TableInfo<?> buildListInfo(List<Object> data, Exported exported) {
        Class<?> clazz = data.get(0).getClass();

        TableInfo.TableInfoBuilder<Object> tableInfoBuilder = TableInfo.builder()
                .data(data);
        addTableTitle(tableInfoBuilder, clazz);
        addFilter(tableInfoBuilder, clazz);
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> tableInfoBuilder.addColumn(parseColumnInfo(field, exported)));
        return tableInfoBuilder.build();
    }

    private static ColumnInfo<Object> parseColumnInfo(Field field, Exported exported) {
        Column column = field.getAnnotation(Column.class);

        return ColumnInfo.builder()
                .header(column.header())
                .cellDataType(column.type())
                .dataCellStyle(parseCellStyle(column, exported, false))
                .headerCellStyle(parseCellStyle(column, exported, true))
                .dataGetter(item -> {
                    try {
                        field.setAccessible(true);
                        return getDataGetter(field.get(item));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .code(null)
                .maxWidth(column.maxWidth())
                .width(column.width())
                .autoWidth(column.autoWidth())
                .build();
    }

    private static void addFilter(TableInfo.TableInfoBuilder<?> tableBuilder, Class<?> clazz) {
        if (clazz.isAnnotationPresent(WithFilter.class)) {
            tableBuilder.withFilter();
        }
    }

    private static void addTableTitle(TableInfo.TableInfoBuilder<?> tableBuilder, Class<?> clazz) {
        if (clazz.isAnnotationPresent(Exported.class)) {
            Exported exported = clazz.getAnnotation(Exported.class);
            if (isNotBlank(exported.title())) {
                tableBuilder.title(exported.title());
            }
        }
    }

    private static ColumnCellStyle parseCellStyle(Column column, Exported exported, boolean isHeader) {
        return ColumnCellStyle.builder()
                .dataFormat(column.dataFormat())
                .verticalAlignment(column.verticalAlignment())
                .horizontalAlignment(column.horizontalAlignment())
                .borderRight(column.borderInfo().right())
                .borderLeft(column.borderInfo().left())
                .borderTop(column.borderInfo().top())
                .borderBottom(column.borderInfo().bottom())
                .fontStyle(parseFontStyle(isHeader ? column.headerFont() : column.dataFont()))
                .height(isHeader ? exported.headerHeight() : exported.dataHeight())
                .build();
    }

    private static FontStyle parseFontStyle(FontInfo fontInfo) {
        return FontStyle.builder()
                .bold(fontInfo.bold())
                .fontName(fontInfo.name())
                .fontSize(fontInfo.size())
                .build();
    }

    private static String getDataGetter(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        throw new IllegalArgumentException("Field must be a String type");
    }

    private static boolean isExported(List<Object> data) {
        Class<?> clazz = data.get(0).getClass();
        return clazz.isAnnotationPresent(Exported.class);
    }
}
