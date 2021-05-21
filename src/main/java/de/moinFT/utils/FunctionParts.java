package de.moinFT.utils;

public class FunctionParts {
    private int ID;
    private boolean number;
    private boolean symbol;
    private char functionPart;

    private boolean free;
    private FunctionParts follower;

    public FunctionParts() {
        this.ID = 0;
        this.number = false;
        this.symbol = false;
        this.functionPart = ' ';

        this.free = true;
        this.follower = null;
    }

    public int set(boolean number, boolean symbol, char functionPart) {
        if (this.free) {
            this.number = number;
            this.symbol = symbol;
            this.functionPart = functionPart;

            this.free = false;
            return this.ID;
        } else {
            if (this.follower == null) {
                this.follower = new FunctionParts();
                this.follower.ID = this.ID + 1;
            }

            this.follower.set(number, symbol, functionPart);
        }
        return 0;
    }
}