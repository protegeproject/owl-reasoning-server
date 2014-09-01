package edu.stanford.protege.reasoning.protocol;

import com.google.protobuf.ByteString;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClassExpression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
public class ClassExpressionTranslator_TestCase {

    private OWLClassExpression classExpression;

    private BinaryOWLHelper helper;

    @Before
    public void setUp() {
        classExpression = mock(OWLClassExpression.class);
        helper = mock(BinaryOWLHelper.class);
        ByteString bytes = mock(ByteString.class);
        when(helper.encode(classExpression)).thenReturn(bytes);
        when(helper.decode(bytes)).thenReturn(classExpression);
    }

    @Test
    public void shouldRoundTrip() {
        ClassExpressionTranslator translator = new ClassExpressionTranslator(helper);
        OWLClassExpression decoded = translator.decode(translator.encode(classExpression));
        assertThat(decoded, is(equalTo(classExpression)));
    }
}
