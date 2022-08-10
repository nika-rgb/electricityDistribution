package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums;

public enum GraphVizColor {
    RED {
        @Override
        public String getName() {
            return "red";
        }
    }, BLUE {
        @Override
        public String getName() {
            return "blue";
        }
    }, GREEN {
        @Override
        public String getName() {
            return "green";
        }
    };

    public abstract String getName();
}
