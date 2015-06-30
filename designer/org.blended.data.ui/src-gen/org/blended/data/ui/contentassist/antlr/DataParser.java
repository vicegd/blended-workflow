/*
 * generated by Xtext
 */
package org.blended.data.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.blended.data.services.DataGrammarAccess;

public class DataParser extends AbstractContentAssistParser {
	
	@Inject
	private DataGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.blended.data.ui.contentassist.antlr.internal.InternalDataParser createParser() {
		org.blended.data.ui.contentassist.antlr.internal.InternalDataParser result = new org.blended.data.ui.contentassist.antlr.internal.InternalDataParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getEntityAccess().getAttributesAlternatives_5_0(), "rule__Entity__AttributesAlternatives_5_0");
					put(grammarAccess.getCardinalityAccess().getAlternatives(), "rule__Cardinality__Alternatives");
					put(grammarAccess.getPrimaryAccess().getAlternatives(), "rule__Primary__Alternatives");
					put(grammarAccess.getAtomicAccess().getAlternatives(), "rule__Atomic__Alternatives");
					put(grammarAccess.getDataModelAccess().getGroup(), "rule__DataModel__Group__0");
					put(grammarAccess.getEntityAccess().getGroup(), "rule__Entity__Group__0");
					put(grammarAccess.getEntityAccess().getGroup_3(), "rule__Entity__Group_3__0");
					put(grammarAccess.getAttributeAccess().getGroup(), "rule__Attribute__Group__0");
					put(grammarAccess.getAttributeAccess().getGroup_4(), "rule__Attribute__Group_4__0");
					put(grammarAccess.getAttributeAccess().getGroup_4_2(), "rule__Attribute__Group_4_2__0");
					put(grammarAccess.getAttributeGroupAccess().getGroup(), "rule__AttributeGroup__Group__0");
					put(grammarAccess.getAttributeGroupAccess().getGroup_2(), "rule__AttributeGroup__Group_2__0");
					put(grammarAccess.getAttributeGroupAccess().getGroup_2_2(), "rule__AttributeGroup__Group_2_2__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
					put(grammarAccess.getQualifiedNameAccess().getGroup_1(), "rule__QualifiedName__Group_1__0");
					put(grammarAccess.getAssociationAccess().getGroup(), "rule__Association__Group__0");
					put(grammarAccess.getConstraintAccess().getGroup(), "rule__Constraint__Group__0");
					put(grammarAccess.getCardinalityAccess().getGroup_1(), "rule__Cardinality__Group_1__0");
					put(grammarAccess.getCardinalityAccess().getGroup_2(), "rule__Cardinality__Group_2__0");
					put(grammarAccess.getCardinalityAccess().getGroup_3(), "rule__Cardinality__Group_3__0");
					put(grammarAccess.getOrAccess().getGroup(), "rule__Or__Group__0");
					put(grammarAccess.getOrAccess().getGroup_1(), "rule__Or__Group_1__0");
					put(grammarAccess.getAndAccess().getGroup(), "rule__And__Group__0");
					put(grammarAccess.getAndAccess().getGroup_1(), "rule__And__Group_1__0");
					put(grammarAccess.getPrimaryAccess().getGroup_0(), "rule__Primary__Group_0__0");
					put(grammarAccess.getPrimaryAccess().getGroup_1(), "rule__Primary__Group_1__0");
					put(grammarAccess.getAtomicAccess().getGroup_0(), "rule__Atomic__Group_0__0");
					put(grammarAccess.getAtomicAccess().getGroup_1(), "rule__Atomic__Group_1__0");
					put(grammarAccess.getDataModelAccess().getEntitiesAssignment_0(), "rule__DataModel__EntitiesAssignment_0");
					put(grammarAccess.getDataModelAccess().getAssociationsAssignment_1(), "rule__DataModel__AssociationsAssignment_1");
					put(grammarAccess.getDataModelAccess().getConstraintAssignment_2(), "rule__DataModel__ConstraintAssignment_2");
					put(grammarAccess.getEntityAccess().getNameAssignment_1(), "rule__Entity__NameAssignment_1");
					put(grammarAccess.getEntityAccess().getExistsAssignment_2(), "rule__Entity__ExistsAssignment_2");
					put(grammarAccess.getEntityAccess().getDependsOnAssignment_3_1(), "rule__Entity__DependsOnAssignment_3_1");
					put(grammarAccess.getEntityAccess().getAttributesAssignment_5(), "rule__Entity__AttributesAssignment_5");
					put(grammarAccess.getAttributeAccess().getNameAssignment_0(), "rule__Attribute__NameAssignment_0");
					put(grammarAccess.getAttributeAccess().getTypeAssignment_2(), "rule__Attribute__TypeAssignment_2");
					put(grammarAccess.getAttributeAccess().getMandatoryAssignment_3(), "rule__Attribute__MandatoryAssignment_3");
					put(grammarAccess.getAttributeAccess().getDependsOnAssignment_4_1(), "rule__Attribute__DependsOnAssignment_4_1");
					put(grammarAccess.getAttributeAccess().getDependsOnAssignment_4_2_1(), "rule__Attribute__DependsOnAssignment_4_2_1");
					put(grammarAccess.getAttributeGroupAccess().getMandatoryAssignment_1(), "rule__AttributeGroup__MandatoryAssignment_1");
					put(grammarAccess.getAttributeGroupAccess().getDependsOnAssignment_2_1(), "rule__AttributeGroup__DependsOnAssignment_2_1");
					put(grammarAccess.getAttributeGroupAccess().getDependsOnAssignment_2_2_1(), "rule__AttributeGroup__DependsOnAssignment_2_2_1");
					put(grammarAccess.getAttributeGroupAccess().getAttributesAssignment_4(), "rule__AttributeGroup__AttributesAssignment_4");
					put(grammarAccess.getAssociationAccess().getEntity1Assignment_2(), "rule__Association__Entity1Assignment_2");
					put(grammarAccess.getAssociationAccess().getName1Assignment_4(), "rule__Association__Name1Assignment_4");
					put(grammarAccess.getAssociationAccess().getCardinality1Assignment_6(), "rule__Association__Cardinality1Assignment_6");
					put(grammarAccess.getAssociationAccess().getEntity2Assignment_8(), "rule__Association__Entity2Assignment_8");
					put(grammarAccess.getAssociationAccess().getName2Assignment_10(), "rule__Association__Name2Assignment_10");
					put(grammarAccess.getAssociationAccess().getCardinality2Assignment_12(), "rule__Association__Cardinality2Assignment_12");
					put(grammarAccess.getConstraintAccess().getConstraintAssignment_2(), "rule__Constraint__ConstraintAssignment_2");
					put(grammarAccess.getOrAccess().getRightAssignment_1_2(), "rule__Or__RightAssignment_1_2");
					put(grammarAccess.getAndAccess().getRightAssignment_1_2(), "rule__And__RightAssignment_1_2");
					put(grammarAccess.getPrimaryAccess().getExpressionAssignment_1_2(), "rule__Primary__ExpressionAssignment_1_2");
					put(grammarAccess.getAtomicAccess().getNameAssignment_0_3(), "rule__Atomic__NameAssignment_0_3");
					put(grammarAccess.getAtomicAccess().getNameAssignment_1_1(), "rule__Atomic__NameAssignment_1_1");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.blended.data.ui.contentassist.antlr.internal.InternalDataParser typedParser = (org.blended.data.ui.contentassist.antlr.internal.InternalDataParser) parser;
			typedParser.entryRuleDataModel();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public DataGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(DataGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
