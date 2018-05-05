import java.util.*;

public class FunctionalDependency {
    private Set<Attribute> lhs = new HashSet<>();
    private Set<Attribute> rhs = new HashSet<>();

    FunctionalDependency(Set<Attribute> lhs, Set<Attribute> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    private FunctionalDependency() {

    }

    Set<Attribute> getLhs() {
        return lhs;
    }

    public void setLhs(Set<Attribute> lhs) {
        this.lhs = lhs;
    }

    Set<Attribute> getRhs() {
        return rhs;
    }

    void setRhs(Set<Attribute> rhs) {
        this.rhs = rhs;
    }

    public void addRhs(Set<Attribute> rhs) {
        this.rhs.addAll(rhs);
    }

    static FunctionalDependency convertStringToFd(String fd) {
        String[] strings = fd.split("->");
        FunctionalDependency fdd = new FunctionalDependency();
        HashSet<Attribute> lhs = new HashSet<>();
        for (char c : strings[0].toCharArray()) {
            lhs.add(new Attribute(c));
        }
        fdd.setLhs(lhs);
        HashSet<Attribute> rhs = new HashSet<>();
        for (char c : strings[1].toCharArray()) {
            rhs.add(new Attribute(c));
        }
        fdd.setRhs(rhs);
        return fdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionalDependency that = (FunctionalDependency) o;
        return Objects.equals(lhs, that.lhs) &&
                Objects.equals(rhs, that.rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs, rhs);
    }

    @Override
    public String toString() {
        return "{" + lhs + "-->" + rhs + '}';
    }
}
