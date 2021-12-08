import java.util.Objects;

public class Product {
    private int code;
    private String title;


    public Product(int code, String title) {
        this.code = code;
        this.title = title;
    }


    public int getCode() {
        return code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code == product.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code=" + code +
                ", title='" + title + '\'' +
                '}';
    }


}