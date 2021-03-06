package pt.ist.socialsoftware.blendedworkflow.adapters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.util.JDOMUtil;
import org.yawlfoundation.yawl.worklet.rdr.RdrNode;
import org.yawlfoundation.yawl.worklet.rdr.RuleType;
import org.yawlfoundation.yawl.worklet.support.WorkletGatewayClient;

import pt.ist.socialsoftware.blendedworkflow.domain.BWAttribute;
import pt.ist.socialsoftware.blendedworkflow.domain.AttributeInstance;
import pt.ist.socialsoftware.blendedworkflow.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.domain.Condition.ConditionType;
import pt.ist.socialsoftware.blendedworkflow.domain.BWDataModel.DataState;
import pt.ist.socialsoftware.blendedworkflow.domain.BWEntity;
import pt.ist.socialsoftware.blendedworkflow.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.domain.Task;
import pt.ist.socialsoftware.blendedworkflow.domain.TaskModel;
import pt.ist.socialsoftware.blendedworkflow.domain.TaskModelInstance;
import pt.ist.socialsoftware.blendedworkflow.domain.TaskWorkItem;
import pt.ist.socialsoftware.blendedworkflow.domain.TaskWorkItem.ActivityState;
import pt.ist.socialsoftware.blendedworkflow.domain.WorkItem;
import pt.ist.socialsoftware.blendedworkflow.domain.WorkItemArgument;
import pt.ist.socialsoftware.blendedworkflow.service.BWErrorType;
import pt.ist.socialsoftware.blendedworkflow.service.BWException;
import pt.ist.socialsoftware.blendedworkflow.shared.BWPropertiesManager;

public class WorkletAdapter {

    private final Logger log;
    protected String engineAdminUser = BWPropertiesManager
            .getProperty("yawl.AdminUser");
    protected String engineAdminPassword = BWPropertiesManager
            .getProperty("yawl.AdminPassword");
    protected String workletGateway = BWPropertiesManager
            .getProperty("worklet.gateway");

    private WorkletGatewayClient client = null;
    private String handle = null;
    private final ConcurrentHashMap<WorkItemRecord, String> yawlEnabledWIR = new ConcurrentHashMap<WorkItemRecord, String>(); // WIR:WorkItemID

    public WorkletAdapter() {
        log = Logger.getLogger("WorkletAdpater");
        registerWorkletListener();
    }

    // Note: This constructor is used for testing purposes only
    public WorkletAdapter(WorkletGatewayClient client) {
        log = Logger.getLogger("WorkletAdpater");
        this.client = client;
        this.handle = "NO HANDLE";
    }

    /*************************************************
     * WorkletAdapterEventListener Services Methods
     *************************************************/
    /**
     * Process the notification by the WorkletService of a Workitem PreCondition
     * evaluation.
     * 
     * @param wir
     *            the YAWL enabled WorkItemRecord.
     * @param result
     *            the ItemPreConstraint result.
     */
    public void notifyWorkItemPreConditionResult(WorkItemRecord wir,
            String result) {
        TaskWorkItem taskWorkItem = null;
        try {
            BWInstance bwInstance = BlendedWorkflow.getInstance()
                    .getBWInstanceFromYAWLCaseID(wir.getCaseID());
            TaskModelInstance taskModelInstance = bwInstance
                    .getTaskModelInstance();

            String taskName = wir.getTaskName().replaceAll("_", " ");
            Task task = taskModelInstance.getTask(taskName);
            taskWorkItem = new TaskWorkItem(bwInstance, task);

            associateWorkItemRecordWithTaskWorkItem(wir, taskWorkItem);
        } catch (BWException bwe) {
            log.error("notifyNewTaskWorkItem: exception: " + bwe.getError());
        }

        processPreConditionEvaluationResult(result, taskWorkItem);
    }

    /**
     * Process the notification by the WorkletService of a Workitem PreCondition
     * evaluation.
     * 
     * @param wir
     *            a YAWL enabled WorkItemRecord.
     * @param result
     *            the ItemConstrainViolation result.
     */
    public void notifyWorkItemPostConditionResult(WorkItemRecord wir,
            String result) {
        TaskWorkItem taskWorkItem = getTaskWorkItem(wir);
        processPostConditionEvaluationResult(result, taskWorkItem);
    }

