/*
 * generated by Xtext
 */
package org.blended.condition.ui.labeling

import com.google.inject.Inject
import org.blended.common.common.And
import org.blended.common.common.AttributeAchieveCondition
import org.blended.common.common.AttributeDependenceCondition
import org.blended.common.common.AttributeInvariantCondition
import org.blended.common.common.EntityAchieveCondition
import org.blended.common.common.EntityDependenceCondition
import org.blended.common.common.EntityInvariantCondition
import org.blended.common.common.Not
import org.blended.common.common.Or
import org.blended.condition.condition.ConditionModel
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class ConditionLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:

	def image(ConditionModel cm) {
		'condition.gif'
	}
	
	def text(ConditionModel cm) {
		'Conditions'
	}
	
	/*def image(Condition c) {
		'condition.gif'
	}
	
	def text(Condition c) {
		'For Entities and Attributes'
	}*/
	
	//********ENTITIES
	def image(EntityAchieveCondition eac) {
		'LetterABlue.png'
	}
	
	def text(EntityAchieveCondition eac) {
		'Achieve: [' + eac.name + ']'
	}
	
	def image(EntityInvariantCondition eic) {
		'LetterIBlue.png'
	}
	
	def text(EntityInvariantCondition eic) {
		'Invariant for: [' + eic.name + ']'
	}
	
	def image(EntityDependenceCondition edc) {
		'LetterDBlue.png'
	}
	
	def text(EntityDependenceCondition edc) {
		'Dependence on: [' + edc.entity2 + ']'
	}
	
	//********ATTRIBUTES
	
	def image(AttributeAchieveCondition aac) {
		'LetterAGreen.png'
	}
	
	def text(AttributeAchieveCondition aac) {
		'Achieve: ' + aac.conditions
	}
	
	def image(AttributeInvariantCondition aic) {
		'LetterIGreen.png'
	}
	
	def text(AttributeInvariantCondition aic) {
		'Invariant for attribute'
	}
	
	def text(And and) {
		'AND'
	}
	
	def text(Or or) {
		'OR'
	}
	
	def text(Not not) {
		'NOT'
	}
	
	def image(AttributeDependenceCondition adc) {
		'LetterDGreen.png'
	}
	
	def text(AttributeDependenceCondition edc) {
		'Dependence on: [' + edc.attributes2.join(',') + ']'
	}
}
