import java.util.*;

public class CandidateKeyEvaluator {
    private HashSet<Attribute> obligatoryCandidateKey;
    private HashSet<Attribute> possibleCandidateKey;
    private HashSet<Attribute> notCandidateKey;

    CandidateKeyEvaluator() {
        obligatoryCandidateKey = new HashSet<>();
        possibleCandidateKey = new HashSet<>();
        notCandidateKey = new HashSet<>();
    }

    Set<Set<Attribute>> findCandidateKeys(Set<FunctionalDependency> fds, Set<Attribute> schema) {
        Set<Set<Attribute>> candidateKeys = new HashSet<>();
        Set<Set<Attribute>> newCandidateKeys;
        testPossibility(fds, schema);
        Set<Attribute> key = new HashSet<>(obligatoryCandidateKey);
        if (isCandidateKey(fds, key, schema)) {
            candidateKeys.add(key);
            return candidateKeys;
        } else {
            Set<Set<Attribute>> powerSet = Normalization.powerSet(possibleCandidateKey);
            for (Set<Attribute> subset : powerSet) {
                key = new HashSet<>();
                key.addAll(obligatoryCandidateKey);
                key.addAll(subset);
                if (isCandidateKey(fds, key, schema)) {
                    candidateKeys.add(key);
                }
            }
            newCandidateKeys = new HashSet<>(candidateKeys);
            for (Set<Attribute> candidateKey : candidateKeys) {
                for (Set<Attribute> candidateKey1 : candidateKeys) {
                    if (candidateKey.containsAll(candidateKey1) && (!candidateKey.equals(candidateKey1))) {
                        newCandidateKeys.remove(candidateKey);
                    }
                }
            }
            return newCandidateKeys;
        }
    }

    public static Boolean isCandidateKey(Set<FunctionalDependency> fds, Set<Attribute> key, Set<Attribute> schema) {
        Set<Attribute> result = Normalization.findClosure(key, fds);
        return result.equals(schema);
    }

    private void testPossibility(Set<FunctionalDependency> fds, Set<Attribute> attributes) {
        for (Attribute attribute : attributes) {
            testPossibility(fds, attribute);
        }
    }

    private void testPossibility(Set<FunctionalDependency> fds, Attribute attribute) {
        String location = computeAttributeLocation(fds, attribute);
        if (location != null) {
            switch (location) {
                case AttributeLocation.LHS:
                case AttributeLocation.NOT_FOUND:
                    obligatoryCandidateKey.add(attribute);
                    System.out.println(attribute + " must be part of the key");
                    break;
                case AttributeLocation.BOTH:
                    possibleCandidateKey.add(attribute);
                    System.out.println(attribute + " can be part of the key");
                    break;
                case AttributeLocation.RHS:
                    notCandidateKey.add(attribute);
                    System.out.println(attribute + " not part of the key");
                    break;
            }
        }
    }

    private static String computeAttributeLocation(Set<FunctionalDependency> fds, Attribute attribute) {
        HashSet<Attribute> lhs = new HashSet<>();
        HashSet<Attribute> rhs = new HashSet<>();
        for (FunctionalDependency fd : fds) {
            rhs.addAll(fd.getRhs());
            lhs.addAll(fd.getLhs());
        }
        if (!lhs.contains(attribute) && !rhs.contains(attribute)) {
            attribute.setLocation(AttributeLocation.NOT_FOUND);
        } else {
            if (lhs.contains(attribute)) {
                attribute.setLocation(AttributeLocation.LHS);
            }
            if (rhs.contains(attribute)) {
                if (attribute.getLocation() != null && attribute.getLocation().equals(AttributeLocation.LHS)) {
                    attribute.setLocation(AttributeLocation.BOTH);
                } else {
                    attribute.setLocation(AttributeLocation.RHS);
                }
            }
        }
        return attribute.getLocation();
    }
}
