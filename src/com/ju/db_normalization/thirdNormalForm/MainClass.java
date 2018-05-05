import java.util.*;


public class MainClass {

    public static void main(String... args) {
        //ABCDEFGH
        // CD->A, EC->H, GHB->AB, C->D, EG->A, H->B, BE->CD, EC->B
        Scanner sc = new Scanner(System.in);
        HashSet<Attribute> schema = getSchemaFromConsole(sc);
        Set<FunctionalDependency> fds = getFdSFromConsole(sc);
        System.out.println("\n-------------------The Functional Dependencies are------------------\n");
        System.out.println(fds);
        Normalization normalization = new Normalization(fds, schema);
        normalization.computeCanonicalCover();
        CandidateKeyEvaluator candidateKeyEvaluator = new CandidateKeyEvaluator();
        Set<Set<Attribute>> candidateKeys = candidateKeyEvaluator.findCandidateKeys(fds, schema);
        System.out.println("\n-------------------------Candidate Keys are-------------------------\n");
        System.out.println(candidateKeys);
        System.out.println("\n--------------------Converting into relations-----------------------\n");
        System.out.println(normalization.convertToRelations(candidateKeys));
    }

    private static HashSet<Attribute> getSchemaFromConsole(Scanner sc) {
        String input;
        System.out.println("Enter schema in the form ABCDE");
        input = sc.nextLine();

        char[] chars = input.toCharArray();
        HashSet<Attribute> schema = new HashSet<>();
        for (Character character : chars) {
            Attribute attribute = new Attribute(character);
            schema.add(attribute);
        }
        return schema;
    }

    private static Set<FunctionalDependency> getFdSFromConsole(Scanner sc) {
        String input;
        System.out.println("Enter fd's separated by comma example: CD->A, EC->H ");
        input = sc.nextLine();
        StringTokenizer st = new StringTokenizer(input, ","); // comma as delimiter
        Set<FunctionalDependency> fds = new LinkedHashSet<>();
        while (st.hasMoreTokens()) {
            String fd = st.nextToken().trim();
            FunctionalDependency functionalDependency = FunctionalDependency.convertStringToFd(fd);
            fds.add(functionalDependency);
        }
        return fds;
    }


}
