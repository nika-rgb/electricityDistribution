package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums;

public enum GraphVizStyleOptions {
    FILLED {
        @Override
        public String getStyleName() {
            return "filled";
        }
    },
    NOT_FILLED {
        @Override
        public String getStyleName() {
            return "not filled";
        }
    };

    public abstract String getStyleName();
}
