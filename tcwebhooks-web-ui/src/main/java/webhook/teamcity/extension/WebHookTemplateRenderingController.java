package webhook.teamcity.extension;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;
import org.springframework.web.servlet.ModelAndView;

import webhook.WebHook;
import webhook.WebHookImpl;
import webhook.teamcity.BuildState;
import webhook.teamcity.BuildStateEnum;
import webhook.teamcity.Loggers;
import webhook.teamcity.WebHookContentBuilder;
import webhook.teamcity.WebHookFactory;
import webhook.teamcity.extension.bean.template.TemplateRenderingBean;
import webhook.teamcity.extension.bean.template.TemplateRenderingBeanJsonSerialiser;
import webhook.teamcity.payload.WebHookPayloadManager;
import webhook.teamcity.payload.WebHookTemplateContent;
import webhook.teamcity.payload.WebHookTemplateResolver;
import webhook.teamcity.payload.content.WebHookPayloadContent;
import webhook.teamcity.payload.template.render.WebHookStringRenderer;
import webhook.teamcity.payload.template.render.WebHookStringRenderer.WebHookHtmlRendererException;
import webhook.teamcity.settings.WebHookConfig;
import webhook.teamcity.settings.WebHookProjectSettings;
import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.serverSide.SFinishedBuild;
import jetbrains.buildServer.serverSide.SProject;
import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.settings.ProjectSettingsManager;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;

public class WebHookTemplateRenderingController extends BaseController {
	
	
	private final WebControllerManager myWebManager;
    private SBuildServer myServer;
    private ProjectSettingsManager mySettings;
    private final String myPluginPath;
    private final WebHookPayloadManager myPayloadManager;
	private final WebHookTemplateResolver myTemplateResolver;
	private final WebHookContentBuilder myContentBuilder;
	private final WebHookFactory myWebHookFactory;
    
    public WebHookTemplateRenderingController(SBuildServer server, WebControllerManager webManager, 
    		ProjectSettingsManager settings, WebHookProjectSettings whSettings, WebHookPayloadManager payloadManager,
    		WebHookTemplateResolver templateResolver, WebHookContentBuilder builder, WebHookFactory webHookFactory,
    		PluginDescriptor pluginDescriptor) {
        super(server);
        myWebManager = webManager;
        myServer = server;
        mySettings = settings;
        myPluginPath = pluginDescriptor.getPluginResourcesPath();
        myPayloadManager = payloadManager;
        myTemplateResolver = templateResolver;
        myContentBuilder = builder;
        myWebHookFactory = webHookFactory;
    }
    
    public void register(){
	      myWebManager.registerController("/webhooks/renderTemplate.html", this);
	}

	@Override
	protected ModelAndView doHandle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if (	request.getParameter("buildState") != null
			&&	request.getParameter("payloadTemplate") != null
			&&	request.getParameter("payloadFormat") != null
			&&	request.getParameter("buildId") != null
			&&	request.getParameter("projectId") != null)
		{
			
			SProject myproject = this.myServer.getProjectManager().findProjectByExternalId(request.getParameter("projectId"));
			SBuild sBuild = this.myServer.findBuildInstanceById(Long.parseLong(request.getParameter("buildId")));
			String buildState = request.getParameter("buildState");
			String payloadFormat = request.getParameter("payloadFormat");
			String payloadTemplate = request.getParameter("payloadTemplate");
			
			BuildStateEnum state = BuildStateEnum.findBuildState(buildState);
			
			WebHookTemplateContent content;
			if (sBuild.getBranch() != null){
				content = myTemplateResolver.findWebHookBranchTemplate(state, myproject, payloadFormat, payloadTemplate);
			} else {
				content = myTemplateResolver.findWebHookTemplate(state, myproject, payloadFormat, payloadTemplate);
			}
			
			WebHook wh = new WebHookImpl();
			wh.setEnabled(true);
			wh.setBuildStates(new BuildState().setAllEnabled());
			
			wh = myContentBuilder.buildWebHookContent(wh, new WebHookConfig("", true, wh.getBuildStates(), payloadFormat, payloadTemplate, true, true, new TreeSet<String>()), sBuild, state, true);
			
			WebHookStringRenderer renderer = myPayloadManager.getFormat(payloadFormat).getWebHookStringRenderer();
			
			String rendererTextTemplate = "No template available for this format.";
			if (content != null){
				rendererTextTemplate = renderer.render(content.getTemplateText());
			}
			
			HashMap<String,Object> params = new HashMap<String,Object>();
			String rendererTextActualContent = "";
			
			try {
				rendererTextActualContent = renderer.render(wh.getPayload());
			} catch (WebHookHtmlRendererException ex){
				rendererTextActualContent = "Error rendering webhook payload for this build: " + ex.getMessage().toString();
				Loggers.SERVER.info(ex);
			}
			params.put("templateRendering", TemplateRenderingBeanJsonSerialiser.serialise(
											TemplateRenderingBean.build( 
															myproject.getExternalId(), 
															buildState.replace("Branch", ""), 
															payloadFormat, 
															rendererTextTemplate, 
															rendererTextActualContent
														)
											)
						);
			return new ModelAndView(myPluginPath + "WebHook/templateRendering.jsp", params);
			
			
		}
		return null;
	}

}