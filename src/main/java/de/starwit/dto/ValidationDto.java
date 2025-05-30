package de.starwit.dto;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ValidationDto {
    private boolean isValid = false;
    private boolean nameTaken = false;
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

    public boolean isNameTaken() {
        return nameTaken;
    }

    public void setNameTaken(boolean nameTaken) {
        this.nameTaken = nameTaken;
    }

    @Override
    public String toString() {
        return "ValidationFeedback [isValid=" + isValid + ", nameTaken=" + nameTaken + ", invalidUris=" + invalidUris
                + ", unreachableUris=" + unreachableUris + ", hasIncompleteModelData=" + hasIncompleteModelData + "]";
    }
}
