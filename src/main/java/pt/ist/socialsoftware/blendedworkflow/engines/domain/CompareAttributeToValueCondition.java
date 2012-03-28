package pt.ist.socialsoftware.blendedworkflow.engines.domain;

public class CompareAttributeToValueCondition extends CompareAttributeToValueCondition_Base {

	public CompareAttributeToValueCondition(){
		super();
	}

	public CompareAttributeToValueCondition(Attribute attribute, String operator, String value) {
		setAttribute(attribute);
		setOperator(operator);
		setValue(value);
	}

	@Override
	Condition cloneCondition(GoalModelInstance goalModelInstance) {
		DataModelInstance dataModelInstance = goalModelInstance.getBwInstance().getDataModelInstance();
		Entity entity = dataModelInstance.getEntity(getAttribute().getEntity().getName());
		Attribute attribute = entity.getAttribute(getAttribute().getName());
		return new CompareAttributeToValueCondition(attribute, getOperator(), getValue());
	}

	@Override
	Condition cloneCondition(TaskModelInstance taskModelInstance) {
		DataModelInstance dataModelInstance = taskModelInstance.getBwInstance().getDataModelInstance();
		Entity entity = dataModelInstance.getEntity(getAttribute().getEntity().getName());
		Attribute attribute = entity.getAttribute(getAttribute().getName());
		return new CompareAttributeToValueCondition(attribute, getOperator(), getValue());
	}
	
	@Override
	public void assignAttributeInstances(GoalWorkItem goalWorkItem) {
		getAttribute().getEntity().assignAttributeInstances(goalWorkItem,getAttribute());
	}
	
	@Override
	public void assignAttributeInstances(TaskWorkItem taskWorkItem, String conditionType) {
		getAttribute().getEntity().assignAttributeInstances(taskWorkItem,getAttribute(), conditionType);
	}
	
	@Override
	String getData() {
		return getAttribute().getEntity().getName();
	}
}