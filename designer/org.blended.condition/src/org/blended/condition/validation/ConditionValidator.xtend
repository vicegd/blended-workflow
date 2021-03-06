/*
 * generated by Xtext
 */
package org.blended.condition.validation

import org.eclipse.xtext.validation.Check
import pt.ist.socialsoftware.blendedworkflow.service.BWException
import pt.ist.socialsoftware.blendedworkflow.service.design.DesignInterface
import static extension org.eclipse.xtext.EcoreUtil2.*
import org.blended.condition.condition.ConditionModel
import org.blended.condition.condition.ConditionPackage

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class ConditionValidator extends AbstractConditionValidator {

  		@Check
	  	def checkModel(ConditionModel model) {
	  	  	var instance = DesignInterface.getInstance
	  		try {
				var fileName = model.eResource.normalizedURI.lastSegment.split("\\.").get(0)
//	  			instance.loadConditionModel(model, fileName)
	  		} catch (BWException bwe) {
	  			//error('Specification with the same name already exists', ConditionPackage.Literals.SPECIFICATION__NAME, INVALID_NAME)
	  		}

	  		//if (entity.uid == null) {
	  		//	entity.uid = entity.hashCode().toString
	  		//	System.out.println("UUID for entity " + entity.name + ": " + entity.uid)
	  		//}
	  		//else System.out.println("UUID for entity " + entity.name + "is already assigned with value: " + entity.uid)
	  		//System.out.println("UUID for entity " + entity.name + ": " + entity.hashCode) 		
  	}
}
