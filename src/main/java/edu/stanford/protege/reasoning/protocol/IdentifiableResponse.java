package edu.stanford.protege.reasoning.protocol;

import edu.stanford.protege.reasoning.Response;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class IdentifiableResponse {

    private final Integer id;

    private final Response response;

    public IdentifiableResponse(Integer id, Response response) {
        this.id = id;
        this.response = response;
    }

    public Integer getId() {
        return id;
    }

    public Response getResponse() {
        return response;
    }
}
