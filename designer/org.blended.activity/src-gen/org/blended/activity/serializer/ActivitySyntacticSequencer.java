/*
 * generated by Xtext
 */
package org.blended.activity.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.blended.activity.services.ActivityGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class ActivitySyntacticSequencer extends AbstractSyntacticSequencer {

	protected ActivityGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Condition_ATTRIBUTE_ACHIEVE_CONDITIONSKeyword_4_0_q;
	protected AbstractElementAlias match_Condition_ATTRIBUTE_DEPENDENCE_CONDITIONSKeyword_6_0_q;
	protected AbstractElementAlias match_Condition_ATTRIBUTE_INVARIANT_CONDITIONSKeyword_5_0_q;
	protected AbstractElementAlias match_Condition_ENTITY_ACHIEVE_CONDITIONSKeyword_1_0_q;
	protected AbstractElementAlias match_Condition_ENTITY_DEPENDENCE_CONDITIONSKeyword_3_0_q;
	protected AbstractElementAlias match_Condition_ENTITY_INVARIANT_CONDITIONSKeyword_2_0_q;
	protected AbstractElementAlias match_Primary_LeftParenthesisKeyword_0_0_a;
	protected AbstractElementAlias match_Primary_LeftParenthesisKeyword_0_0_p;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (ActivityGrammarAccess) access;
		match_Condition_ATTRIBUTE_ACHIEVE_CONDITIONSKeyword_4_0_q = new TokenAlias(false, true, grammarAccess.getConditionAccess().getATTRIBUTE_ACHIEVE_CONDITIONSKeyword_4_0());
		match_Condition_ATTRIBUTE_DEPENDENCE_CONDITIONSKeyword_6_0_q = new TokenAlias(false, true, grammarAccess.getConditionAccess().getATTRIBUTE_DEPENDENCE_CONDITIONSKeyword_6_0());
		match_Condition_ATTRIBUTE_INVARIANT_CONDITIONSKeyword_5_0_q = new TokenAlias(false, true, grammarAccess.getConditionAccess().getATTRIBUTE_INVARIANT_CONDITIONSKeyword_5_0());
		match_Condition_ENTITY_ACHIEVE_CONDITIONSKeyword_1_0_q = new TokenAlias(false, true, grammarAccess.getConditionAccess().getENTITY_ACHIEVE_CONDITIONSKeyword_1_0());
		match_Condition_ENTITY_DEPENDENCE_CONDITIONSKeyword_3_0_q = new TokenAlias(false, true, grammarAccess.getConditionAccess().getENTITY_DEPENDENCE_CONDITIONSKeyword_3_0());
		match_Condition_ENTITY_INVARIANT_CONDITIONSKeyword_2_0_q = new TokenAlias(false, true, grammarAccess.getConditionAccess().getENTITY_INVARIANT_CONDITIONSKeyword_2_0());
		match_Primary_LeftParenthesisKeyword_0_0_a = new TokenAlias(true, true, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
		match_Primary_LeftParenthesisKeyword_0_0_p = new TokenAlias(true, false, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_0_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_Condition_ATTRIBUTE_ACHIEVE_CONDITIONSKeyword_4_0_q.equals(syntax))
				emit_Condition_ATTRIBUTE_ACHIEVE_CONDITIONSKeyword_4_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Condition_ATTRIBUTE_DEPENDENCE_CONDITIONSKeyword_6_0_q.equals(syntax))
				emit_Condition_ATTRIBUTE_DEPENDENCE_CONDITIONSKeyword_6_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Condition_ATTRIBUTE_INVARIANT_CONDITIONSKeyword_5_0_q.equals(syntax))
				emit_Condition_ATTRIBUTE_INVARIANT_CONDITIONSKeyword_5_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Condition_ENTITY_ACHIEVE_CONDITIONSKeyword_1_0_q.equals(syntax))
				emit_Condition_ENTITY_ACHIEVE_CONDITIONSKeyword_1_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Condition_ENTITY_DEPENDENCE_CONDITIONSKeyword_3_0_q.equals(syntax))
				emit_Condition_ENTITY_DEPENDENCE_CONDITIONSKeyword_3_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Condition_ENTITY_INVARIANT_CONDITIONSKeyword_2_0_q.equals(syntax))
				emit_Condition_ENTITY_INVARIANT_CONDITIONSKeyword_2_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_LeftParenthesisKeyword_0_0_a.equals(syntax))
				emit_Primary_LeftParenthesisKeyword_0_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Primary_LeftParenthesisKeyword_0_0_p.equals(syntax))
				emit_Primary_LeftParenthesisKeyword_0_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     'ATTRIBUTE_ACHIEVE_CONDITIONS'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS'? 
	 *         (rule end)
	 *     )
	 *     (
	 *         entityInvariantConditions+=EntityInvariantCondition 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule start)
	 *     entityAchieveConditions+=EntityAchieveCondition 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityDependenceConditions+=EntityDependenceCondition (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS' attributeInvariantConditions+=AttributeInvariantCondition
	 *     entityDependenceConditions+=EntityDependenceCondition (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS' attributeDependenceConditions+=AttributeDependenceCondition
	 *     entityDependenceConditions+=EntityDependenceCondition (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityInvariantConditions+=EntityInvariantCondition 'ENTITY_DEPENDENCE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS' attributeInvariantConditions+=AttributeInvariantCondition
	 *     entityInvariantConditions+=EntityInvariantCondition 'ENTITY_DEPENDENCE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 */
	protected void emit_Condition_ATTRIBUTE_ACHIEVE_CONDITIONSKeyword_4_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'ATTRIBUTE_DEPENDENCE_CONDITIONS'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? (ambiguity) (rule start)
	 *     attributeAchieveConditions+=AttributeAchieveCondition 'ATTRIBUTE_INVARIANT_CONDITIONS'? (ambiguity) (rule end)
	 *     attributeInvariantConditions+=AttributeInvariantCondition (ambiguity) (rule end)
	 *     entityAchieveConditions+=EntityAchieveCondition 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? (ambiguity) (rule end)
	 *     entityAchieveConditions+=EntityAchieveConditionExist 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? (ambiguity) (rule end)
	 *     entityDependenceConditions+=EntityDependenceCondition 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? (ambiguity) (rule end)
	 *     entityInvariantConditions+=EntityInvariantCondition 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? (ambiguity) (rule end)
	 */
	protected void emit_Condition_ATTRIBUTE_DEPENDENCE_CONDITIONSKeyword_6_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'ATTRIBUTE_INVARIANT_CONDITIONS'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityInvariantConditions+=EntityInvariantCondition 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule start)
	 *     attributeAchieveConditions+=AttributeAchieveCondition (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS' attributeDependenceConditions+=AttributeDependenceCondition
	 *     attributeAchieveConditions+=AttributeAchieveCondition (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityAchieveConditions+=EntityAchieveCondition 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityAchieveConditions+=EntityAchieveConditionExist 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityDependenceConditions+=EntityDependenceCondition 'ATTRIBUTE_ACHIEVE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS' attributeDependenceConditions+=AttributeDependenceCondition
	 *     entityDependenceConditions+=EntityDependenceCondition 'ATTRIBUTE_ACHIEVE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityInvariantConditions+=EntityInvariantCondition 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? (ambiguity) 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 */
	protected void emit_Condition_ATTRIBUTE_INVARIANT_CONDITIONSKeyword_5_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'ENTITY_ACHIEVE_CONDITIONS'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (
	 *         (rule start) 
	 *         (ambiguity) 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         (rule start) 
	 *         (ambiguity) 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (rule start) (ambiguity) 'ENTITY_INVARIANT_CONDITIONS' entityInvariantConditions+=EntityInvariantCondition
	 *     (rule start) (ambiguity) 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS' entityDependenceConditions+=EntityDependenceCondition
	 *     (rule start) (ambiguity) 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     (rule start) (ambiguity) 'ENTITY_INVARIANT_CONDITIONS'? 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule start)
	 */
	protected void emit_Condition_ENTITY_ACHIEVE_CONDITIONSKeyword_1_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'ENTITY_DEPENDENCE_CONDITIONS'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         'ENTITY_INVARIANT_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS'? 
	 *         (rule end)
	 *     )
	 *     (
	 *         entityInvariantConditions+=EntityInvariantCondition 
	 *         (ambiguity) 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? 'ENTITY_INVARIANT_CONDITIONS'? (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? 'ENTITY_INVARIANT_CONDITIONS'? (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule start)
	 *     entityAchieveConditions+=EntityAchieveCondition 'ENTITY_INVARIANT_CONDITIONS'? (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     entityAchieveConditions+=EntityAchieveCondition 'ENTITY_INVARIANT_CONDITIONS'? (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityAchieveConditions+=EntityAchieveConditionExist 'ENTITY_INVARIANT_CONDITIONS'? (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     entityInvariantConditions+=EntityInvariantCondition (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     entityInvariantConditions+=EntityInvariantCondition (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS' attributeInvariantConditions+=AttributeInvariantCondition
	 *     entityInvariantConditions+=EntityInvariantCondition (ambiguity) 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 */
	protected void emit_Condition_ENTITY_DEPENDENCE_CONDITIONSKeyword_3_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     'ENTITY_INVARIANT_CONDITIONS'?
	 *
	 * This ambiguous syntax occurs at:
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         (rule start) 
	 *         'ENTITY_ACHIEVE_CONDITIONS'? 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveCondition 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS' 
	 *         attributeInvariantConditions+=AttributeInvariantCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS' 
	 *         attributeDependenceConditions+=AttributeDependenceCondition
	 *     )
	 *     (
	 *         entityAchieveConditions+=EntityAchieveConditionExist 
	 *         (ambiguity) 
	 *         'ENTITY_DEPENDENCE_CONDITIONS'? 
	 *         'ATTRIBUTE_ACHIEVE_CONDITIONS'? 
	 *         'ATTRIBUTE_INVARIANT_CONDITIONS'? 
	 *         'ATTRIBUTE_DEPENDENCE_CONDITIONS'? 
	 *         (rule end)
	 *     )
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS' entityDependenceConditions+=EntityDependenceCondition
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     (rule start) 'ENTITY_ACHIEVE_CONDITIONS'? (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule start)
	 *     entityAchieveConditions+=EntityAchieveCondition (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS' entityDependenceConditions+=EntityDependenceCondition
	 *     entityAchieveConditions+=EntityAchieveCondition (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 *     entityAchieveConditions+=EntityAchieveCondition (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS'? 'ATTRIBUTE_INVARIANT_CONDITIONS'? 'ATTRIBUTE_DEPENDENCE_CONDITIONS'? (rule end)
	 *     entityAchieveConditions+=EntityAchieveConditionExist (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS' entityDependenceConditions+=EntityDependenceCondition
	 *     entityAchieveConditions+=EntityAchieveConditionExist (ambiguity) 'ENTITY_DEPENDENCE_CONDITIONS'? 'ATTRIBUTE_ACHIEVE_CONDITIONS' attributeAchieveConditions+=AttributeAchieveCondition
	 */
	protected void emit_Condition_ENTITY_INVARIANT_CONDITIONSKeyword_2_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) 'DEF' '(' name=Attribute
	 *     (rule start) (ambiguity) 'NOT' expression=Primary
	 *     (rule start) (ambiguity) name=Attribute
	 *     (rule start) (ambiguity) {And.left=}
	 *     (rule start) (ambiguity) {Or.left=}
	 */
	protected void emit_Primary_LeftParenthesisKeyword_0_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) {And.left=}
	 *     (rule start) (ambiguity) {Or.left=}
	 */
	protected void emit_Primary_LeftParenthesisKeyword_0_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
