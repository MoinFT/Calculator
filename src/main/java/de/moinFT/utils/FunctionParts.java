package de.moinFT.utils;

public class FunctionParts {
    private int ID;
    private boolean number;
    private boolean symbol;
    private boolean bracket;
    private String functionPart;

    private boolean free;
    private FunctionParts follower;
    public FunctionParts() {
        this.ID = 0;
        this.number = false;
        this.symbol = false;
        this.bracket = false;
        this.functionPart = "";

        this.free = true;
        this.follower = null;
    }

    public void set(boolean number, boolean symbol, boolean bracket, String functionPart) {
        if (this.free) {
            this.number = number;
            this.symbol = symbol;
            this.bracket = bracket;
            this.functionPart = functionPart;

            this.free = false;
        } else {
            if (this.follower == null) {
                this.follower = new FunctionParts();
                this.follower.ID = this.ID + 1;
            }

            this.follower.set(number, symbol, bracket, functionPart);
        }
    }

    public void setNew(boolean number, boolean symbol, boolean bracket, String functionPart) {
        this.number = number;
        this.symbol = symbol;
        this.bracket = bracket;
        this.functionPart = functionPart;

        this.free = false;
    }

    public FunctionParts get(int ID) {
        if (!this.free) {
            if (this.ID == ID) {
                return this;
            }

            if (this.follower != null) {
                return this.follower.get(ID);
            }
        }
        return null;
    }

    public void delete() {
        if (this.follower != null) {
            this.ID = this.follower.ID;
            this.number = this.follower.number;
            this.symbol = this.follower.symbol;
            this.bracket = this.follower.bracket;
            this.functionPart = this.follower.functionPart;

            this.free = this.follower.free;
            this.follower = this.follower.follower;

            this.newID();
        } else {
            this.number = false;
            this.symbol = false;
            this.bracket = false;
            this.functionPart = "";

            this.free = true;
        }
    }

    private void newID() {
        this.ID = this.ID - 1;
        if (this.follower != null) {
            this.follower.newID();
        }
    }

    public boolean isNumber() {
        return this.number;
    }

    public boolean isSymbol() {
        return this.symbol;
    }

    public boolean isBracket() {
        return this.bracket;
    }

    public String getFunctionPart() {
        return this.functionPart;
    }

    public int count() {
        if (!this.free) {
            if (this.follower != null) {
                return 1 + this.follower.count();
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public int countOpenBrackets() {
        if (!this.free) {
            if (this.follower != null) {
                if (this.functionPart.equals("(")) {
                    return 1 + this.follower.countOpenBrackets();
                } else {
                    return this.follower.countOpenBrackets();
                }
            } else {
                if (this.functionPart.equals("(")) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }
}