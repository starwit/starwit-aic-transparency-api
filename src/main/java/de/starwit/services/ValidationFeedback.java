package de.starwit.services;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ValidationFeedback {
    private boolean isValid = false;
    private List<String> invalidUris = new LinkedList<>();
    private List<URL> unreachableUris = new LinkedList<>();
    private boolean hasIncompleteModelData = true;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public List<String> getInvalidUris() {
        return invalidUris;
    }

    public void setInvalidUris(List<String> invalidUris) {
        this.invalidUris = invalidUris;
    }

    public List<URL> getUnreachableUris() {
        return unreachableUris;
    }

    public void setUnreachableUris(List<URL> unreachableUris) {
        this.unreachableUris = unreachableUris;
    }

    public boolean isHasIncompleteModelData() {
        return hasIncompleteModelData;
    }

    public void setHasIncompleteModelData(boolean hasIncompleteModelData) {
        this.hasIncompleteModelData = hasIncompleteModelData;
    }

    @Override
    public String toString() {
        StringBuffer feedback = new StringBuffer();
        if(invalidUris.size() > 0) {
            feedback.append("\"invalidUris\": {\n");
            invalidUris.forEach((s) -> feedback.append("\"" + s + "\",\n"));
            feedback.append("}\n");
        }
        if(unreachableUris.size() > 0) {
            feedback.append("\"unreachableUris\": {\n");
            unreachableUris.forEach((s) -> feedback.append("\"" + s.toString() + "\",\n"));
            feedback.append("}\n");
        }
        return "{\n\"isValid\":" + isValid + ",\n\"hasIncompleteModelData\":" + hasIncompleteModelData + ",\n" + feedback.toString() + "}";
    }
}
