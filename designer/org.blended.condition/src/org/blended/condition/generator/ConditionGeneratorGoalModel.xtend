package org.blended.condition.generator

import com.google.inject.Guice
import org.blended.common.common.AttributeAchieveCondition
import org.blended.common.common.CommonFactory
import org.blended.common.common.EntityAchieveCondition
import org.blended.common.common.Specification
import org.blended.goal.GoalRuntimeModule
import org.blended.goal.goal.GoalFactory
import org.blended.goal.goal.GoalModel
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.resource.SaveOptions

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*
import org.blended.common.common.MandatoryAttributeAchieveCondition
import org.eclipse.emf.ecore.EObject
import org.blended.goal.goal.Goal
import org.blended.common.common.NotMandatoryAttributeAchieveCondition
import org.blended.common.common.EntityDependenceCondition
import org.blended.common.common.AttributeDependenceCondition
import org.blended.common.utils.Queries
import org.blended.common.common.EntityInvariantCondition
import org.blended.common.common.AttributeInvariantCondition
import java.util.ArrayList

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
class ConditionGeneratorGoalModel {
	Resource resource
	IFileSystemAccess fsa
	CommonFactory factory
	GoalFactory goalFactory
	GoalModel model

	new(Resource resource, IFileSystemAccess fsa) {
		this.resource = resource
		this.fsa = fsa
		this.factory = CommonFactory.eINSTANCE
		this.goalFactory = GoalFactory.eINSTANCE
		this.model = goalFactory.createGoalModel
	}

	def doGenerate() {
		model.specification = resource.allContents.toIterable.filter(typeof(Specification)).get(0).copy 

		//first goal (g0)
		var goal0 = goalFactory.createGoal
		goal0.name = 'g0'
		var nothing = factory.createNothing
		nothing.name = 'nothing'
		goal0.successConditions.add(nothing)
		model.goals.add(goal0)

		var i = 1 // number of the goal
		for (o : resource.allContents.toIterable.filter(typeof(EntityAchieveCondition))) {
			var goal = goalFactory.createGoal
			goal.name = 'g' + i++
			model.goals.add(goal)
			
			//SUC			
			goal.successConditions.add(o.copy)
			
			//SUB
			goal0.childrenGoals.add(goal) 

			//ACT
			for (dep : resource.allContents.toIterable.filter(typeof(EntityDependenceCondition))) {
				step2(goal, o.name, dep)
			}
			
			//INV
			for (inv : resource.allContents.toIterable.filter(typeof(EntityInvariantCondition))) {
				step3(goal, o.name, inv)
			}
		}
		
		for (o : resource.allContents.toIterable.filter(typeof(AttributeAchieveCondition))) {
			var goal = goalFactory.createGoal
			goal.name = 'g' + i++
			model.goals.add(goal)
			
			//SUC
			if(o instanceof MandatoryAttributeAchieveCondition) {
				var nma = factory.createNotMandatoryAttributeAchieveCondition
				nma.conditions.addAll(o.conditions)	
				goal.successConditions.add(nma)		
			}
			else {
				goal.successConditions.add(o.copy)
			}
			
			//SUB
			step1(goal, o)
			
			//ACT
			for (dep : resource.allContents.toIterable.filter(typeof(AttributeDependenceCondition))) {
				step2(goal, o, dep)
			}
			
			//INV 
			for (inv : resource.allContents.toIterable.filter(typeof(AttributeInvariantCondition))) {
				step3(goal, o, inv)
			}
		}
		
		// TO SERIALIZE THE ACTIVITY MODEL ACCORDING TO THE GOAL FORMATTER
		val injector = Guice.createInjector(new GoalRuntimeModule)
		var rs = injector.getInstance(ResourceSet)
		var r = rs.createResource(URI.createURI(resource.normalizedURI.toString.replace(".cm", ".gm")))
		r.contents.add(model)
		var builder = SaveOptions.newBuilder()
		builder.noValidation
		builder.format
		r.save(builder.options.toOptionsMap)
	}
	
	def step1(Goal goal, EObject o) {
		if (o instanceof NotMandatoryAttributeAchieveCondition) {
			for (String c : o.conditions) {
				var entity = Queries.getEntityNameFrom(c)
				var entityGoal = getEntityGoalFromName(entity)
				entityGoal.childrenGoals.add(goal)
			}
		} 
		else if (o instanceof MandatoryAttributeAchieveCondition) {
			for (String c : o.conditions) {
				var entity = Queries.getEntityNameFrom(c)
				var entityGoal = getEntityGoalFromName(entity)
				entityGoal.childrenGoals.add(goal)
			}
		}
	}
	
	def getEntityGoalFromName(String entity) {
		for (Goal g : model.goals) {
			for (suc : g.successConditions) {	
				if (suc instanceof EntityAchieveCondition)
					if (suc.name.equals(entity)) return g
			} 
		}
	}
	
	def step2(Goal goal, String name, EntityDependenceCondition dep) {
		if (name.equals(dep.entity1)) {
			var eac = factory.createEntityAchieveCondition
			eac.name = dep.entity2
			goal.activationConditions.add(eac)
		}
	}
	
	def step2(Goal goal, EObject o, AttributeDependenceCondition dep) {
		if (o instanceof NotMandatoryAttributeAchieveCondition) {
			var done = false
			for (String c : o.conditions) {
				if (!done) {
					if (dep.attributes1.contains(c)) {
						var aac = factory.createNotMandatoryAttributeAchieveCondition
						aac.conditions.addAll(dep.attributes2)
						goal.activationConditions.add(aac)
						done = true
					}		
				}
			}			
		}
		else if (o instanceof MandatoryAttributeAchieveCondition) {
			var done = false
			for (String c : o.conditions) {
				if (!done) {
					if (dep.attributes1.contains(c)) {
						var aac = factory.createNotMandatoryAttributeAchieveCondition
						aac.conditions.addAll(dep.attributes2)
						goal.activationConditions.add(aac)
						done = true
					}		
				}
			}			
		}
	}
	
	def step3(Goal goal, String name, EntityInvariantCondition inv) {
		if (name.equals(Queries.getEntityNameFrom(inv.name))) {
			goal.invariantConditions.add(inv.copy)
		}
	}
	
	def step3(Goal goal, EObject o, AttributeInvariantCondition inv) {
		var list = new ArrayList<String>()
		Queries.getDecomposedExpression(inv.expression, list)
		if (o instanceof NotMandatoryAttributeAchieveCondition) {
			var done = false
			for (String c : o.conditions) {
				if (!done) {
					if (list.contains(c)) {
						goal.invariantConditions.add(inv.copy)
						done = true
					}		
				}
			}
		}
		else if (o instanceof MandatoryAttributeAchieveCondition) {	
			var done = false
			for (String c : o.conditions) {
				if (!done) {
					if (list.contains(c)) {
						goal.invariantConditions.add(inv.copy)
						done = true
					}		
				}
			}	
		}		
	}
	
}

