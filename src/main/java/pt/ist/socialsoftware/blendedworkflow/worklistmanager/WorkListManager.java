package pt.ist.socialsoftware.blendedworkflow.worklistmanager;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vaadin.ui.Window.Notification;

import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CheckInWorkItemService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateGoalInstanceService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.CreateNewGoalService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.DisableGoalConditionService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.EnableGoalWorkItemsService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.RedoGoalWorkItemService;
import pt.ist.socialsoftware.blendedworkflow.engines.bwengine.servicelayer.SkipWorkItemService;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWInstance;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BWSpecification;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.GoalWorkItem;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.WorkItem;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.WorkItem.WorkItemState;
import pt.ist.socialsoftware.blendedworkflow.engines.exception.BlendedWorkflowException.BlendedWorkflowError;
import pt.ist.socialsoftware.blendedworkflow.presentation.BWPresentation;
import pt.ist.socialsoftware.blendedworkflow.shared.BWExecutorService;

public class WorkListManager {

	private Logger log = Logger.getLogger("WorklistManager");
	protected BWPresentation bwPresentation = null;

	public BWPresentation getBwPresentation() {
		return bwPresentation;
	}

	public void setBwPresentation(BWPresentation bwPresentation) {
		this.bwPresentation = bwPresentation;
	}

	/**
	 * Notify the BWPresentation of enabled WorkItems.
	 * @param workItem The enabled WorkItem (i.e. State = ENABLED or PRE_TASK).
	 */
	public void notifyEnabledWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now enabled. with OID" + workItem.getOID());
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().addGoalWorkItem(workItem.getOID(), workItem.getID());
		}
		else {
			getBwPresentation().addTaskWorkItem(workItem.getOID(), workItem.getID());
		}
	}

	/**
	 * Notify the BWPresentation of skipped WorkItems.
	 * @param workItem The skipped WorkItem (i.e. State = SKIPPED).
	 */
	public void notifySkippedWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now skipped.");
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().removeGoalWorkItem(workItem.getOID());
		}
		else {
			getBwPresentation().removeTaskWorkItem(workItem.getOID());
		}
	}

	/**
	 * Notify the BWPresentation of pending WorkItems.
	 * @param workItem The pending WorkItem (i.e. State = PENDING).
	 */
	public void notifyPendingWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now pending.");
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().removeGoalWorkItem(workItem.getOID());
		}
		else {
			getBwPresentation().removeTaskWorkItem(workItem.getOID());
		}
	}

	/**
	 * Notify the BWPresentation of completed WorkItems.
	 * @param workItem The pending WorkItem (i.e. State = COMPLETED).
	 */
	public void notifyCompletedWorkItem(WorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now completed.");
		if (workItem.getClass().equals(GoalWorkItem.class)) {
			getBwPresentation().removeGoalWorkItem(workItem.getOID());
		}
		else {
			getBwPresentation().removeTaskWorkItem(workItem.getOID());
		}
	}

	/**
	 * Update the BWPresentation with all the active WorkItems.
	 */
	public void updateBWPresentation() {
		for (BWSpecification bwSpecification : BlendedWorkflow.getInstance().getBwSpecifications()) {
			for (BWInstance bwInstance : bwSpecification.getBwInstances()) {
				for (WorkItem workItem : bwInstance.getWorkItems()) {
					if (workItem.getState().equals(WorkItemState.ENABLED) || workItem.getState().equals(WorkItemState.PRE_TASK) || workItem.getState().equals(WorkItemState.PRE_GOAL))
						notifyEnabledWorkItem(workItem);
				}
			}
		}
	}
	
	public void checkInWorkItem(long workItemOID){
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		CheckInWorkItemService service = new CheckInWorkItemService(workItemOID);
		bwExecutorService.runTask(service);
	}
	
	public void skipWorkItem(long workItemOID){
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		SkipWorkItemService service = new SkipWorkItemService(workItemOID);
		bwExecutorService.runTask(service);
	}
	
	public void createGoal(long bwInstanceOID, String goalName, String goalDescription, long parentGoalID, String goalCondition, ArrayList<String> activateConditions, long entityOID, String userID){
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		CreateNewGoalService service = new CreateNewGoalService(bwInstanceOID, goalName, goalDescription, parentGoalID, goalCondition, activateConditions, entityOID, userID);
		bwExecutorService.runTask(service);
	}
	
	public void notifyException(BlendedWorkflowError bwe) {
		getBwPresentation().getMainWindow().showNotification(bwe.toString(), Notification.TYPE_ERROR_MESSAGE);
	}

	public void notifyReEnabledWorkItem(GoalWorkItem workItem) {
		log.info("WorkItem " + workItem.getID() + " is now re-enabled. with OID" + workItem.getOID());
		getBwPresentation().addGoalWorkItem(workItem.getOID(), workItem.getID() + "(ReEnabled)");
	}

	public void createGoalInstance(long bwInstanceOID, long parentGoalID, Long context) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		CreateGoalInstanceService service = new CreateGoalInstanceService(bwInstanceOID, parentGoalID, context);
		bwExecutorService.runTask(service);
	}

	public void redoGoal(long bwInstanceOID, long workItemOID, String userID) {
		log.info("Redo GoalWorkItem: " + workItemOID);
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		RedoGoalWorkItemService service = new RedoGoalWorkItemService(bwInstanceOID, workItemOID, userID);
		bwExecutorService.runTask(service);
	}
	
	public void disableWorkItemCondition(long workitemOID, long conditionOID) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		DisableGoalConditionService service = new DisableGoalConditionService(workitemOID, conditionOID);
		bwExecutorService.runTask(service);
	}

	public void enableGoalWorkItemsService(long bwInstanceOID) {
		BWExecutorService bwExecutorService = BlendedWorkflow.getInstance().getBWExecutorService();
		EnableGoalWorkItemsService service = new EnableGoalWorkItemsService(bwInstanceOID);
		bwExecutorService.runTask(service);
	}

}