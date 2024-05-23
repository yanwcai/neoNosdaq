package org.genesys.clients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.genesys.language.NosdaqGrammar;
import org.genesys.models.Example;
import org.genesys.models.Problem;
import org.genesys.utils.ProblemDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by "" on 22/3/24.
 */
public class NosdaqMainNeo {

    public static void main(String[] args) throws FileNotFoundException {
        String specLoc = "./specs/Nosdaq";
        String json = "./problem/Nosdaq/bop.json";
        if (args.length != 0) json = args[0];

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Problem.class, new ProblemDeserializer());
        Gson gson = gsonBuilder.create();
        Problem problem = gson.fromJson(new FileReader(json), Problem.class);
        System.out.println("Run Nosdaq Neo main..." + problem + "\n");
        System.out.println("Printing examples: " + problem.getExamples() + "\n"); // ok

    /*
        Problem mongoProblem = new Problem();
        List<Example> examples = new ArrayList<>();
        for(Example org : problem.getExamples()) {
            Example tgt = new Example();
            List inputTgt = new ArrayList();
            for(Object o : org.getInput()) {
                //Get the input
                LinkedTreeMap out = (LinkedTreeMap)o;
                List<String> inHeader = (List) out.get("header");
                List<String> inContent = (List) out.get("content");
                String[] arrIn = inHeader.toArray(new String[inHeader.size()]);
                DataFrame inDf = SimpleDataFrameKt.dataFrameOf(arrIn).invoke(inContent.toArray());
                System.out.println("INPUT===================");
                Extensions.print(inDf);
                inputTgt.add(inDf);
            }
            tgt.setInput(inputTgt);
            //Get the output
            LinkedTreeMap out = (LinkedTreeMap)org.getOutput();
            List<String> outHeader = (List) out.get("header");
            System.out.println("header:" + outHeader);
            List<String> outContent = (List) out.get("content");
            System.out.println("content:" + outContent);
            String[] arr = outHeader.toArray(new String[outHeader.size()]);
            DataFrame outDf = SimpleDataFrameKt.dataFrameOf(arr).invoke(outContent.toArray());
            tgt.setOutput(outDf);
            tgtExamples.add(tgt);
            System.out.println("OUTPUT=================");
            Extensions.print(outDf);
        }
*/


        // Fill in the input/output types based on the problem
        // get production rules for AbstractType symbol
//        NosdaqGrammar grammar = new NosdaqGrammar(nosdaqProblem);
//        System.out.println("Get Nosdaq input productions size : " + grammar.getInputProductions().size());

        /* Load component specs. */
//        NosdaqChecker checker = new NosdaqChecker(specLoc);
//        NosdaqInterpreter interpreter = new NosdaqInterpreter();
//        interpreter.initNosdaqConstants(grammar.getInitProductions());
//        Decider decider = new FirstDecider(); // return the first candidate (candidates.get(0)), can use this for nosdaq for now

        // Using NeoSynthesizer
//        NeoSynthesizer synth;
//        synth = new NeoSynthesizer(grammar, nosdaqProblem, checker, interpreter, 3, specLoc, false, decider);
//        synth.synthesize();
    }
}

/**
 * Command:
 * ant neoNosdaq -Dapp=./problem/Nosdaq/bop.json -Ddepth=1 -Dlearn=false -Dstat=false -Dfile=""
 * ant neoMorpheus -Dapp=./problem/Morpheus/r4.json -Ddepth=3 -Dlearn=false -Dstat=false -Dfile="" -Dspec=specs/Morpheus/
 *
 * problem bop: return id == 2, or total == 20
 ***/