    /*********************************
     * Process Conditions Result
     *********************************/
    private void processPreConditionEvaluationResult(String result,
            TaskWorkItem taskWorkItem) {
        if (result.equals("TRUE")) {
            taskWorkItem.notifyEnabled(ConditionType.PRE_CONDITION);
        } else if (result.equals("SKIPPED") || result.equals("FALSE")) {
            taskWorkItem.notifyPreActivity(ConditionType.PRE_CONDITION);
        } else {
            log.error("It should not reach this point");
        }
        // requestWorkItemPostConditionEvaluation(taskWorkItem); //FIXME: call 2
        // times
    }

    private void processPostConditionEvaluationResult(String result,
            TaskWorkItem taskWorkItem) {
        if (result.equals("TRUE")) {
            taskWorkItem.notifyCompleted();
        } else if (result.equals("SKIPPED")) {
            taskWorkItem.notifySkipped();
        } else if (result.equals("FALSE")) {
            if (taskWorkItem.getState().equals(ActivityState.ENABLED)) {
                taskWorkItem.notifyEnabled(ConditionType.POS_CONDITION);
            } else if (taskWorkItem.getState()
                    .equals(ActivityState.PRE_ACTIVITY)) {
                taskWorkItem.notifyPreActivity(ConditionType.POS_CONDITION);
            }
        }
    }

    /*********************************
     * BWEngine Methods
     *********************************/
    /**
     * Evaluate a TaskWorkitem PreConditon.
     * 
     * @param taskWorkItem
     *            the taskWorkItem to evaluate.
     */
    public void requestWorkItemPreConditionEvaluation(
            TaskWorkItem taskWorkItem) {
        try {
            evaluatePreCondition(taskWorkItem);
        } catch (BWException bwe) {
            log.error("notifyWorkItemContraintViolation: exception"
                    + bwe.getMessage());
        }
    }

    /**
     * Evaluate GoalWorkitem condition or Process TaskWorkitem postCondition.
     * 
     * @param workItem
     *            the workItem to evaluate.
     */
    public void requestWorkItemPostConditionEvaluation(
            TaskWorkItem taskWorkItem) {
        log.debug("requestWorkItemPostConditionEvaluation"
                + taskWorkItem.getID());
        try {
            process(taskWorkItem);
        } catch (BWException bwe) {
            log.error("notifyWorkItemContraintViolation: exception"
                    + bwe.getMessage());
        }
    }

