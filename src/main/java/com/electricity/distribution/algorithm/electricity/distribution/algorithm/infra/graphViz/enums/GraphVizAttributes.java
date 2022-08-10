package com.electricity.distribution.algorithm.electricity.distribution.algorithm.infra.graphViz.enums;

public enum GraphVizAttributes {
    LABEL {
        @Override
        public String getAttributeName() {
            return "label";
        }
    },
    STYLE {
        @Override
        public String getAttributeName() {
            return "style";
        }
    },
    FILLCOLOR {
        @Override
        public String getAttributeName() {
            return "fillcolor";
        }
    },
    COLOR {
        @Override
        public String getAttributeName() {
            return "color";
        }
    };

    public abstract String getAttributeName();
}
