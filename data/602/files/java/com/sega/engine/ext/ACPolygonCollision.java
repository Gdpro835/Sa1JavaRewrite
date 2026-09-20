package com.sega.engine.ext;

import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.engine.lib.Line;

public class ACPolygonCollision extends ACCollision {
    protected int VERTEX_NUM;
    protected int height;
    protected int posX;
    protected int posY;
    protected Line.CrossPoint rePoint = new Line.CrossPoint();
    protected int[] useId;
    protected int[][] vertex;
    protected int width;

    public ACPolygonCollision(ACObject acObj, int[][] vertex2, int[] useId2, int x, int y) {
        super(acObj, acObj.getWorld());
        setProperty(vertex2, useId2, x, y);
    }

    public void setProperty(int[][] vertex2, int[] useId2, int x, int y) {
        this.vertex = vertex2;
        this.useId = useId2;
        this.VERTEX_NUM = this.useId.length;
        this.posX = x;
        this.posY = y;
    }

    public void update() {
        int leftX = getLeftX() - this.posX;
        int topY = getTopY() - this.posY;
        int width2 = 0;
        int height2 = 0;
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            if (this.vertex[this.useId[i]][0] - leftX > width2) {
                width2 = this.vertex[this.useId[i]][0] - leftX;
            }
        }
        for (int i2 = 0; i2 < this.VERTEX_NUM; i2++) {
            if (this.vertex[this.useId[i2]][1] - topY > height2) {
                height2 = this.vertex[this.useId[i2]][1] - topY;
            }
        }
        this.width = width2;
        this.height = height2;
    }

    public int getLeftX() {
        int re = this.vertex[this.useId[0]][0];
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            if (this.vertex[this.useId[i]][0] < re) {
                re = this.vertex[this.useId[i]][0];
            }
        }
        return this.posX + re;
    }

    public int getTopY() {
        int re = this.vertex[this.useId[0]][1];
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            if (this.vertex[this.useId[i]][1] < re) {
                re = this.vertex[this.useId[i]][1];
            }
        }
        return this.posY + re;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getCollisionXFromLeft(int y) {
        int reX = -1000;
        int leftX = getLeftX();
        int topY = getTopY();
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            Line.getCrossPoint(this.rePoint, (this.vertex[this.useId[i]][0] + this.posX) - leftX, (this.vertex[this.useId[i]][1] + this.posY) - topY, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][0] + this.posX) - leftX, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][1] + this.posY) - topY, 0, y, getWidth(), y);
            if (this.rePoint.hasPoint && (reX == -1000 || this.rePoint.x < reX)) {
                reX = this.rePoint.x;
            }
        }
        return reX;
    }

    public int getCollisionXFromRight(int y) {
        int reX = -1000;
        int leftX = getLeftX();
        int topY = getTopY();
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            Line.getCrossPoint(this.rePoint, (this.vertex[this.useId[i]][0] + this.posX) - leftX, (this.vertex[this.useId[i]][1] + this.posY) - topY, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][0] + this.posX) - leftX, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][1] + this.posY) - topY, 0, y, getWidth(), y);
            if (this.rePoint.hasPoint && (reX == -1000 || this.rePoint.x > reX)) {
                reX = this.rePoint.x;
            }
        }
        return reX;
    }

    public int getCollisionYFromDown(int x) {
        int reY = -1000;
        int leftX = getLeftX();
        int topY = getTopY();
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            Line.getCrossPoint(this.rePoint, (this.vertex[this.useId[i]][0] + this.posX) - leftX, (this.vertex[this.useId[i]][1] + this.posY) - topY, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][0] + this.posX) - leftX, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][1] + this.posY) - topY, x, 0, x, getHeight());
            if (this.rePoint.hasPoint && (reY == -1000 || this.rePoint.y > reY)) {
                reY = this.rePoint.y;
            }
        }
        return reY;
    }

    public int getCollisionYFromUp(int x) {
        int reY = -1000;
        int leftX = getLeftX();
        int topY = getTopY();
        for (int i = 0; i < this.VERTEX_NUM; i++) {
            Line.getCrossPoint(this.rePoint, (this.vertex[this.useId[i]][0] + this.posX) - leftX, (this.vertex[this.useId[i]][1] + this.posY) - topY, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][0] + this.posX) - leftX, (this.vertex[this.useId[(i + 1) % this.VERTEX_NUM]][1] + this.posY) - topY, x, 0, x, getHeight());
            if (this.rePoint.hasPoint && (reY == -1000 || this.rePoint.y < reY)) {
                reY = this.rePoint.y;
            }
        }
        return reY;
    }
}
