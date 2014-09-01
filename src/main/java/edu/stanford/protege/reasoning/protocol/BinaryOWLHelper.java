package edu.stanford.protege.reasoning.protocol;

import com.google.protobuf.ByteString;
import org.semanticweb.binaryowl.BinaryOWLVersion;
import org.semanticweb.binaryowl.change.OntologyChangeDataType;
import org.semanticweb.binaryowl.owlobject.OWLObjectBinaryType;
import org.semanticweb.binaryowl.stream.BinaryOWLInputStream;
import org.semanticweb.binaryowl.stream.BinaryOWLOutputStream;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 21/08/2014
 */
public class BinaryOWLHelper {

    private static final BinaryOWLVersion VERSION = BinaryOWLVersion.getVersion(1);

    private OWLDataFactory dataFactory;

    @Inject
    public BinaryOWLHelper(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public <T extends OWLObject> T decode(ByteString bytes) {
        try {
            return OWLObjectBinaryType.read(getInputStream(bytes));
        } catch (IOException e) {
            throw new BinaryOWLCodecException(e);
        }
    }

    public ByteString encode(OWLObject object) {
        try {
            ByteString.Output output = ByteString.newOutput();
            OWLObjectBinaryType.write(object, new BinaryOWLOutputStream(output, VERSION));
            return output.toByteString();

        } catch (IOException e) {
            throw new BinaryOWLCodecException(e);
        }
    }

    public AxiomChangeData decodeChangeData(ByteString bytes) {
        try {
            return (AxiomChangeData) OntologyChangeDataType.read(getInputStream(bytes));
        } catch (IOException e) {
            throw new BinaryOWLCodecException(e);
        }
    }

    public ByteString encodeChangeData(AxiomChangeData changeData) {
        try {
            ByteString.Output output = ByteString.newOutput();
            OntologyChangeDataType.write(changeData, new BinaryOWLOutputStream(output, VERSION));
            return output.toByteString();
        } catch (IOException e) {
            throw new BinaryOWLCodecException(e);
        }
    }


    private BinaryOWLInputStream getInputStream(ByteString bytes) {
        return new BinaryOWLInputStream(bytes.newInput(), dataFactory, VERSION);
    }



    private static class BinaryOWLCodecException extends RuntimeException {

        public BinaryOWLCodecException(IOException cause) {
            super(cause);
        }
    }



}
