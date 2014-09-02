package edu.stanford.protege.reasoning.action;

import edu.stanford.protege.reasoning.KbId;
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
public class GetInstancesAction_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private OWLClassExpression classExpression;

    private HierarchyQueryType queryType = HierarchyQueryType.DIRECT;

    private GetInstancesAction action;

    @Before
    public void setUp() {
        action = new GetInstancesAction(kbId, classExpression, queryType);
    }

    @Test
    public void shouldBeEqual() {
        GetInstancesAction actionB = new GetInstancesAction(kbId, classExpression, queryType);
        assertThat(action, is(equalTo(actionB)));
    }

    @Test
    public void shouldReturnFalseForEqualsNull() {
        assertThat(action.equals(null), is(false));
    }

    @Test
    public void shouldReturnSameActionType() {
        ActionType<GetInstancesActionHandler> typeA = action.getType();
        ActionType<GetInstancesActionHandler> typeB = action.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldReturnSameActionTypeForDifferentActions() {
        ActionType<GetInstancesActionHandler> typeA = action.getType();
        GetInstancesAction actionB = new GetInstancesAction(kbId, classExpression, queryType);
        ActionType<GetInstancesActionHandler> typeB = actionB.getType();
        assertThat(typeA, is(typeB));
    }

    @Test
    public void shouldDispatchToHandler() {
        GetInstancesActionHandler handler = mock(GetInstancesActionHandler.class);
        action.dispatch(handler);
        verify(handler, times(1)).handleAction(action);
    }
    @Test
    public void shouldHaveSameHashCode() {
        GetInstancesAction actionB = new GetInstancesAction(kbId, classExpression, queryType);
        assertThat(action.hashCode(), is(equalTo(actionB.hashCode())));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfIdIsNull() {
        new GetInstancesAction(null, classExpression, queryType);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfClassExpressionIsNull() {
        new GetInstancesAction(kbId, null, queryType);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionIfQueryTypeIsNull() {
        new GetInstancesAction(kbId, classExpression, null);
    }

    @Test
    public void shouldNotBeEqual() {
        GetInstancesAction actionB = new GetInstancesAction(mock(KbId.class), mock(OWLClassExpression.class), HierarchyQueryType.DIRECT);
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

    @Test
    public void shouldReturnProvidedQueryType() {
        assertThat(action.getHierarchyQueryType(), is(equalTo(queryType)));
    }
    
}
