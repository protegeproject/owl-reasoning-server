package edu.stanford.protege.reasoning.util;

import com.google.common.collect.*;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/09/2014
 */
public class ReasonerSynchronizer {

    private ReasoningService reasoningService;

    private KbId kbId;

    public ReasonerSynchronizer(
            KbId kbId, ReasoningService reasoningService) {
        this.kbId = kbId;
        this.reasoningService = reasoningService;
    }


    /**
     * Pushes the specified axioms into the reasoner.
     * @param expectedAxioms The axioms to be pushed to the reasoner.
     * @return A future result representing the digest of the pushed axioms.  If the push succeeds then the reasoner
     * will contain the axioms with the specified digest.  The push may fail either because of a reasoner timeout,
     * denoted by a {@link ReasonerTimeOutException}, or some kind of internal reasoner error, denoted by a
     * {@link ReasonerInternalErrorException}.
     */
    public ListenableFuture<KbDigest> synchronizeReasoner(final ImmutableSortedSet<OWLLogicalAxiom> expectedAxioms) {
        return synchronizeReasoner(MinimizedLogicalAxiomChanges.empty(), expectedAxioms);
    }


    public ListenableFuture<KbDigest> synchronizeReasoner(
            final MinimizedLogicalAxiomChanges changes,
            final ImmutableSortedSet<OWLLogicalAxiom> expectedAxioms) {
        final SettableFuture<KbDigest> futureResult = SettableFuture.create();
        final ImmutableList<OWLAxiom> expectedAxiomsList = getExpectedAxioms(expectedAxioms);
        final KbDigest expectedDigest = KbDigest.getDigest(expectedAxioms);
        ListenableFuture<GetKbDigestResponse> digestFuture = reasoningService.execute(new GetKbDigestAction(kbId));
        Futures.addCallback(digestFuture, new FutureCallback<GetKbDigestResponse>() {
            @Override
            public void onSuccess(GetKbDigestResponse result) {
                final KbDigest reasonerDigest = result.getKbDigest();
                if (reasonerDigest.equals(expectedDigest)) {
                    // There's nothing to do.
                    futureResult.set(reasonerDigest);
                }
                else {
                    if (changes.isEmpty()) {
                        replaceAxiomsInReasoner(expectedAxiomsList, futureResult);
                    }
                    else {
                        applyChangesToReasoner(expectedAxiomsList, expectedDigest, changes, futureResult);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Propagate the exception
                futureResult.setException(t);
            }
        });
        return futureResult;
    }


    private void replaceAxiomsInReasoner(
            ImmutableList<OWLAxiom> expectedAxiomsList, final SettableFuture<KbDigest> resultToReturn) {
        // Replace all of the axioms in the reasoner
        ListenableFuture<ReplaceAxiomsResponse> replaceAxiomsFuture = reasoningService.execute(new ReplaceAxiomsAction(
                kbId,
                expectedAxiomsList));
        Futures.addCallback(replaceAxiomsFuture, new FutureCallback<ReplaceAxiomsResponse>() {
            @Override
            public void onSuccess(ReplaceAxiomsResponse result) {
                resultToReturn.set(result.getKbDigest());
            }

            @Override
            public void onFailure(Throwable t) {
                // Propagate exception
                resultToReturn.setException(t);
            }
        });
    }


    private void applyChangesToReasoner(
            final ImmutableList<OWLAxiom> expectedAxiomsList,
            final KbDigest expectedDigest,
            MinimizedLogicalAxiomChanges changes,
            final SettableFuture<KbDigest> resultToReturn) {
        // Apply the changes
        ListenableFuture<ApplyChangesResponse> replaceAxiomsFuture = reasoningService.execute(new ApplyChangesAction(
                kbId,
                ImmutableList.copyOf(changes.getLogicalAxiomChangeData())));
        Futures.addCallback(replaceAxiomsFuture, new FutureCallback<ApplyChangesResponse>() {
            @Override
            public void onSuccess(ApplyChangesResponse result) {
                // Is this what is expected?
                if (expectedDigest.equals(result.getKbDigest())) {
                    resultToReturn.set(result.getKbDigest());
                }
                else {
                    // Something went wrong somewhere.  Do a full blown synchronization.
                    replaceAxiomsInReasoner(expectedAxiomsList, resultToReturn);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Propagate exception
                resultToReturn.setException(t);
            }
        });
    }

    private ImmutableList<OWLAxiom> getExpectedAxioms(ImmutableCollection<OWLLogicalAxiom> expectedAxioms) {
        return ImmutableList.<OWLAxiom>copyOf(expectedAxioms);
    }
}
