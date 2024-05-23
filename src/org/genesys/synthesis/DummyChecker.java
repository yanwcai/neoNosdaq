package org.genesys.synthesis;

import com.microsoft.z3.BoolExpr;
import org.genesys.models.Node;
import org.genesys.models.Problem;

/**
 * Created by yufeng on 6/3/17.
 */
public class DummyChecker implements Checker<Problem, BoolExpr> {
    @Override
    public boolean check(Problem specification, Node node) {
        System.out.println("Dummy checker checking ... \n");
        return true;
    }

    @Override
    public boolean check(Problem specification, Node node, Node curr) {
        System.out.println("Dummy checker checking ... \n");
        return true;
    }

    @Override
    public BoolExpr learnCore() {
        return null;
    }
}
