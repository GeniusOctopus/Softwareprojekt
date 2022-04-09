package com.softwareproject.backend.api;

public class WinnerOnLeftSide {

    private int winnerOnLeftSideCount;
    private int winnerOnRightSideCount;

    public WinnerOnLeftSide() {

    }

    public WinnerOnLeftSide(int winnerOnLeftSideCount, int winnerOnRightSide) {
        this.winnerOnLeftSideCount = winnerOnLeftSideCount;
        this.winnerOnRightSideCount = winnerOnRightSide;
    }

    public int getWinnerOnLeftSideCount() {
        return winnerOnLeftSideCount;
    }

    public void setWinnerOnLeftSideCount(int winnerOnLeftSideCount) {
        this.winnerOnLeftSideCount = winnerOnLeftSideCount;
    }

    public int getWinnerOnRightSideCount() {
        return winnerOnRightSideCount;
    }

    public void setWinnerOnRightSideCount(int winnerOnRightSideCount) {
        this.winnerOnRightSideCount = winnerOnRightSideCount;
    }
}
