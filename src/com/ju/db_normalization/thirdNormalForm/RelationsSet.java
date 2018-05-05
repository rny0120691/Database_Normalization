import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class RelationsSet {

    private Set<Relation> relations = new LinkedHashSet<>();

    public void setRelations(Set<Relation> relations) {
        this.relations = relations;
    }

    public void addRelation(Relation relation) {
        this.relations.add(relation);
    }

    public Set<Relation> getRelations() {
        return relations;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        Iterator<Relation> iterator = relations.iterator();
        while (iterator.hasNext()) {
            Relation relation = iterator.next();
            stringBuilder.append("Relation").append(" ").append(i).append(relation);
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
            i++;
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelationsSet)) return false;
        RelationsSet that = (RelationsSet) o;
        return Objects.equals(getRelations(), that.getRelations());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRelations());
    }
}
