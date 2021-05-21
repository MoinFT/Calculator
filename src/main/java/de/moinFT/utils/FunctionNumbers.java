package de.moinFT.utils;

public class FunctionNumbers {
    private int ID;
    private int functionID;
    private int number;

    private boolean free;
    private FunctionNumbers follower;

    public FunctionNumbers() {
        this.ID = 0;
        this.functionID = 0;
        this.number = ' ';

        this.free = true;
        this.follower = null;
    }

    public void set(int functionID, int number) {
        if (this.free) {
            this.functionID = functionID;
            this.number = number;

            this.free = false;
        } else {
            if (this.follower == null) {
                this.follower = new FunctionNumbers();
                this.follower.ID = this.ID + 1;
            }

            this.follower.set(functionID, number);
        }
    }
}