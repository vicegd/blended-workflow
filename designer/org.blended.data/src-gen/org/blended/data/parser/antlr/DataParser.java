/*
 * generated by Xtext
 */
package org.blended.data.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.blended.data.services.DataGrammarAccess;

public class DataParser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private DataGrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.blended.data.parser.antlr.internal.InternalDataParser createParser(XtextTokenStream stream) {
		return new org.blended.data.parser.antlr.internal.InternalDataParser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "DataModel";
	}
	
	public DataGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(DataGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
