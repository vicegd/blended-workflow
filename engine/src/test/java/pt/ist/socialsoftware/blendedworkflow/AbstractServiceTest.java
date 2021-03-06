package pt.ist.socialsoftware.blendedworkflow;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.worklet.rdr.RdrNode;
import org.yawlfoundation.yawl.worklet.rdr.RuleType;
import org.yawlfoundation.yawl.worklet.support.WorkletGatewayClient;

import jvstm.Transaction;
import pt.ist.socialsoftware.blendedworkflow.adapters.WorkletAdapter;
import pt.ist.socialsoftware.blendedworkflow.adapters.YAWLAdapter;
import pt.ist.socialsoftware.blendedworkflow.bwmanager.BWManager;
import pt.ist.socialsoftware.blendedworkflow.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.domain.WorkItem;
import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.service.execution.CreateBWInstanceService;
import pt.ist.socialsoftware.blendedworkflow.service.execution.LoadBWSpecificationService;
import pt.ist.socialsoftware.blendedworkflow.shared.Bootstrap;
import pt.ist.socialsoftware.blendedworkflow.shared.StringUtils;
import pt.ist.socialsoftware.blendedworkflow.worklistmanager.WorkListManager;

@RunWith(JMock.class)
public abstract class AbstractServiceTest {

    protected static final String BWSPECIFICATION_FILENAME = "src/test/xml/MedicalEpisode/MedicalEpisodeTest.xml";
    protected static final String BWSPECIFICATION_NAME = "Medical Appointment";
    protected static final String YAWLCASE_ID = "yawlCaseID";
    protected static final String USER_ID = "BlendedWorkflow";

    protected final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
            setThreadingPolicy(new Synchroniser());
        }
    };
    private Expectations initialExpectations;

    protected WorkletAdapter workletAdapter;
    protected WorkletGatewayClient workletGatewayClient;
    protected BWManager bwManager;
    protected WorkListManager workListManager;
    protected YAWLAdapter yawlAdapter;
    protected final YSpecificationID yawlSpec = new YSpecificationID(
            "UID_3213f8b4-5757-4674-a6a2-415aa191ca91", "1.0",
            "MedicalAppointment");

    @Before
    public void initialSetUp() throws Exception {
        Bootstrap.initTestDB();
        yawlAdapter = context.mock(YAWLAdapter.class);
        workletGatewayClient = context.mock(WorkletGatewayClient.class);
        workletAdapter = new WorkletAdapter(workletGatewayClient);
        bwManager = context.mock(BWManager.class);
        workListManager = context.mock(WorkListManager.class);

        Transaction.begin();
        BlendedWorkflow.getInstance().setYawlAdapter(yawlAdapter);
        BlendedWorkflow.getInstance().setWorkletAdapter(workletAdapter);
        BlendedWorkflow.getInstance().setBwManager(bwManager);
        BlendedWorkflow.getInstance().setWorkListManager(workListManager);
        Transaction.commit();

        setInitialExpectations();
        context.checking(initialExpectations);

    }

    private void setInitialExpectations() throws Exception {
        final ArrayList<YSpecificationID> specIds = new ArrayList<YSpecificationID>();
        specIds.add(yawlSpec);
        initialExpectations = new Expectations() {
            {
                oneOf(yawlAdapter).loadSpecification(with(any(String.class)));
                oneOf(yawlAdapter).getLoadedActivitySpecs();
                will(returnValue(specIds));
                allowing(workletGatewayClient).addNode(
                        with(any(YSpecificationID.class)),
                        with(any(String.class)), with(any(RuleType.class)),
                        with(any(RdrNode.class)), with(any(String.class)));
                oneOf(bwManager).notifyLoadedBWSpecification(
                        with(any(BWSpecification.class)));
            }
        };
    }

    protected void initializeSpecification() throws Exception {
        final String bwSpecificationString = StringUtils
                .fileToString(BWSPECIFICATION_FILENAME);
        new LoadBWSpecificationService(bwSpecificationString).call();
    }

    protected void initializeBWInstance() throws BWException, Exception {
        context.checking(new Expectations() {
            {
                oneOf(yawlAdapter).launchCase(with(any(String.class)));
                will(returnValue(YAWLCASE_ID));
                oneOf(bwManager)
                        .notifyCreatedBWInstance(with(any(BWInstance.class)));
                allowing(workListManager)
                        .notifyEnabledWorkItem(with(any(WorkItem.class)));
            }
        });

        final BWSpecification bwSpecification = getBWSpecification(
                BWSPECIFICATION_NAME);
        new CreateBWInstanceService(bwSpecification.getExternalId(), "",
                USER_ID).call();
    }

    @After
    public void tearDown() {
        Bootstrap.clean();
    }

    protected BWSpecification getBWSpecification(String name)
            throws BWException {
        Transaction.begin();
        final BWSpecification bwSpecification = BlendedWorkflow.getInstance()
                .getSpecById(name).orElseThrow(() -> new BWException(
                        BWErrorType.INVALID_SPECIFICATION_NAME, name));
        Transaction.commit();
        return bwSpecification;
    }

    protected BWInstance getBWInstance(BWSpecification bwSpecification) {
        Transaction.begin();
        List<BWInstance> bwInstances = new ArrayList<BWInstance>(
                bwSpecification.getBwInstancesSet());
        final BWInstance bwInstance = bwInstances.get(0);
        Transaction.commit();
        return bwInstance;
    }

}