    /*********************************
     * RdrSet
     *********************************/
    /**
     * Create and Load a specification RdrSet.
     * 
     * @param bwSpecification
     *            the BWSpecification.
     * @throws BWException
     */
    public void loadRdrSet(BWSpecification bwSpecification) throws BWException {
        TaskModel taskModel = bwSpecification.getTaskModel();
        YSpecificationID yawlSpecID = getYAWLSpecificationID(bwSpecification);
        String condition = null;
        Element eConclusion = null;
        Element eCornerstone = null;

        // Create Tasks RdrSet
        for (Task task : taskModel.getTasksSet()) {
            String taskName = generateYAWLTaskName(task);

            // PreCondition Tree
            if (!task.getPreConstraint().existTrue()) {
                // Undefined Node
                eCornerstone = getCornerstoneData(task, true, "UNDEFINED");
                condition = task.getPreConstraint().getRdrUndefinedCondition();
                eConclusion = createRdrConclusion("UNDEFINED", true);
                addNode(yawlSpecID, taskName, condition, eCornerstone,
                        eConclusion, RuleType.ItemPreconstraint);

                // Skipped Node
                eCornerstone = getCornerstoneData(task, true, "SKIPPED");
                condition = task.getPreConstraint().getRdrSkippedCondition();
                eConclusion = createRdrConclusion("SKIPPED", true);
                addNode(yawlSpecID, taskName, condition, eCornerstone,
                        eConclusion, RuleType.ItemPreconstraint);

                // True Node
                eCornerstone = getCornerstoneData(task, true, "DEFINED");
                condition = task.getPreConstraint().getRdrTrueCondition();
                eConclusion = createRdrConclusion("TRUE", true);
                addNode(yawlSpecID, taskName, condition, eCornerstone,
                        eConclusion, RuleType.ItemPreconstraint);

                // False Node
                if (task.getPreConstraint().existCompareAttributeToValue()) {
                    eCornerstone = getCornerstoneData(task, true, "DEFINED");
                    condition = task.getPreConstraint().getRdrFalseCondition();
                    eConclusion = createRdrConclusion("FALSE", true);
                    addNode(yawlSpecID, taskName, condition, eCornerstone,
                            eConclusion, RuleType.ItemPreconstraint);
                }
            } else {
                eCornerstone = getCornerstoneData(task, true, "DEFINED");
                condition = task.getPreConstraint().getRdrTrueCondition();
                eConclusion = createRdrConclusion("TRUE", true);
                addNode(yawlSpecID, taskName, condition, eCornerstone,
                        eConclusion, RuleType.ItemPreconstraint);
            }

            // PostCondition Tree
            // Undefined Node
            eCornerstone = getCornerstoneData(task, false, "UNDEFINED");
            condition = task.getPostConstraint().getRdrUndefinedCondition();
            eConclusion = createRdrConclusion("UNDEFINED", false);
            addNode(yawlSpecID, taskName, condition, eCornerstone, eConclusion,
                    RuleType.ItemConstraintViolation);

            // Skipped Node
            eCornerstone = getCornerstoneData(task, false, "SKIPPED");
            condition = task.getPostConstraint().getRdrSkippedCondition();
            eConclusion = createRdrConclusion("SKIPPED", false);
            addNode(yawlSpecID, taskName, condition, eCornerstone, eConclusion,
                    RuleType.ItemConstraintViolation);

            // True Node
            eCornerstone = getCornerstoneData(task, false, "DEFINED");
            condition = task.getPostConstraint().getRdrTrueCondition();
            eConclusion = createRdrConclusion("TRUE", false);
            addNode(yawlSpecID, taskName, condition, eCornerstone, eConclusion,
                    RuleType.ItemConstraintViolation);

            // False Node
            if (task.getPostConstraint().existCompareAttributeToValue()) {
                eCornerstone = getCornerstoneData(task, false, "DEFINED");
                condition = task.getPostConstraint().getRdrFalseCondition();
                eConclusion = createRdrConclusion("FALSE", false);
                addNode(yawlSpecID, taskName, condition, eCornerstone,
                        eConclusion, RuleType.ItemConstraintViolation);
            }
        }
    }

    /*******************************
     * Get Data
     *******************************/
    /**
     * Get Conditions Data for a Task
     * 
     * @param task
     *            a Task.
     * @param isPreCondition
     *            if its a Task, get the ItemPreConstraint data.
     * @param type
     *            the type of CornerstoneData, i.e DEFINED, UNDEFINED or
     *            SKIPPED.
     * @return an ELement with the cornerstoneData.
     */
    private Element getCornerstoneData(Task task, Boolean isPreCondition,
            String type) {
        String cornerStr = "<cornerstone>";
        Set<BWEntity> entities = null;
        Set<BWAttribute> attributes = null;
        HashMap<BWAttribute, String> attributesValues = null;

        // Get Condition Data
        if (task != null && isPreCondition) {
            entities = task.getPreConstraint().getEntities();
            attributes = task.getPreConstraint().getAttributes();
            attributesValues = task.getPreConstraint()
                    .getcompareConditionValues();
        } else if (task != null) {
            entities = task.getPostConstraint().getEntities();
            attributes = task.getPostConstraint().getAttributes();
            attributesValues = task.getPostConstraint()
                    .getcompareConditionValues();
        }

        if (attributes != null) {
            Iterator<BWAttribute> it = attributes.iterator();
            while (it.hasNext()) {
                BWAttribute attribute = it.next();
                BWEntity entity = attribute.getEntity();
                if (entities.contains(entity)
                        && !attributesValues.containsKey(attribute)
                        && attribute.getIsKeyAttribute()) {
                    it.remove();
                }
            }
        }
        if (entities != null) {
            // Parse complete entities
            for (BWEntity entity : entities) {
                String entityName = entity.getName().replaceAll(" ", "");
                for (BWAttribute attribute : entity.getAttributesSet()) {
                    if (attribute.getIsKeyAttribute()) {
                        String attributeName = attribute.getName()
                                .replaceAll(" ", "");
                        String value;
                        if (type.equals("UNDEFINED"))
                            value = "$UNDEFINED$";
                        else if (type.equals("SKIPPED"))
                            value = "$SKIPPED$";
                        else
                            value = "$DEFINED$";

                        cornerStr += "<" + entityName + "_" + attributeName
                                + "_State" + ">" + type + "</" + entityName
                                + "_" + attributeName + "_State" + ">";
                        cornerStr += "<" + entityName + "_" + attributeName
                                + ">" + value + "</" + entityName + "_"
                                + attributeName + ">";
                    }
                }
            }
        }
        // Parse single attributes
        if (attributes != null) {
            for (BWAttribute attribute : attributes) {
                String entityName = attribute.getEntity().getName()
                        .replaceAll(" ", "");
                String attributeName = attribute.getName().replaceAll(" ", "");
                String value = attributesValues.get(attribute);
                if (value == null) {
                    if (type == "UNDEFINED")
                        value = "$UNDEFINED$";
                    else if (type == "SKIPPED")
                        value = "$SKIPPED$";
                    else
                        value = "$DEFINED$";
                }

                cornerStr += "<" + entityName + "_" + attributeName + "_State"
                        + ">" + type + "</" + entityName + "_" + attributeName
                        + "_State" + ">";
                cornerStr += "<" + entityName + "_" + attributeName + ">"
                        + value + "</" + entityName + "_" + attributeName + ">";
            }
        }

        cornerStr += "</cornerstone>";
        Element eCornerstone = JDOMUtil.stringToElement(cornerStr);
        return eCornerstone;
    }

