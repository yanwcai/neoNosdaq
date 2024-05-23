package org.genesys.synthesis;

import com.google.gson.Gson;
import com.microsoft.z3.BoolExpr;
import org.genesys.models.*;
import org.genesys.type.Maybe;
import org.genesys.type.MongoCollectionType;
import org.genesys.utils.LibUtils;
import org.genesys.utils.MorpheusUtil;
import org.genesys.utils.Z3Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Deduction for Nosdaq?
 * Reference: DeepCoderChecker, MorpheusChecker
 */

public class NosdaqChecker implements Checker<Problem, List<List<Pair<Integer, List<String>>>>>{

    private HashMap<String, Component> components_ = new HashMap<>();

    private Gson gson = new Gson();

    // TODO: do we need a validator?

    private List<List<Pair<Integer, List<String>>>> core_ = new ArrayList<>();

    private Z3Utils z3_ = Z3Utils.getInstance();
    private MorpheusUtil util_ = MorpheusUtil.getInstance();

    private Map<Pair<Integer, String>, List<BoolExpr>> cstCache_ = new HashMap<>();

    private final String alignId_ = "alignOutput";

    // Abstractions: # document, degree of root, # nodes in the tree
    // Properties: SIZE, ROOT_DEGREE,
    private String[] spec = {"IN_SIZE_SPEC", "OUT_SIZE_SPEC",
                             "IN_RD_SPEC", "OUT_RD_SPEC"};

    private Map<String, Object> clauseToNodeMap_ = new HashMap<>();
    //Map a clause to its original spec
    private Map<String, String> clauseToSpecMap_ = new HashMap<>();


    public NosdaqChecker(String specLoc) throws FileNotFoundException {
        System.out.println("Calling nosdaq checker..\n"); // Called ..
        File[] files = new File(specLoc).listFiles();
        for (File file : files) {
            assert file.isFile() : file;
            String json = file.getAbsolutePath();
            Component comp = gson.fromJson(new FileReader(json), Component.class);
            System.out.println("In checker, comp is " + comp);
            components_.put(comp.getName(), comp);
        }
    }

    @Override
    public boolean check(Problem specification, Node node) {
        return false;
    }

    @Override
    public boolean check(Problem specification, Node node, Node curr) {
        System.out.println("Checker.check is called..");
//        core_.clear();
        z3_.clearEqClassesInPE();

        Example example = specification.getExamples().get(0);
        Object output = example.getOutput();
        List inputs = example.getInput(); // ?

        System.out.println("In Nosdaq checker, inputs: " + inputs);

        /* Generate SMT formula for current AST node. */
        Queue<Node> queue = new LinkedList<>();
        List<BoolExpr> cstList = new ArrayList<>(); // TODO: what to add to the cstList?

        queue.add(node);
        while (!queue.isEmpty()) {
            Node worker = queue.remove();
            //Generate constraint between worker and its children.
            String func = worker.function;
            //Get component spec.
            Component comp = components_.get(func);
            System.out.println("working on : " + func + " id:" + worker.id + " isconcrete:" + worker.isConcrete());
            if ("root".equals(func)) {
                System.out.println("if root == func: \n");
//                List<BoolExpr> abs = abstractNosdaq(worker, output);
//                List<BoolExpr> align = alignOutput(worker); // TODO: implement alignOutput function
//                cstList.addAll(abs);
//                cstList.addAll(align);
            } else if (func.contains("input")) {
                System.out.println("else if (func.contains('input'))\n");
                //attach inputs
//                List<String> nums = LibUtils.extractNums(func);
//                assert !nums.isEmpty();
//                int index = Integer.valueOf(nums.get(0));
//                Object inDf = inputs.get(index);
//                z3_.updateTypeMap(worker.id, worker.function);
//
//                List<BoolExpr> abs = abstractNosdaq(worker, inDf);
//
//                cstList.addAll(abs);
            } else {
                System.out.println("else.. ");
            }

            for (int i = 0; i < worker.children.size(); i++) {
                Node child = worker.children.get(i);
                queue.add(child);
            }
        }

        return false;
    }

    @Override
    public List<List<Pair<Integer, List<String>>>> learnCore() {
        return null;
    }

    private List<BoolExpr> abstractNosdaq(Node worker, MongoCollectionType collection, List<MongoCollectionType> inputs) {
        List<BoolExpr> cstList = new ArrayList<>();
        String strVal = worker.function;


        return cstList;
    }

    private List<BoolExpr> alignOutput(Node worker) {
        List<BoolExpr> cstList = new ArrayList<>();
        return cstList;
    }
}
