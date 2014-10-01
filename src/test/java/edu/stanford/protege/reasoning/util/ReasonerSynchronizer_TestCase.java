package edu.stanford.protege.reasoning.util;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/09/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class ReasonerSynchronizer_TestCase {

    @Mock
    private KbId kbId;

    @Mock
    private KbDigest kbDigest;

    @Mock
    private ReasoningService reasoningService;

    private ImmutableSortedSet<OWLLogicalAxiom> axioms;

    private ReasonerSynchronizer synchronizer;



    @Before
    public void setUp() throws Exception {
        DefaultPrefixManager pm = new DefaultPrefixManager();
        pm.setDefaultPrefix("http://other.com/");
        synchronizer = new ReasonerSynchronizer(kbId, reasoningService);
        axioms = ImmutableSortedSet.<OWLLogicalAxiom>naturalOrder()
                .add(SubClassOf(Class("A", pm), Class("B", pm)))
                .add(SubClassOf(Class("B", pm), Class("C", pm)))
                .build();

    }

    @Test
    public void shouldNotReplaceAxiomsOrApplyChangesIfDigestIsAsExpected() throws InterruptedException, ExecutionException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(
                Futures.immediateFuture(new GetKbDigestResponse(kbId, KbDigest.getDigest(axioms)))
        );
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(axioms);
        future.get();
        verify(reasoningService, never()).execute(isA(ReplaceAxiomsAction.class));
        verify(reasoningService, never()).execute(isA(ApplyChangesAction.class));
    }

    @Test
    public void shouldReplaceAxiomsIfDigestIsNotAsExpected() throws InterruptedException, ExecutionException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(Futures.immediateFuture(new GetKbDigestResponse(kbId, kbDigest)));
        when(reasoningService.execute(isA(ReplaceAxiomsAction.class))).thenReturn(
                Futures.immediateFuture(new ReplaceAxiomsResponse(kbId, kbDigest))
        );
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(axioms);
        future.get();
        verify(reasoningService, times(1)).execute(isA(ReplaceAxiomsAction.class));
        verify(reasoningService, never()).execute(isA(ApplyChangesAction.class));
    }

    @Test
    public void shouldApplyChangesIfDigestIsNotAsExpected() throws InterruptedException, ExecutionException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(
                Futures.immediateFuture(new GetKbDigestResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ApplyChangesAction.class))).thenReturn(
                // Return expected digest
                Futures.immediateFuture(new ApplyChangesResponse(kbId, KbDigest.getDigest(axioms)))
        );
        when(reasoningService.execute(isA(ReplaceAxiomsAction.class))).thenReturn(
                Futures.immediateFuture(new ReplaceAxiomsResponse(kbId, kbDigest))
        );
        MinimizedLogicalAxiomChanges changes = mock(MinimizedLogicalAxiomChanges.class);
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(changes, axioms);
        future.get();
        verify(reasoningService, times(1)).execute(isA(ApplyChangesAction.class));
        verify(reasoningService, never()).execute(isA(ReplaceAxiomsAction.class));
    }

    @Test
    public void shouldReplaceAxiomsIfApplyChangesDigestIsNotAsExpected() throws InterruptedException, ExecutionException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(
                Futures.immediateFuture(new GetKbDigestResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ApplyChangesAction.class))).thenReturn(
                // Return unexpected digest
                Futures.immediateFuture(new ApplyChangesResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ReplaceAxiomsAction.class))).thenReturn(
                Futures.immediateFuture(new ReplaceAxiomsResponse(kbId, kbDigest))
        );
        MinimizedLogicalAxiomChanges changes = mock(MinimizedLogicalAxiomChanges.class);
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(changes, axioms);
        future.get();
        verify(reasoningService, times(1)).execute(isA(ApplyChangesAction.class));
        verify(reasoningService, times(1)).execute(isA(ReplaceAxiomsAction.class));
    }

    @Test
    public void shouldPropagateTimeOutExceptionOnReplaceAxioms() throws InterruptedException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(
                Futures.immediateFuture(new GetKbDigestResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ReplaceAxiomsAction.class))).thenReturn(
                Futures.<ReplaceAxiomsResponse>immediateFailedFuture(new ReasonerTimeOutException())
        );
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(axioms);
        try {
            future.get();
            fail("Expected ExecutionException to be thrown");
        } catch (ExecutionException e) {
            verify(reasoningService, times(1)).execute(isA(GetKbDigestAction.class));
            verify(reasoningService, times(1)).execute(isA(ReplaceAxiomsAction.class));
            assertThat(e.getCause() instanceof ReasonerTimeOutException, is(true));
        }
    }

    @Test
    public void shouldPropagateTimeOutExceptionOnApplyChanges() throws InterruptedException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(
                Futures.immediateFuture(new GetKbDigestResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ApplyChangesAction.class))).thenReturn(
                Futures.<ApplyChangesResponse>immediateFailedFuture(new ReasonerTimeOutException())
        );
        MinimizedLogicalAxiomChanges changes = mock(MinimizedLogicalAxiomChanges.class);
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(changes, axioms);
        try {
            future.get();
            fail("Expected ExecutionException to be thrown");
        } catch (ExecutionException e) {
            verify(reasoningService, times(1)).execute(isA(ApplyChangesAction.class));
            assertThat(e.getCause() instanceof ReasonerTimeOutException, is(true));
        }
    }

    @Test
    public void shouldPropagateTimeOutExceptionOnReplaceAxiomsAfterFailedApplyChanges() throws InterruptedException {
        when(reasoningService.execute(isA(GetKbDigestAction.class))).thenReturn(
                Futures.immediateFuture(new GetKbDigestResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ApplyChangesAction.class))).thenReturn(
                // Return unexpected digest
                Futures.immediateFuture(new ApplyChangesResponse(kbId, kbDigest))
        );
        when(reasoningService.execute(isA(ReplaceAxiomsAction.class))).thenReturn(
                Futures.<ReplaceAxiomsResponse>immediateFailedFuture(new ReasonerTimeOutException())
        );
        MinimizedLogicalAxiomChanges changes = mock(MinimizedLogicalAxiomChanges.class);
        ListenableFuture<KbDigest> future = synchronizer.synchronizeReasoner(changes, axioms);
        try {
            future.get();
            fail("Expected ExecutionException to be thrown");
        } catch (ExecutionException e) {
            verify(reasoningService, times(1)).execute(isA(GetKbDigestAction.class));
            verify(reasoningService, times(1)).execute(isA(ApplyChangesAction.class));
            verify(reasoningService, times(1)).execute(isA(ReplaceAxiomsAction.class));
            assertThat(e.getCause() instanceof ReasonerTimeOutException, is(true));
        }
    }
}