    /**
     * Get PreCondition user submitted data.
     * 
     * @param taskWorkItem
     *            a TaskWorkItem.
     * @return an ELement with the evaluation data.
     */
    private Element getInputEvaluationData(TaskWorkItem taskWorkItem) {
        // Get Workitem
        WorkItem workItem = taskWorkItem;
        Set<WorkItemArgument> workItemArguments = workItem
                .getInputWorkItemArgumentsSet();

        // Get Workitem data
        String cornerStr = "<cornerstone>";
        for (WorkItemArgument workItemArgument : workItemArguments) {
            AttributeInstance attributeInstance = workItemArgument
                    .getAttributeInstance();
            String entityName = attributeInstance.getEntityInstance()
                    .getEntity().getName().replaceAll(" ", "");
            String attributeName = attributeInstance.getAttribute().getName()
                    .replaceAll(" ", "");
            DataState state = workItemArgument.getState();

            // FIXME: Today
            String value = workItemArgument.getValue();
            if (workItemArgument.getAttributeInstance().getAttribute()
                    .getIsSystem()) {
                if (value.equals(BlendedWorkflow.getInstance().getToday())) {
                    value = "" + "$TODAY$".hashCode();
                } else {
                    value = "0";
                }
            }

            cornerStr += "<" + entityName + "_" + attributeName + "_State" + ">"
                    + state + "</" + entityName + "_" + attributeName + "_State"
                    + ">";
            cornerStr += "<" + entityName + "_" + attributeName + ">" + value
                    + "</" + entityName + "_" + attributeName + ">";
        }
        cornerStr += "</cornerstone>";
        Element eCornerstone = JDOMUtil.stringToElement(cornerStr);
        return eCornerstone;
    }

    /**
     * Get PostCondition user submitted data.
     * 
     * @param taskWorkItem
     *            a TaskWorkItem.
     * @return an ELement with the evaluation data.
     */
    private Element getOutputEvaluationData(TaskWorkItem taskWorkItem) {
        // Get Workitem
        WorkItem workItem = taskWorkItem;
        Set<WorkItemArgument> workItemArguments = workItem
                .getOutputWorkItemArgumentsSet();

        // Get Workitem data
        String cornerStr = "<cornerstone>";
        for (WorkItemArgument workItemArgument : workItemArguments) {
            AttributeInstance attributeInstance = workItemArgument
                    .getAttributeInstance();
            String entityName = attributeInstance.getEntityInstance()
                    .getEntity().getName().replaceAll(" ", "");
            String attributeName = attributeInstance.getAttribute().getName()
                    .replaceAll(" ", "");
            DataState state = workItemArgument.getState();

            // FIXME: Today
            String value = workItemArgument.getValue();
            if (workItemArgument.getAttributeInstance().getAttribute()
                    .getIsSystem()) {
                if (value.equals(BlendedWorkflow.getInstance().getToday())) {
                    value = "" + "$TODAY$".hashCode();
                } else {
                    value = "0";
                }
            }

            cornerStr += "<" + entityName + "_" + attributeName + "_State" + ">"
                    + state + "</" + entityName + "_" + attributeName + "_State"
                    + ">";
            cornerStr += "<" + entityName + "_" + attributeName + ">" + value
                    + "</" + entityName + "_" + attributeName + ">";
        }
        cornerStr += "</cornerstone>";
        Element eCornerstone = JDOMUtil.stringToElement(cornerStr);
        return eCornerstone;
    }

