package de.moinFT.utils;

public class FunctionSymbols {
    private int ID;
    private int functionID;
    private char symbol;

    private boolean free;
    private FunctionSymbols follower;

    public FunctionSymbols() {
        this.ID = 0;
        this.functionID = 0;
        this.symbol = ' ';

        this.free = true;
        this.follower = null;
    }

    public void set(int functionID, char symbol) {
        if (this.free) {
            this.functionID = functionID;
            this.symbol = symbol;

            this.free = false;
        } else {
            if (this.follower == null) {
                this.follower = new FunctionSymbols();
                this.follower.ID = this.ID + 1;
            }

            this.follower.set(functionID, symbol);
        }
    }
}