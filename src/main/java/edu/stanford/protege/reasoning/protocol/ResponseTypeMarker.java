package edu.stanford.protege.reasoning.protocol;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 26/09/2014
 */
public enum ResponseTypeMarker {

    EXPECTED_RESPONSE(0),

    REASONER_TIME_OUT_EXCEPTION(-1),

    REASONER_INTERNAL_ERROR_EXCEPTION(-2);

    private byte marker;

    ResponseTypeMarker(int marker) {
        if(marker < Byte.MIN_VALUE) {
            throw new IllegalArgumentException();
        }
        if(marker > Byte.MAX_VALUE) {
            throw new IllegalStateException();
        }
        this.marker = (byte) marker;
    }

    public byte getMarker() {
        return marker;
    }
}