    /*******************************
     * Rdr Conclusions
     *******************************/
    // FIXME:
    private Element createRdrConclusion(String type, Boolean isPreCondition) {
        String concStr = null;
        Element eConclusion = null;
        String action = "";
        Boolean constrainVilationSkip = false;

        if (type.equals("NULL")) {
            concStr = "<conclusion>null</conclusion>";
            eConclusion = JDOMUtil.stringToElement(concStr);
            return eConclusion;
        } else if (isPreCondition) {
            if (type.equals("TRUE")) {
                action = "TRUE";
            } else if (type.equals("FALSE") || type.equals("UNDEFINED")) {
                action = "FALSE";
            } else if (type.equals("SKIPPED")) {
                action = "SKIPPED";
            }
        } else {
            if (type.equals("TRUE")) {
                action = "complete";
            } else if (type.equals("FALSE") || type.equals("UNDEFINED")) {
                action = "FALSE";
            } else if (type.equals("SKIPPED")) {
                action = "complete";
                constrainVilationSkip = true;
            }
        }

        concStr = "<conclusion><_1>";
        concStr += "<action>" + action + "</action>";
        concStr += "<target>" + "workitem" + "</target>";
        concStr += "</_1>";

        if (constrainVilationSkip) {
            concStr += "<_2>";
            concStr += "<action>" + "SKIPPED" + "</action>";
            concStr += "<target>" + "workitem" + "</target>";
            concStr += "</_2>";
        }

        concStr += "</conclusion>";

        eConclusion = JDOMUtil.stringToElement(concStr);
        return eConclusion;
    }

    /**
     * Parse the RdrConclusion.
     * 
     * @param rdrNode
     *            the enabled RdrNode.
     * @return a string with the result.
     */
    // FIXME:
    public String parseConclusion(String conclusion) {
        if (conclusion.contains("SKIPPED"))
            return "SKIPPED";
        else if (conclusion.contains("FALSE"))
            return "FALSE";
        else if (conclusion.contains("complete") || conclusion.contains("TRUE"))
            return "TRUE";
        else
            return "FAIL";
    }

    /************************************************
     * WorkletGateway Methods
     *************************************************/
    /**
     * Register BlendedWorkflow WorkletListener.
     */
    public boolean registerWorkletListener() {
        this.client = new WorkletGatewayClient();
        try {
            this.handle = this.client.connect(engineAdminUser,
                    engineAdminPassword);
            client.addListener(workletGateway, handle);
            log.info("Register Worklet Listener: Sucess!");
            return true;
        } catch (IOException ioe) {
            log.error("Register Worklet Listener: Exception: "
                    + ioe.getMessage());
        }
        log.error("Register Worklet Listener: Failed!");
        return false;
    }

    /**
     * Add a node to a RdrTree.
     * 
     * @param yawlSpecID
     *            the YSpecificationID.
     * @param name
     *            the Task name.
     * @param condition
     *            the condition.
     * @param eCornerstone
     *            the RdrNode cornerstone data.
     * @param eConclusion
     *            the RdrNode RdrConclusion.
     * @param ruleType
     *            the RuleType.
     * @throws BWException
     */
    private void addNode(YSpecificationID yawlSpecID, String name,
            String condition, Element eCornerstone, Element eConclusion,
            RuleType ruleType) throws BWException {
        try {
            RdrNode node = new RdrNode(condition, eConclusion, eCornerstone);
            client.addNode(yawlSpecID, name, ruleType, node, handle);
        } catch (IOException e) {
            throw new BWException(BWErrorType.WORKLET_ADAPTER_ADDNODE);
        }
    }

    /**
     * Process the TaskWorkItem ItemConstrainViolation.
     * 
     * @param taskWorkItem
     *            the TaskWorkItem.
     * @throws BWException
     */
    public void process(TaskWorkItem taskWorkItem) throws BWException {
        // Evaluation Data
        Element eData = getOutputEvaluationData(taskWorkItem);

        // Get WorkItemRecord
        WorkItemRecord workItemRecord = getWorkItemRecord(taskWorkItem);

        // Process
        try {
            client.process(workItemRecord, eData,
                    RuleType.ItemConstraintViolation, handle);
        } catch (IOException e) {
            throw new BWException(BWErrorType.WORKLET_ADAPTER_PROCESS);
        }
    }

