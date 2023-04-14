package studio.medvedev.example;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import studio.medvedev.dataproexporterstarter.annotation.*;

@Exported(headerHeight = 1000)
@WithFilter
public class TestDto {

    @Column(header = "Первое имя",
            width = 50,
            borderInfo = @BorderInfo(
                    bottom = BorderStyle.MEDIUM,
                    top = BorderStyle.THIN,
                    left = BorderStyle.THIN,
                    right = BorderStyle.THIN),
            headerFont = @FontInfo(
                    bold = true,
                    size = 12
            ))
    private String firstName;
    @Column(header = "Отчетсво",
            borderInfo = @BorderInfo(
                    bottom = BorderStyle.MEDIUM,
                    top = BorderStyle.THIN,
                    left = BorderStyle.THIN,
                    right = BorderStyle.THIN),
            headerFont = @FontInfo(
                    bold = true,
                    size = 10
            ),
            horizontalAlignment = HorizontalAlignment.CENTER)
    private String secondName;
    @Column(header = "Фамилия",
            borderInfo = @BorderInfo(
                    bottom = BorderStyle.MEDIUM,
                    top = BorderStyle.THIN,
                    left = BorderStyle.THIN,
                    right = BorderStyle.THIN),
            headerFont = @FontInfo(
                    bold = true,
                    size = 8
            ))
    private String surname;

    public static TestDto of(String arg1, String arg2, String arg3) {
        TestDto dto = new TestDto();
        dto.surname = arg1;
        dto.firstName = arg2;
        dto.secondName = arg3;
        return dto;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getSurname() {
        return surname;
    }
}
