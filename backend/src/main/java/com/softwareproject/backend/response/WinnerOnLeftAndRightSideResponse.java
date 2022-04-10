package com.softwareproject.backend.response;

public class WinnerOnLeftAndRightSideResponse {

    private boolean isWinnerOnLeftSide;
    private long count;

    public WinnerOnLeftAndRightSideResponse() {

    }

    public WinnerOnLeftAndRightSideResponse(boolean isWinnerOnLeftSide, long count) {
        this.isWinnerOnLeftSide = isWinnerOnLeftSide;
        this.count = count;
    }

    public boolean isWinnerOnLeftSide() {
        return isWinnerOnLeftSide;
    }

    public void setWinnerOnLeftSide(boolean winnerOnLeftSide) {
        this.isWinnerOnLeftSide = winnerOnLeftSide;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