    /**
     * Evaluate a Task PreCondition.
     * 
     * @param taskWorkItem
     *            a TaskWorkItem;
     * @throws BWException
     */
    public void evaluatePreCondition(TaskWorkItem taskWorkItem)
            throws BWException {
        BWSpecification bwSpecification = taskWorkItem.getBwInstance()
                .getSpecification();
        YSpecificationID yawlSpecID = getYAWLSpecificationID(bwSpecification);

        String name = generateYAWLTaskName(taskWorkItem.getTask());
        Element eData = getInputEvaluationData(taskWorkItem);
        RuleType ruleType = RuleType.ItemPreconstraint;
        String conclusion = null;

        // Evaluate
        try {
            conclusion = client.evaluate(yawlSpecID, name, eData, ruleType,
                    handle);
        } catch (IOException e) {
            throw new BWException(
                    BWErrorType.WORKLET_ADAPTER_EVALUATEPRECONDITION);
        }

        processPreConditionEvaluationResult(parseConclusion(conclusion),
                taskWorkItem);
    }

    // /**
    // * Evaluate a Task PostCondition.
    // * @param taskWorkItem a TaskWorkItem;
    // * @throws BlendedWorkflowException
    // */
    // public void evaluatePostCondition(TaskWorkItem taskWorkItem) throws
    // BlendedWorkflowException {
    // BWSpecification bwSpecification =
    // taskWorkItem.getBwInstance().getBwSpecification();
    // YSpecificationID yawlSpecID = getYAWLSpecificationID(bwSpecification);
    //
    // String name = generateYAWLTaskName(taskWorkItem.getTask());
    // Element eData = getOutputEvaluationData(taskWorkItem);
    // RuleType ruleType = RuleType.ItemConstraintViolation;
    // String conclusion = null;
    //
    // // Evaluate
    // try {
    // conclusion = client.evaluate(yawlSpecID, name, eData, ruleType, handle);
    // } catch (IOException e) {
    // throw new
    // BlendedWorkflowException(BlendedWorkflowError.WORKLET_ADAPTER_EVALUATE);
    // }
    //
    // // Parse result
    // if (parseConclusion(conclusion).equals("TRUE")) {
    // taskWorkItem.notifyCompleted();
    // } else if (parseConclusion(conclusion).equals("FALSE")) {
    // taskWorkItem.notifyEnabled();
    // } else {
    // taskWorkItem.notifySkipped();
    // }
    // }

    /*************************************
     * YAWL Converter
     ************************************/
    /**
     * Get YAWLSpecificationID of a BWSpecification
     * 
     * @param bwSpecification
     *            the BWSpecification
     * @return yawlSpecID the YAWLSpecificationID
     * @throws BWException
     */
    private YSpecificationID getYAWLSpecificationID(
            BWSpecification bwSpecification) throws BWException {
        String yawlCaseID = bwSpecification.getYawlSpecficationID();
        YSpecificationID yawlSpecID = null;
        for (YSpecificationID ySpecificationID : BlendedWorkflow.getInstance()
                .getYawlAdapter().getLoadedActivitySpecs()) {
            if (ySpecificationID.getIdentifier().equals(yawlCaseID)) {
                yawlSpecID = ySpecificationID;
                break;
            }
        }
        return yawlSpecID;
    }

    /**
     * Get the YAWL Task name
     * 
     * @param taskWorkItem
     *            the taskWorkItem
     * @return name the task name on YAWL
     */
    private String generateYAWLTaskName(Task task) {
        return task.getName().replaceAll(" ", "_");
    }

    /**
     * Get the corresponding WorkItemRecord of the TaskWorkItem.
     * 
     * @param taskWorkItem
     *            the TaskWorkItem.
     * @return the WorkItemRecord.
     */
    private WorkItemRecord getWorkItemRecord(TaskWorkItem taskWorkItem) {
        Iterator<?> it = yawlEnabledWIR.keySet().iterator();
        while (it.hasNext()) {
            WorkItemRecord wir = (WorkItemRecord) it.next();
            TaskWorkItem tWorkItem = getTaskWorkItem(wir);
            if (tWorkItem.getID().equals(taskWorkItem.getID())) {
                return wir;
            }
        }
        return null;
    }

