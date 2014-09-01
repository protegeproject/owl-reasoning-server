package edu.stanford.protege.reasoning.protocol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ClassTranslator_TestCase {

    @Mock
    private OWLClassProvider classProvider;

    @Mock
    private OWLClass cls;

    @Mock
    private IRI iri;

    @Before
    public void setUp() {
        when(cls.getIRI()).thenReturn(iri);
        when(classProvider.getOWLClass(any(IRI.class))).thenReturn(cls);
    }

    @Test
    public void shouldRoundTrip() {
        ClassTranslator translator = new ClassTranslator(classProvider);
        OWLClass decoded = translator.decode(translator.encode(cls));
        assertThat(decoded, is(equalTo(cls)));
    }
}
