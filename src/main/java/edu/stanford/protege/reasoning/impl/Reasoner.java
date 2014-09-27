package edu.stanford.protege.reasoning.impl;

import com.google.common.base.Optional;
import edu.stanford.protege.reasoning.*;
import edu.stanford.protege.reasoning.action.Consistency;
import edu.stanford.protege.reasoning.KbQueryResult;
import edu.stanford.protege.reasoning.action.Entailed;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.*;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 15/04/2014
 */
public interface Reasoner {

    KbDigest getKbDigest();

    /**
     * Determines if the set of reasoner axioms is consistent. Note that this
     * method will NOT throw an
     * {@link org.semanticweb.owlapi.reasoner.InconsistentOntologyException}
     * even if the root ontology imports closure is inconsistent.
     *
     * @return {@code true} if the imports closure of the root ontology is
     *         consistent, or {@code false} if the imports closure of the root
     *         ontology is inconsistent.
     */
     Optional<Consistency> getConsistency();

    /**
     * A convenience method that determines if the specified axiom is entailed
     * by the set of reasoner axioms.
     *
     * @param axiom
     *        The axiom
     * @return {@code true} if {@code axiom} is entailed by the reasoner axioms
     *         or {@code false} if {@code axiom} is not entailed by the reasoner
     *         axioms. {@code true} if the set of reasoner axioms is
     *         inconsistent.
     */
    Optional<KbQueryResult<Entailed>> isEntailed(OWLAxiom axiom);


    /**
     * Gets the set of named classes that are the strict (potentially direct)
     * subclasses of the specified class expression with respect to the reasoner
     * axioms. Note that the classes are returned as a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param ce
     *        The class expression whose strict (direct) subclasses are to be
     *        retrieved.
     * @param direct
     *        Specifies if the direct subclasses should be retrived (
     *        {@code true}) or if the all subclasses (descendant) classes should
     *        be retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code DirectSubClassOf(C, ce)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code StrictSubClassOf(C, ce)}. <br>
     *         If {@code ce} is equivalent to {@code owl:Nothing} then the empty
     *         {@code NodeSet} will be returned.
     */
    Optional<KbQueryResult<NodeSet<OWLClass>>> getSubClasses(OWLClassExpression ce, boolean direct);

    /**
     * Gets the set of named classes that are the strict (potentially direct)
     * super classes of the specified class expression with respect to the
     * imports closure of the root ontology. Note that the classes are returned
     * as a {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param ce
     *        The class expression whose strict (direct) super classes are to be
     *        retrieved.
     * @param direct
     *        Specifies if the direct super classes should be retrived (
     *        {@code true}) or if the all super classes (ancestors) classes
     *        should be retrieved ({@code false}).
     * @return If direct is {@code true}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code DirectSubClassOf(ce, C)}. <br>
     *         If direct is {@code false}, a {@code NodeSet} such that for each
     *         class {@code C} in the {@code NodeSet} the set of reasoner axioms
     *         entails {@code StrictSubClassOf(ce, C)}. <br>
     *         If {@code ce} is equivalent to {@code owl:Thing} then the empty
     *         {@code NodeSet} will be returned.
     */
    Optional<KbQueryResult<NodeSet<OWLClass>>> getSuperClasses(OWLClassExpression ce, boolean direct);

    /**
     * Gets the set of named classes that are equivalent to the specified class
     * expression with respect to the set of reasoner axioms. The classes are
     * returned as a {@link org.semanticweb.owlapi.reasoner.Node}.
     *
     * @param ce
     *        The class expression whose equivalent classes are to be retrieved.
     * @return A node containing the named classes such that for each named
     *         class {@code C} in the node the root ontology imports closure
     *         entails {@code EquivalentClasses(ce C)}. If {@code ce} is not a
     *         class name (i.e. it is an anonymous class expression) and there
     *         are no such classes {@code C} then the node will be empty. <br>
     *         If {@code ce} is a named class then {@code ce} will be contained
     *         in the node. <br>
     *         If {@code ce} is unsatisfiable with respect to the set of
     *         reasoner axioms then the node representing and containing
     *         {@code owl:Nothing}, i.e. the bottom node, will be returned. <br>
     *         If {@code ce} is equivalent to {@code owl:Thing} with respect to
     *         the set of reasoner axioms then the node representing and
     *         containing {@code owl:Thing}, i.e. the top node, will be returned <br>
     */
    Optional<KbQueryResult<Node<OWLClass>>> getEquivalentClasses(OWLClassExpression ce);


    /**
     * Gets the individuals which are instances of the specified class
     * expression. The individuals are returned a a
     * {@link org.semanticweb.owlapi.reasoner.NodeSet}.
     *
     * @param ce
     *        The class expression whose instances are to be retrieved.
     * @param direct
     *        Specifies if the direct instances should be retrieved (
     *        {@code true}), or if all instances should be retrieved (
     *        {@code false}).
     * @return If {@code direct} is {@code true}, a {@code NodeSet} containing
     *         named individuals such that for each named individual {@code j}
     *         in the node set, the set of reasoner axioms entails
     *         {@code DirectClassAssertion(ce, j)}. <br>
     *         If {@code direct} is {@code false}, a {@code NodeSet} containing
     *         named individuals such that for each named individual {@code j}
     *         in the node set, the set of reasoner axioms entails
     *         {@code ClassAssertion(ce, j)}. <br>
     *         If ce is unsatisfiable with respect to the set of reasoner axioms
     *         then the empty {@code NodeSet} is returned.
     */
    Optional<KbQueryResult<NodeSet<OWLNamedIndividual>>> getInstances(OWLClassExpression ce,
                                             boolean direct);
}
