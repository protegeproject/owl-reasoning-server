package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
import edu.stanford.protege.reasoning.protocol.HierachyQueryTypeTranslator;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLClassExpression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 29/08/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class GetSuperClassesAction_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private OWLClassExpression classExpression;

    private HierarchyQueryType queryType = HierarchyQueryType.DIRECT;

    private GetSuperClassesAction action;

    @Before
    public void setUp() {
        action = new GetSuperClassesAction(kbId, classExpression, queryType);
    }

    @Test
    public void shouldBeEqual() {
        GetSuperClassesAction actionB = new GetSuperClassesAction(kbId, classExpression, queryType);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        assertThat(action.equals(null), is(false));
    }

    @Test
    public void shouldHaveSameHashCode() {
        GetSuperClassesAction actionB = new GetSuperClassesAction(kbId, classExpression, queryType);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<GetSuperClassesActionHandler> typeA = action.getType();
        ActionType<GetSuperClassesActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<GetSuperClassesActionHandler> typeA = action.getType();
        GetSuperClassesAction actionB = new GetSuperClassesAction(kbId, classExpression, queryType);
        ActionType<GetSuperClassesActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        GetSuperClassesActionHandler handler = mock(GetSuperClassesActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new GetSuperClassesAction(null, classExpression, queryType);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfClassExpressionIsNull() {
        new GetSuperClassesAction(kbId, null, queryType);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfQueryTypeIsNull() {
        new GetSuperClassesAction(kbId, classExpression, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetSuperClassesAction actionB = new GetSuperClassesAction(mock(KbId.class), mock(OWLClassExpression.class), HierarchyQueryType.DIRECT);
        assertThat(action, is(not(equalTo(actionB))));
    }

    @Test
    public void shouldReturnProvidedId() {
        assertThat(action.getKbId(), is(equalTo(kbId)));
    }

    @Test
    public void shouldReturnProvidedClassExpression() {
        assertThat(action.getClassExpression(), is(equalTo(classExpression)));
    }

    public void shouldReturnProvidedQueryType() {
        assertThat(action.getHierarchyQueryType(), is(equalTo(queryType)));
    }

    @Test
    public void shouldNotThrowNullPointerInToString() {
        String s = action.toString();
        assertThat(s, is(IsNull.notNullValue()));
    }
    
}
