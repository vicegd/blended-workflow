/*
 * generated by Xtext
 */
package org.blended.data;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class DataStandaloneSetup extends DataStandaloneSetupGenerated{

	public static void doSetup() {
		new DataStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

