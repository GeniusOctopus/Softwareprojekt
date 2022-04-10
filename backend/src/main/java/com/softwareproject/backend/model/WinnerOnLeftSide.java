package com.softwareproject.backend.model;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "WinnerOnLeftSide", description = "Repräsentiert die Anzal der Gewinne, welche Bilder auf der linken und rechten Seite gemacht haben")
public class WinnerOnLeftSide {

    @ApiObjectField(name = "winnerOnLeftSideCount", description = "Anzahl der Gewinne auf der linken Seite")
    private int winnerOnLeftSideCount;

    @ApiObjectField(name = "winnerOnRightSideCount", description = "Anzahl der Gewinne auf der rechten Seite")
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
