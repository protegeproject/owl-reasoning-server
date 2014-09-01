package edu.stanford.protege.reasoning.protocol;

import com.google.common.collect.Sets;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNode;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static edu.stanford.protege.reasoning.protocol.Messages.ClassHierarchyNode;
import static edu.stanford.protege.reasoning.protocol.Messages.ClassName;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 21/08/2014
 */
public class ClassNodeTranslator implements Translator<Node<OWLClass>, Messages.ClassHierarchyNode> {

    private final Translator<OWLClass, Messages.ClassName> classNameTranslator;

    @Inject
    public ClassNodeTranslator(Translator<OWLClass, Messages.ClassName> classNameTranslator) {
        this.classNameTranslator = classNameTranslator;
    }

    @Override
    public Node<OWLClass> decode(ClassHierarchyNode message) {
        Set<OWLClass> clses = Sets.newHashSet();
        for(ClassName className : message.getClassNamesList()) {
            clses.add(classNameTranslator.decode(className));
        }
        return new OWLClassNode(clses);
    }

    @Override
    public ClassHierarchyNode encode(Node<OWLClass> object) {
        ClassHierarchyNode.Builder builder = ClassHierarchyNode.newBuilder();
        for(OWLClass cls : object.getEntities()) {
            builder.addClassNames(ClassName.newBuilder().setIri(cls.getIRI().toString()));
        }
        return builder.build();
    }

    @Override
    public Node<OWLClass> decode(byte[] bytes) throws IOException {
        return decode(ClassHierarchyNode.parseFrom(bytes));
    }
}
