package pt.ist.socialsoftware.blendedworkflow.adapters;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.yawlfoundation.yawl.authentication.YExternalClient;
import org.yawlfoundation.yawl.elements.YAWLServiceReference;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;

import pt.ist.socialsoftware.blendedworkflow.adapters.convertor.SpecUtils;
import pt.ist.socialsoftware.blendedworkflow.adapters.convertor.StringUtils;
import pt.ist.socialsoftware.blendedworkflow.engines.domain.BlendedWorkflow;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

@SuppressWarnings("deprecation")
public class YAWLAdapterTestInterface extends Application {
	
	private static Logger log = Logger.getLogger("bwServicelogger");
	
	// Inputs
	private static String YAWL_SPEC_FILENAME = "C:/Users/User/Desktop/CreditRatingProcess.yawl";
	private static String specID ="CreditRatingProcess.ywl";
	private String caseInstanceID ="";

	
	@Override
	public void init() {
		final Window main = new Window("Blended Workflow Service");
		setMainWindow(main);
		
		HorizontalLayout registrationButtons = new HorizontalLayout();
		HorizontalLayout loadButtons = new HorizontalLayout();
		HorizontalLayout launchButtons = new HorizontalLayout();
		HorizontalLayout workitemButtons = new HorizontalLayout();
		
		main.addComponent(registrationButtons);
		main.addComponent(loadButtons);
		main.addComponent(launchButtons);
		main.addComponent(workitemButtons);
		
		final TextField results = new TextField("Results:", "...");
		results.setWidth("100%");
        main.addComponent(results);
	
		// Registration Buttons
        registrationButtons.addComponent(new Button("Connect to Yawl",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			BlendedWorkflow.getInstance().getYawlAdapter().connectYAWL();
					results.setValue("Connect to Yawl - Sucesso!");
        		}
        		catch (Exception e) {
					results.setValue("Connect to Yawl - Fail!");
				}
        	}
        }));

        registrationButtons.addComponent(new Button("Disconnect to Yawl",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			BlendedWorkflow.getInstance().getYawlAdapter().disconnectYAWL();
        			results.setValue("Disconnect to Yawl - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Disconnect to Yawl - Fail!");
        		}
        	}
        }));
        
        registrationButtons.addComponent(new Button("Get Connection State",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			
    				log.debug("Clients Registered:");
        			for (YExternalClient client : BlendedWorkflow.getInstance().getYawlAdapter().getClientAccounts()) {
        				log.debug(client.getUserName());
        			}
    				log.debug("Services Registered:");
        			for (YAWLServiceReference service : BlendedWorkflow.getInstance().getYawlAdapter().getRegisteredServices()) {
        				log.debug(service.getURI());
        			}

        			results.setValue("Get Connection State - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Get Connection State - Fail!");
        		}
        	}
        }));
        
        loadButtons.addComponent(new Button("Load Specification",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			String spec = StringUtils.fileToString(YAWL_SPEC_FILENAME);
        			BlendedWorkflow.getInstance().getYawlAdapter().loadSpecification(spec);
        			results.setValue("Load Specification - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Load Specification - Fail!");
        		}
        	}
        }));
        
        loadButtons.addComponent(new Button("Unload Specification",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			BlendedWorkflow.getInstance().getYawlAdapter().unloadSpecification(specID);
        			results.setValue(" Unload Specification - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Unload Specification - Fail!");
        		}
        	}
        }));
        
        loadButtons.addComponent(new Button("Get Loaded Specifications",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			
       				log.debug("Loaded Specifications: ");
        			for (SpecificationData specification : BlendedWorkflow.getInstance().getYawlAdapter().getLoadedSpecs()) {
        				log.debug(specification.getName());
        			}
        		
        			results.setValue("Get Loaded Specifications - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Get Loaded Specifications - Fail!");
        		}
        	}
        }));
        
        launchButtons.addComponent(new Button("Launch Specification",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			String spec = StringUtils.fileToString(YAWL_SPEC_FILENAME);
    				specID = SpecUtils.getYAWLSpecificationIDFromSpec(spec).getIdentifier();
        			caseInstanceID = BlendedWorkflow.getInstance().getYawlAdapter().launchCase(specID);
        			results.setValue("Launch Specification - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Launch Specification - Fail!");
        		}
        	}
        }));
        
        launchButtons.addComponent(new Button("Cancel Case",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			BlendedWorkflow.getInstance().getYawlAdapter().cancelCase(caseInstanceID);
        			results.setValue("Cancel Case - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("Cancel Case - Fail!");
        		}
        	}
        }));
        
//        workitemButtons.addComponent(new Button("Get Enabled Workitems",
//        		new Button.ClickListener() {
//        	public void buttonClick(ClickEvent event) {
//        		try {
//        			BlendedWorkflow.getInstance().getYawlAdapter().notifyActiveTasks(caseInstanceID);
//        			results.setValue("Get Enabled Workitems - Sucesso!");
//        		}
//        		catch (Exception e) {
//        			results.setValue("Get Enabled Workitems - Fail!");
//        		}
//        	}
//        }));
        
        workitemButtons.addComponent(new Button("CheckIn Workitem",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			BlendedWorkflow.getInstance().getYawlAdapter().connectYAWL();
        			results.setValue("CheckIn Workitem - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("CheckIn Workitem - Fail!");
        		}
        	}
        }));
        
        workitemButtons.addComponent(new Button("CheckOut Workitem",
        		new Button.ClickListener() {
        	public void buttonClick(ClickEvent event) {
        		try {
        			BlendedWorkflow.getInstance().getYawlAdapter().connectYAWL();
        			results.setValue("CheckOut Workitem - Sucesso!");
        		}
        		catch (Exception e) {
        			results.setValue("CheckOut Workitem - Fail!");
        		}
        	}
        }));
	}
}
