package com.tencent.goff.pojo;

public class Box {

    private Double left;
    private Double bottom;
    private Double top;
    private Double right;

    public Box(Double left, Double bottom, Double top, Double right) {
        this.left = left;
        this.bottom = bottom;
        this.top = top;
        this.right = right;
    }

    public Box() {
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getBottom() {
        return bottom;
    }

    public void setBottom(Double bottom) {
        this.bottom = bottom;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Double getRight() {
        return right;
    }

    public void setRight(Double right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Box{" +
                "left=" + left +
                ", bottom=" + bottom +
                ", top=" + top +
                ", right=" + right +
                '}';
    }
}
