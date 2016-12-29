package com.example.tdkhoa.sensorgraph;

public class GraphInfo {

    private int maxWidth;
    private int maxHeight;
    private float maxData;
    private float minData;
    private float xStartPosition;
    private float xEndPosition;
    private float yPositionToDraw;

    private GraphInfo(Builder builder) {
        maxWidth = builder.maxWidth;
        maxHeight = builder.maxHeight;
        maxData = builder.maxData;
        minData = builder.minData;
        xStartPosition = builder.xStartPosition;
        xEndPosition = builder.xEndPosition;
        yPositionToDraw = builder.yPositionToDraw;
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

    public static final class Builder {
        private int maxWidth;
        private int maxHeight;
        private float maxData = 100;
        private float minData = -100;
        private float xStartPosition = -1.f;
        private float xEndPosition = 1;
        private float yPositionToDraw = -0.5f;

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

        public GraphInfo build() {
            return new GraphInfo(this);
        }
    }
}
