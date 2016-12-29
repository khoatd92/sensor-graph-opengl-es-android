package com.example.tdkhoa.sensorgraph.model;

import android.graphics.Color;

public class GraphInfo {

    private int maxWidth;
    private int maxHeight;
    private float maxData;
    private float minData;
    private float xStartPosition;
    private float xEndPosition;
    private float yPositionToDraw;

    //color - default is yellow
    private float red = 1;
    private float green = 1;
    private float blue = 0;

    private GraphInfo(Builder builder) {
        maxWidth = builder.maxWidth;
        maxHeight = builder.maxHeight;
        maxData = builder.maxData;
        minData = builder.minData;
        xStartPosition = builder.xStartPosition;
        xEndPosition = builder.xEndPosition;
        yPositionToDraw = builder.yPositionToDraw;
        if (builder.color != 0) {
            red = Color.red(builder.color) / 255f;
            green = Color.green(builder.color) / 255f;
            blue = Color.blue(builder.color) / 255f;
        }
    }

    public void setMaxData(float maxData) {
        this.maxData = maxData;
    }

    public float getMaxData() {
        return maxData;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public float getMinData() {
        return minData;
    }

    public float getxEndPosition() {
        return xEndPosition;
    }

    public float getxStartPosition() {
        return xStartPosition;
    }

    public float getyPositionToDraw() {
        return yPositionToDraw;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public static final class Builder {
        private int maxWidth;
        private int maxHeight;
        private float maxData = 100;
        private float minData = -100;
        private float xStartPosition = -1.f;
        private float xEndPosition = 1;
        private float yPositionToDraw = -0.5f;
        private int color;

        public Builder maxWidth(int val) {
            maxWidth = val;
            return this;
        }

        public Builder maxHeight(int val) {
            maxHeight = val;
            return this;
        }

        public Builder maxData(float val) {
            maxData = val;
            return this;
        }

        public Builder minData(float val) {
            minData = val;
            return this;
        }

        public Builder xStartPosition(float val) {
            xStartPosition = val;
            return this;
        }

        public Builder xEndPosition(float val) {
            xEndPosition = val;
            return this;
        }

        public Builder yPositionToDraw(float val) {
            yPositionToDraw = val;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public GraphInfo build() {
            return new GraphInfo(this);
        }
    }
}
