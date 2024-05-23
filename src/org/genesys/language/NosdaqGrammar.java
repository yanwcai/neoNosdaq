package org.genesys.language;

import org.genesys.models.Example;
import org.genesys.models.Problem;
import org.genesys.type.*;

import java.util.*;

/**
 * Grammar for Nosdaq
 */
public class NosdaqGrammar implements Grammar<AbstractType>{

    public AbstractType inputType;

    public AbstractType outputType;

    private int maxNumDocument = 3; // temp

    private int maxNumField = 5; // temp

    private List<Production<AbstractType>> initProductions = new ArrayList<>();

    private List<Production<AbstractType>> inputProductions = new ArrayList<>();

    /**
     * Run Morpheus main...Problem{name='r5',
     * examples=[Example{input=[{header=[ID, T, P.1, P.2, Q.1],
     *                          content=[1, 24.3, 10.2, 5.5, 4.5, 2, 23.4, 10.4, 5.7, 3.2]}],
     *                  output={header=[ID, Channel, T, P],
     *                          content=[1, 1, 24.3, 10.2, 2, 1, 23.4, 10.4, 1, 2, 24.3, 5.5, 2, 2, 23.4, 5.7, 1, 1, 24.3, 4.5, 2, 1, 23.4, 3.2]}}]}
     * examples=[Example{input=[{id=1.0, price=10.0}, {id=2.0, price=20.0}],
     *                  output=[{id=2.0, total=20.0}]}]}
     * @param p
     */

    public NosdaqGrammar(Problem p) {
        assert !p.getExamples().isEmpty();
        // Table domain: assuming only have one example
        // MongoDB domain: can have multiple examples ( Start with only one example? )
        // document -> row, schema -> column (corresponding to Table)
        Example example = p.getExamples().get(0);
        System.out.println("Example input: " + example.getInput());

        // Rules for inputs
        inputType = new MongoCollectionType();
        Set constSet = new HashSet();
        System.out.println("input collection size: " + example.getInput().size()); // size = 1
        for (int i = 0; i < example.getInput().size(); i++) {

            // deserialize '{collection1=[{id=1.0, price=10.0}, {id=2.0, price=20.0}]}' -> MongoCollectionType [Done]
            MongoCollectionType inputCollection = (MongoCollectionType) example.getInput().get(i);
            Object input = example.getInput().get(i);
            System.out.println("inputCollection " + inputCollection); //
            initProductions.add(new Production<>(new MongoCollectionType(), "collection" + i));

//            Object input = inputs.get(i);
//            System.out.println("In NosdaqGrammar the input is: " + input);
//            input = new InputType(i, new MongoCollectionType());
//            System.out.println("In NosdaqGrammar the input is: " + input);


//            for (DocumentType row : input.getDocuments()) {
////                constSet.addAll(row.values());
//                System.out.println(row);
//            }
        }

        // Rule for output.
        this.outputType = new MongoCollectionType();

    }

    @Override
    public AbstractType start() {
        return new InitType(this.outputType);
    }

    @Override
    public String getName() {
        return "NosdaqGrammar";
    }

    @Override
    public List<Production<AbstractType>> getProductions() {
        List<Production<AbstractType>> productions = new ArrayList<>();
        productions.addAll(initProductions);

        productions.add(new Production<>(new BinopIntType(), "l(a,b).(+ a b)"));

        productions.add(new Production<>(new ConstType(),"1"));
        productions.add(new Production<>(new ConstType(),"10"));
        productions.add(new Production<>(new ConstType(),"2"));
        productions.add(new Production<>(new ConstType(),"20"));

        productions.add(new Production<>(true, new MongoCollectionType(), "match", new MongoCollectionType(),new BinopBoolType())); // match takes in access path?

        return productions;
    }

    @Override
    public List<Production<AbstractType>> productionsFor(AbstractType symbol) {
        return null;
    }

    @Override
    public AbstractType getOutputType() {
        return this.outputType;
    }

    @Override
    public List<Production<AbstractType>> getInputProductions() {
        return inputProductions;
    }

    @Override
    public List<Production<AbstractType>> getLineProductions(int size) {
        List<Production<AbstractType>> productions = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            productions.add(new Production<>(new MongoCollectionType(), "line" + i + "MongoCollection"));
        }

        return productions;
    }

    public List<Production<AbstractType>> getInitProductions() {
        return initProductions;
    }
}


