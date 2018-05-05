import java.util.*;

public class MainDemo {
    public static void main(String... args) {
        //ABCDEFGH
        // CD->A, EC->H, GHB->AB, C->D, EG->A, H->B, BE->CD, EC->B
        String schemaString = "CSJDPQV";
        String fds = "C->CSJDPQV, SD->P, JP->C,J->S";

        Set<Attribute> schema = convertToSchema(schemaString);
        Set<FunctionalDependency> functionalDependencies = convertToFdS(fds);

        System.out.println("\n-------------------The Functional Dependencies are------------------\n");
        System.out.println(functionalDependencies);
        Normalization normalization = new Normalization(functionalDependencies, schema);
        normalization.computeCanonicalCover();
        System.out.println("\n--------------------------------------------------------------------\n");

        CandidateKeyEvaluator candidateKeyEvaluator = new CandidateKeyEvaluator();
        Set<Set<Attribute>> candidateKeys = candidateKeyEvaluator.findCandidateKeys(functionalDependencies, schema);
        System.out.println("\n-------------------------Candidate Keys are-------------------------\n");
        System.out.println(candidateKeys);
        System.out.println("\n--------------------Converting into relations-----------------------\n");
        System.out.println(normalization.convertToRelations(candidateKeys));
    }

    private static Set<FunctionalDependency> convertToFdS(String fds) {
        StringTokenizer st = new StringTokenizer(fds, ","); // comma as delimiter
        Set<FunctionalDependency> functionalDependencies = new LinkedHashSet<>();
        while (st.hasMoreTokens()) {
            String fd = st.nextToken().trim();
            FunctionalDependency functionalDependency = FunctionalDependency.convertStringToFd(fd);
            functionalDependencies.add(functionalDependency);
        }
        return functionalDependencies;
    }

    public static Set<Attribute> convertToSchema(String attributes) {
        Set<Attribute> schema = new HashSet<>();
        char[] chars = attributes.toCharArray();
        for (char attribute : chars) {
            schema.add(new Attribute(attribute));
        }
        return schema;
    }


}
