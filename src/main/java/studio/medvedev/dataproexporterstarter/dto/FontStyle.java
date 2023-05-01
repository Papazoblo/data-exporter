
package studio.medvedev.dataproexporterstarter.dto;

public class FontStyle {

    public static FontStyle DEFAULT_STYLE;

    static {
        DEFAULT_STYLE = FontStyle.builder().build();
    }

    private String fontName = "Arial";
    private short fontSize = 10;
    private boolean bold = false;

    public String getFontName() {
        return fontName;
    }

    public short getFontSize() {
        return fontSize;
    }

    public boolean isBold() {
        return bold;
    }

    public static FontStyleBuilder builder() {
        return new FontStyleBuilder();
    }

    private FontStyle() {
//закрытый конструктор
    }

    public static class FontStyleBuilder {

        private final FontStyle style;

        private FontStyleBuilder() {
            this.style = new FontStyle();
        }

        public FontStyleBuilder fontName(String value) {
            this.style.fontName = value;
            return this;
        }

        public FontStyleBuilder fontSize(short value) {
            this.style.fontSize = value;
            return this;
        }

        public FontStyleBuilder bold(boolean value) {
            this.style.bold = value;
            return this;
        }

        public FontStyle build() {
            return this.style;
        }
    }
}
