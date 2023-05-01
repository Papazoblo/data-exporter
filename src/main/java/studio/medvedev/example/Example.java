package studio.medvedev.example;

import studio.medvedev.dataproexporterstarter.dto.ColumnCellStyle;
import studio.medvedev.dataproexporterstarter.dto.ColumnInfo;
import studio.medvedev.dataproexporterstarter.dto.ExportModel;
import studio.medvedev.dataproexporterstarter.dto.TableInfo;
import studio.medvedev.dataproexporterstarter.service.XlsxDocumentAnnotationBuilder;
import studio.medvedev.dataproexporterstarter.service.XlsxDocumentBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Example {
    public static void main(String[] args) throws IOException {

        byte[] byAnnotation = new XlsxDocumentAnnotationBuilder().export(Collections.singletonList(List.of(
                TestDto.of("Surname1", "Name1", "SecondName1"),
                TestDto.of("Surname2", "Name2", "SecondName2")
        )));

        byte[] byBuilders = new XlsxDocumentBuilder().export(build(List.of(
                TestDto.of("Surname1", "Name1", "SecondName1"),
                TestDto.of("Surname2", "Name2", "SecondName2")
        )).getList());
    }

    private static ExportModel build(Collection<Object> list) {
        return ExportModel.builder()
                .addTable(TableInfo.builder()
                        .listName("Тест")
                        .data(list)
                        .title("Тест билдера")
                        .withFilter()
                        .addColumn(ColumnInfo.builder()
                                .dataGetter(o -> ((TestDto) o).getFirstName())
                                .header("фест нейм")
                                .width(200)
                                .dataCellStyle(ColumnCellStyle.DEFAULT_STYLE)
                                .headerCellStyle(ColumnCellStyle.DEFAULT_STYLE)
                                .build())
                        .addColumn(ColumnInfo.builder()
                                .dataGetter(o -> ((TestDto) o).getSecondName())
                                .header("секонд нейм")
                                .width(400)
                                .dataCellStyle(ColumnCellStyle.DEFAULT_STYLE)
                                .headerCellStyle(ColumnCellStyle.DEFAULT_STYLE)
                                .build())
                        .addColumn(ColumnInfo.builder()
                                .dataGetter(o -> ((TestDto) o).getSurname())
                                .header("сернейм")
                                .width(600)
                                .dataCellStyle(ColumnCellStyle.DEFAULT_STYLE)
                                .headerCellStyle(ColumnCellStyle.DEFAULT_STYLE)
                                .build())
                        .build())
                .maxListRows(100)
                .build();
    }
}