package org.genesys.interpreter.nosdaq;

import org.genesys.interpreter.Unop;
import org.genesys.type.MongoCollectionType;
import org.genesys.utils.MorpheusUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter in nosdaq: aggregate expression
 * https://github.com/simpl-group/nosdaq/blob/master/src/main/java/nosdaq/ast/expr/Filter.java
 */
public class Match implements Unop {

    private MorpheusUtil util_ = MorpheusUtil.getInstance();

    @Override
    public Object apply(Object obj) {
        assert obj != null;

        List<List> pair = (List<List>) obj;
        assert pair.size() == 2 : pair;

        System.out.println("In Match interpreter, pair.get(0): " + pair.get(0));
        System.out.println("In Match interpreter, pair.get(1): " + pair.get(1));

//        List input1 = pair.get(0);
//        List input2 = pair.get(1);


        MongoCollectionType res = new MongoCollectionType();
        return res;
    }

    public String toString() {
        return ("MATCH");
    }

}
