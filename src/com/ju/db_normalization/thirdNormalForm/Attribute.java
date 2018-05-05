import java.util.Objects;

public class Attribute {

    private char attribute;
    private String location;


    public Attribute(char attribute) {
        this.attribute = attribute;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public char getAttribute() {
        return attribute;
    }

    public void setAttribute(char attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return String.valueOf(attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute1 = (Attribute) o;
        return attribute == attribute1.attribute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }
}
