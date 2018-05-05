import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Relation {

    private Set<Attribute> attributes = new HashSet<>();

    Relation(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    Relation() {
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void add(Attribute attribute) {
        this.attributes.add(attribute);
    }

    void addAll(Set<Attribute> attribute) {
        this.attributes.addAll(attribute);
    }


    @Override
    public String toString() {
        return ""+ attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relation)) return false;
        Relation relation = (Relation) o;
        return Objects.equals(getAttributes(), relation.getAttributes());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAttributes());
    }
}
