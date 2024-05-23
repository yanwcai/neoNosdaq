package org.genesys.type;

public class DocumentType implements AbstractType{

    public DocumentType() {

    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DocumentType);
    }

    @Override
    public int hashCode() {
        return 13 * this.hashCode() + 1;
    }

    @Override
    public String toString() {
        return "Document{}";
    }
}
