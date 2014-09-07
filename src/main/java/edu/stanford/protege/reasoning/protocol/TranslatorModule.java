package edu.stanford.protege.reasoning.protocol;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import static com.google.inject.Scopes.SINGLETON;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 25/08/2014
 */
public class TranslatorModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(new TypeLiteral<Translator<KbId, Messages.KbId>>() {})
                .to(KbIdTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<OWLLogicalAxiom, Messages.Axiom>>() {})
                .to(AxiomTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<KbDigest, Messages.KbDigest>>(){})
                .to(KbDigestTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<Consistency, Messages.Consistency>>(){})
                .to(ConsistencyTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<Entailed, Messages.EntailmentCheckResult>>(){})
                .to(EntailedTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<Node<OWLClass>, Messages.ClassHierarchyNode>>() {})
                .to(ClassNodeTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<HierarchyQueryType, Messages.HierarchyQueryType>>() {})
                .to(HierachyQueryTypeTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<OWLClass, Messages.ClassName>>() {
        })
                .to(ClassTranslator.class).in(SINGLETON);

        bind(OWLDataFactory.class).to(OWLDataFactoryImpl.class).in(SINGLETON);

        bind(OWLClassProvider.class).to(OWLDataFactory.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<OWLNamedIndividual, Messages.IndividualName>>(){})
            .to(NamedIndividualTranslator.class).in(SINGLETON);

        bind(BinaryOWLHelper.class).in(SINGLETON);

        bind(OWLDataFactory.class).to(OWLDataFactoryImpl.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<OWLClassExpression, Messages.ClassExpression>>(){})
                .to(ClassExpressionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<IsConsistentAction, Messages.IsConsistentActionMessage>>() {})
                .to(IsConsistentActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<IsConsistentResponse, Messages.IsConsistentResponseMessage>>(){})
                .to(IsConsistentResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<IsEntailedAction, Messages.IsEntailedActionMessage>>() {})
                .to(IsEntailedActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<IsEntailedResponse, Messages.IsEntailedResponseMessage>>() {})
                .to(IsEntailedResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetSubClassesAction, Messages.GetSubClassesActionMessage>>() {})
                .to(GetSubClassesActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetSubClassesResponse, Messages.GetSubClassesResponseMessage>>() {})
                .to(GetSubClassesResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetInstancesAction, Messages.GetInstancesActionMessage>>() {
        })
                .to(GetInstancesActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetInstancesResponse, Messages.GetInstancesResponseMessage>>() {})
                .to(GetInstancesResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<HierarchyQueryType, Messages.HierarchyQueryType>>() {
        })
                .to(HierachyQueryTypeTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<ApplyChangesAction, Messages.ApplyChangesActionMessage>>(){})
                .to(ApplyChangesActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<ApplyChangesResponse, Messages.ApplyChangesResponseMessage>>() {})
                .to(ApplyChangesResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetKbDigestAction, Messages.GetKbDigestActionMessage>>() {})
                .to(GetKbDigestActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetKbDigestResponse, Messages.GetKbDigestResponseMessage>>() {})
                .to(GetKbDigestResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<ReplaceAxiomsAction, Messages.ReplaceAxiomsActionMessage>>() {})
                .to(ReplaceAxiomsActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<ReplaceAxiomsResponse, Messages.ReplaceAxiomsResponseMessage>>() {})
                .to(ReplaceAxiomsResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<ReasonerState, Messages.ReasonerState>>() {})
                .to(ReasonerStateTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetReasonerStateAction, Messages.GetReasonerStateActionMessage>>() {})
                .to(GetReasonerStateActionTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<GetReasonerStateResponse, Messages.GetReasonerStateResponseMessage>>() {})
                .to(GetReasonerStateResponseTranslator.class).in(SINGLETON);

        bind(new TypeLiteral<Translator<Progress, Messages.Progress>>() {})
                .to(ProgressTranslator.class).in(SINGLETON);

    }
}
