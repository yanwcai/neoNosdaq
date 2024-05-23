package org.genesys.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoCollectionType implements AbstractType{

    private List<String> attribute;

    private List<Integer> value;

    public MongoCollectionType() {
        this.attribute = new ArrayList<>();
        this.value = new ArrayList<>();
    }

    public MongoCollectionType(List<String> attribute, List<Integer> value) {
        this.attribute = attribute;
        this.value = value;
    }


    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MongoCollectionType);
    }

    @Override
    public int hashCode() {
        return 17 * this.hashCode() + 1;
    }

    @Override
    public String toString() {
        return "MongoCollection{}";
    }

    public List<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<String> attribute) {
        this.attribute = attribute;
    }

    public List<Integer> getValue() {
        return value;
    }

    public void setValue(List<Integer> value) {
        this.value = value;
    }
}
