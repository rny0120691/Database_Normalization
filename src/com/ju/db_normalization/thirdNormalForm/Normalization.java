import java.util.*;

class Normalization {
    private Set<Attribute> schema;
    private Set<FunctionalDependency> canonicalCover;
    private Set<FunctionalDependency> fds;
    private RelationsSet relations;

    Normalization(Set<FunctionalDependency> fds, Set<Attribute> schema) {
        this.fds = fds;
        this.schema = schema;
        canonicalCover = new LinkedHashSet<>();
        relations = new RelationsSet();
    }

    void computeCanonicalCover() {
        System.out.println("\n<----------------Decomposing Right Hand Side----------------------->\n");
        System.out.println(decomposeRhs());
        System.out.println("\n<----------------Removing Extraneous Left Attribute---------------->\n");
        System.out.println(removeExtraneousLeftAttribute());
        System.out.println("\n<----------Eliminating Redundant Functional Dependencies----------->\n");
        System.out.println(eliminateRedundantFds());
        System.out.println("\n<----------------------------Apply Union--------------------------->\n");
        System.out.println(applyUnion());
    }

    private Set<FunctionalDependency> decomposeRhs() {
        Set<FunctionalDependency> newFds = new LinkedHashSet<>();
        for (FunctionalDependency fd : fds) {
            Set<Attribute> rhs = fd.getRhs();
            if (rhs.size() > 1) {
                for (Attribute token : rhs) {
                    newFds.add(new FunctionalDependency(fd.getLhs(), Collections.singleton(token)));
                }
            } else newFds.add(fd);
        }
        return canonicalCover = newFds;
    }

    private Set<FunctionalDependency> removeExtraneousLeftAttribute() {
        Set<FunctionalDependency> newFds = new LinkedHashSet<>();
        for (FunctionalDependency fd : canonicalCover) {
            Set<Set<Attribute>> subSets = powerSet(fd.getLhs());
            int previousAttributesSize = fd.getLhs().size();
            Set<Attribute> newLhsAttributes = fd.getLhs();
            Set<Attribute> rhs = fd.getRhs();
            for (Set<Attribute> attributeSet : subSets) {
                Set<Attribute> closure = findClosure(attributeSet, canonicalCover);
                if (closure.containsAll(rhs) && attributeSet.size() <= previousAttributesSize) {
                    previousAttributesSize = attributeSet.size();
                    newLhsAttributes = attributeSet;
                }
            }
            newFds.add(new FunctionalDependency(newLhsAttributes, rhs));
        }
        return canonicalCover = newFds;
    }


    private Set<FunctionalDependency> eliminateRedundantFds() {
        Set<FunctionalDependency> fdsCopy = new LinkedHashSet<>(canonicalCover);
        for (FunctionalDependency fd : fdsCopy) {
            canonicalCover.remove(fd);
            Set<Attribute> closure = findClosure(fd.getLhs(), canonicalCover);
            if (!closure.containsAll(fd.getRhs())) {
                canonicalCover.add(fd);
            }
        }
        return canonicalCover;
    }

    private Set<FunctionalDependency> applyUnion() {
        Set<FunctionalDependency> mySet = new LinkedHashSet<>();
        for (FunctionalDependency fd : canonicalCover) {
            for (FunctionalDependency fd1 : canonicalCover) {
                if (fd.getLhs().equals(fd1.getLhs())) {
                    Set<Attribute> temp = new HashSet<>();
                    temp.addAll(fd.getRhs());
                    temp.addAll(fd1.getRhs());
                    fd.setRhs(temp);
                }
            }
            mySet.add(fd);
        }
        return mySet;
    }

    public static Set<Attribute> findClosure(Set<Attribute> attributes, Set<FunctionalDependency> fds, Set<Set<Attribute>> oldPowerSet) {
        Set<Attribute> closure;
        Set<Set<Attribute>> powerSet;
        closure = new HashSet<>(attributes);
        powerSet = powerSet(attributes);
        while (!powerSet.equals(oldPowerSet)) {
            for (Set<Attribute> subset : powerSet) {
                for (FunctionalDependency fd : fds) {
                    if (subset.equals(fd.getLhs())) {
                        closure.addAll(fd.getRhs());
                    }
                }
            }
            oldPowerSet = powerSet;
            Set<Attribute> closure1 = findClosure(closure, fds, oldPowerSet);
            closure.addAll(closure1);
        }
        return closure;
    }

    public static Set<Attribute> findClosure(Set<Attribute> attributes, Set<FunctionalDependency> fds) {
        return findClosure(attributes, fds, null);
    }


    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    public RelationsSet convertToRelations(Set<Set<Attribute>> candidateKeys) {
        for (FunctionalDependency fd : canonicalCover) {
            Relation relation = new Relation();
            relation.addAll(fd.getLhs());
            relation.addAll(fd.getRhs());
            relations.addRelation(relation);
        }
        for (Relation relation : new HashSet<>(relations.getRelations())) {
            if (CandidateKeyEvaluator.isCandidateKey(fds,relation.getAttributes(),schema)){
                break;
            }
            for (Set<Attribute> candidateKey : candidateKeys) {
                Relation ck = new Relation(candidateKey);
                if (relation.equals(ck)) {
                    continue;
                }
                relations.addRelation(ck);
            }
        }
        return relations;
    }

}

