package org.genesys.interpreter;

import org.genesys.interpreter.deepcode.NumUnop;
import org.genesys.interpreter.nosdaq.*;
import org.genesys.models.Node;
import org.genesys.models.Problem;
import org.genesys.language.Production;
import org.genesys.type.AbstractType;
import org.genesys.type.Maybe;

import java.util.ArrayList;
import java.util.List;

/**
 * Interpreter for Nosdaq.
 * Created in May 2024.
 * References: DeepCoderInterpreter.java, MorpheusInterpreter.java
 * */

public class NosdaqInterpreter extends BaseInterpreter {

    public NosdaqInterpreter () {
        executors.put("root", (objects, input) -> {
            Object obj = objects.get(0);
            if (obj instanceof Unop)
                return new Maybe<>(((Unop) objects.get(0)).apply(input));
            else
                return new Maybe<>(obj);
        });
        executors.put("input0", (objects, input) -> new Maybe<>(((List) input).get(0)));
//        executors.put("input1", (objects, input) -> new Maybe<>(((List) input).get(1)));

        executors.put("1", (objects, input) -> new Maybe<>(1));
        executors.put("10", (objects, input) -> new Maybe<>(10)); // ?
        executors.put("20", (objects, input) -> new Maybe<>(20)); // ?

        executors.put("l(a,b).(+ a b)", (objects, input) -> new Maybe<>(new PrimitiveBinop("+")));

        executors.put("match", (objects, input) -> {
            System.out.println("Adding 'Match' to nosdaq interpreter.");
            List args = new ArrayList();
            args.add(objects.get(0));
            args.add(objects.get(1));
            return new Maybe<>(new Match().apply(args));
        });
    }

    public void initNosdaqConstants(List<Production<AbstractType>> inits) {
        for(Production<AbstractType> prod : inits) {
//            System.out.println(prod + ":" + prod.getValue());
            executors.put(prod.function, (objects, input) -> new Maybe<>(prod.getValue()));
        }
    }
}