    /**
     * Get the corresponding TaskWorkItem of the WorkItemRecord.
     * 
     * @param wir
     *            the WorkItemRecord.
     * @return the TaskWorkItem.
     */
    private TaskWorkItem getTaskWorkItem(WorkItemRecord wir) {
        String workitemID = yawlEnabledWIR.get(wir);
        WorkItem taskWorkItem = null;
        try {
            BWInstance bwInstance = BlendedWorkflow.getInstance()
                    .getBWInstanceFromYAWLCaseID(wir.getCaseID());
            taskWorkItem = bwInstance.getWorkItem(workitemID);
        } catch (BWException bwe) {
            log.error(bwe.getError());
        }
        return (TaskWorkItem) taskWorkItem;
    }

    public void associateWorkItemRecordWithTaskWorkItem(WorkItemRecord wir,
            TaskWorkItem taskWorkItem) {
        yawlEnabledWIR.put(wir, taskWorkItem.getID());
    }

    // /*****************************************
    // * TEST
    // *****************************************/
    // public void evaluateTest(BWSpecification bwSpecification) throws
    // BlendedWorkflowException {
    // log.debug("--------------------------------------------------------------");
    // log.debug("TEST ALL NODES");
    // log.debug("--------------------------------------------------------------");
    // String name;RuleType ruleType = RuleType.ItemConstraintViolation;
    // Element eData;
    // String conclusion;
    // String cs;
    //
    // YSpecificationID yawlSpecID = getYAWLSpecificationID(bwSpecification);
    //
    // name = "Collect_Physical_Data";
    // log.info(name);
    // // UND
    // cs = "<cornerstone>"+
    // "<PatientData_Height_State>UNDEFINED</PatientData_Height_State>"+
    // "<PatientData_Height>$UNDEFINED$</PatientData_Height>"+
    // "<PatientData_Weight_State>UNDEFINED</PatientData_Weight_State>"+
    // "<PatientData_Weight>$UNDEFINED$</PatientData_Weight>"+
    // "<FALSE_NODE>FALSE</FALSE_NODE>"+
    // "</cornerstone>";
    // eData = JDOMUtil.stringToElement(cs);
    //
    // try {
    // conclusion = client.evaluate(yawlSpecID, name, eData, ruleType, handle);
    // } catch (IOException e) {
    // throw new
    // BlendedWorkflowException(BlendedWorkflowError.WORKLET_ADAPTER_EVALUATE);
    // }
    // log.info("UND=" + conclusion);
    //
    // // DEF
    // cs = "<cornerstone>"+
    // "<PatientData_Height_State>DEFINED</PatientData_Height_State>"+
    // "<PatientData_Height>$UNDEFINED$</PatientData_Height>"+
    // "<PatientData_Weight_State>DEFINED</PatientData_Weight_State>"+
    // "<PatientData_Weight>$UNDEFINED$</PatientData_Weight>"+
    // "<FALSE_NODE>FALSE</FALSE_NODE>"+
    // "</cornerstone>";
    // eData = JDOMUtil.stringToElement(cs);
    //
    // try {
    // conclusion = client.evaluate(yawlSpecID, name, eData, ruleType, handle);
    // } catch (IOException e) {
    // throw new
    // BlendedWorkflowException(BlendedWorkflowError.WORKLET_ADAPTER_EVALUATE);
    // }
    // log.info("TRUE=" + conclusion);
    //
    // // SKIP
    // cs = "<cornerstone>"+
    // "<PatientData_Height_State>SKIPPED</PatientData_Height_State>"+
    // "<PatientData_Height>$SKIPPED$</PatientData_Height>"+
    // "<PatientData_Weight_State>SKIPPED</PatientData_Weight_State>"+
    // "<PatientData_Weight>$SKIPPED$</PatientData_Weight>"+
    // "<FALSE_NODE>FALSE</FALSE_NODE>"+
    // "</cornerstone>";
    // eData = JDOMUtil.stringToElement(cs);
    //
    // try {
    // conclusion = client.evaluate(yawlSpecID, name, eData, ruleType, handle);
    // } catch (IOException e) {
    // throw new
    // BlendedWorkflowException(BlendedWorkflowError.WORKLET_ADAPTER_EVALUATE);
    // }
    // log.info("SKIP=" + conclusion);
    // }

}
