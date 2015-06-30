/*
 * generated by Xtext
 */
package org.blended.goal.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.blended.goal.services.GoalGrammarAccess;

public class GoalParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private GoalGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.blended.goal.parser.antlr.internal.InternalGoalParser createParser(XtextTokenStream stream) {
		return new org.blended.goal.parser.antlr.internal.InternalGoalParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "GoalModel";
	}
	
	public GoalGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(GoalGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
