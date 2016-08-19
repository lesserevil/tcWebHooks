package webhook.teamcity.server.rest.model.template;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import jetbrains.buildServer.server.rest.errors.BadRequestException;
import jetbrains.buildServer.server.rest.errors.NotFoundException;
import jetbrains.buildServer.server.rest.model.Fields;
import jetbrains.buildServer.server.rest.util.ValueWithDefault;
import jetbrains.buildServer.serverSide.WebLinks;
import lombok.Data;
import lombok.Getter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import webhook.teamcity.payload.template.WebHookTemplateFromXml;
import webhook.teamcity.server.rest.WebHookWebLinks;
import webhook.teamcity.server.rest.data.DataProvider;
import webhook.teamcity.server.rest.data.TemplateFinder;
import webhook.teamcity.server.rest.util.BeanContext;
import webhook.teamcity.settings.entity.WebHookTemplateEntity;
import webhook.teamcity.settings.entity.WebHookTemplateEntity.WebHookTemplateItem;
import webhook.teamcity.settings.entity.WebHookTemplateEntity.WebHookTemplateState;
import webhook.teamcity.settings.entity.WebHookTemplateEntity.WebHookTemplateText;

import com.intellij.openapi.util.text.StringUtil;

@XmlRootElement(name = "template")
@XmlType(name = "template", propOrder = { "id", "name", "description", "href",	"webUrl", "templateText", "branchTemplateText", "templates" })

public class Template {
	@XmlAttribute
	public String id;

	@XmlAttribute
	public String name;

	@XmlAttribute
	public String href;

	@XmlAttribute
	public String description;

	@XmlAttribute
	public String webUrl;
	
	@XmlElement(name="default-template", required=false)
	public TemplateText templateText;
	
	
	@XmlElement(name="default-branch-template", required=false)
	public BranchTemplateText branchTemplateText;
	
	@XmlElement(name = "template-item") @XmlElementWrapper(name = "templates") @Getter
	List<TemplateItem> templates;
	
	@XmlType @Data @XmlAccessorType(XmlAccessType.FIELD)
	public static class TemplateText extends BranchTemplateText {
		@XmlAttribute(name = "use-for-branch-template")
		public Boolean useTemplateTextForBranch = false;
		
		TemplateText(){
			super();
		}
		
		TemplateText(WebHookTemplateEntity webHookTemplateEntity, String id, final @NotNull Fields fields, @NotNull final BeanContext beanContext){
			super(webHookTemplateEntity, id, fields, beanContext);
			this.href = ValueWithDefault.decideDefault(fields.isIncluded("href"), beanContext.getApiUrlBuilder().getDefaultTemplateHref(webHookTemplateEntity));
			if (webHookTemplateEntity.getDefaultTemplate() != null){
				useTemplateTextForBranch = ValueWithDefault.decideDefault(fields.isIncluded("useTemplateTextForBranch"), webHookTemplateEntity.getDefaultTemplate().isUseTemplateTextForBranch());
			}
		}
		
		TemplateText(WebHookTemplateEntity webHookTemplateEntity, WebHookTemplateItem webHookTemplateItem, String id, final @NotNull Fields fields, @NotNull final BeanContext beanContext){
			super(webHookTemplateEntity, webHookTemplateItem, id, fields, beanContext);
			this.href = ValueWithDefault.decideDefault(fields.isIncluded("href"), beanContext.getApiUrlBuilder().getTemplateHref(webHookTemplateEntity, webHookTemplateItem));
			if (webHookTemplateItem.getTemplateText() != null){
				useTemplateTextForBranch = ValueWithDefault.decideDefault(fields.isIncluded("useTemplateTextForBranch"), webHookTemplateEntity.getDefaultTemplate().isUseTemplateTextForBranch());
			}
		}
		
				

	}
		
	@XmlType @Data @XmlAccessorType(XmlAccessType.FIELD)
	public static class BranchTemplateText {

		@XmlAttribute
		public String id;
		
		@XmlAttribute
		public String href;
		
		@XmlAttribute
		public String webUrl;

		public BranchTemplateText() {
			// empty constructor for JAXB
		}
		
		BranchTemplateText(WebHookTemplateEntity webHookTemplateEntity, String id, Fields fields, BeanContext beanContext) {
			this.id = ValueWithDefault.decideDefault(fields.isIncluded("id"), String.valueOf(id));
			this.href = ValueWithDefault.decideDefault(fields.isIncluded("href"), beanContext.getApiUrlBuilder().getDefaultBranchTemplateHref(webHookTemplateEntity));
			this.webUrl = ValueWithDefault.decideDefault(fields.isIncluded("webUrl"), beanContext.getSingletonService(WebHookWebLinks.class).getWebHookDefaultBranchTemplateTextUrl(webHookTemplateEntity));
		}
		
		BranchTemplateText(WebHookTemplateEntity webHookTemplateEntity, WebHookTemplateItem webHookTemplateItem, String id, Fields fields, BeanContext beanContext) {
			this.id = ValueWithDefault.decideDefault(fields.isIncluded("id"), String.valueOf(id));
			this.href = ValueWithDefault.decideDefault(fields.isIncluded("href"), beanContext.getApiUrlBuilder().getBranchTemplateTextHref(webHookTemplateEntity, webHookTemplateItem));
			this.webUrl = ValueWithDefault.decideDefault(fields.isIncluded("webUrl"), beanContext.getSingletonService(WebHookWebLinks.class).getWebHookBranchTemplateTextUrl(webHookTemplateEntity, webHookTemplateItem));
		}
		
	}

	@XmlType(name = "template-item") @Data @XmlAccessorType(XmlAccessType.FIELD)
	public static class TemplateItem {
		@NotNull @XmlElement(name = "template-text")
		TemplateText templateText;

		@XmlElement(name = "branch-template-text")
		TemplateText branchTemplateText;

		@XmlAttribute
		boolean enabled = true;

		@XmlElement(name = "state")	@XmlElementWrapper(name = "states")
		private List<WebHookTemplateState> states = new ArrayList<WebHookTemplateState>();

		TemplateItem() {
			// empty constructor for JAXB
		}

		public TemplateItem(WebHookTemplateEntity template, WebHookTemplateItem templateItem, String id, Fields fields, BeanContext beanContext) {
			this.enabled = templateItem.isEnabled();
			this.templateText = new TemplateText(template, id, fields, beanContext);
		}
		
		
	}

	/**
	 * This is used only when posting a link to a template
	 */
	@XmlAttribute
	public String locator;

	public Template() {
	}

	public Template(@NotNull final WebHookTemplateEntity template,
			final @NotNull Fields fields, @NotNull final BeanContext beanContext) {
		
		id = ValueWithDefault.decideDefault(fields.isIncluded("id"),
				template.getName());
		name = ValueWithDefault.decideDefault(fields.isIncluded("name"),
				template.getName());
		description = ValueWithDefault.decideDefault(
				fields.isIncluded("description"),
				template.getTemplateDescription());

		href = ValueWithDefault.decideDefault(fields.isIncluded("href"), beanContext.getApiUrlBuilder().getHref(template));
		
		webUrl = ValueWithDefault.decideDefault(fields.isIncluded("webUrl"), beanContext.getSingletonService(WebHookWebLinks.class).getWebHookTemplateUrl(template));
		
		if (template.getDefaultTemplate() != null){
			templateText = new TemplateText(template, "default", fields, beanContext);
		}
		if (template.getDefaultBranchTemplate() != null){
			branchTemplateText = new BranchTemplateText(template, "defaultBranch", fields, beanContext);
		}

		int count = 0;
		templates = new ArrayList<Template.TemplateItem>();
		
		if (template.getTemplates() != null){
			for (WebHookTemplateItem templateItem: template.getTemplates().getTemplates()){
				templates.add(new TemplateItem(template, templateItem, description, fields, beanContext));
			}	
		}
		
		// final String descriptionText = template.getTemplateDescription();
		// description =
		// ValueWithDefault.decideDefault(fields.isIncluded("description"),
		// StringUtil.isEmpty(descriptionText) ? null : descriptionText);

	}

	@Nullable
	public static String getFieldValue(final WebHookTemplateEntity template,
			final String field) {
		if ("id".equals(field)) {
			return template.getName();
		} else if ("description".equals(field)) {
			return template.getTemplateDescription();
		} else if ("name".equals(field)) {
			return template.getName();
		}
		throw new NotFoundException("Field '" + field
				+ "' is not supported.  Supported are: id, name, description.");
	}

	public static void setFieldValueAndPersist(
			final WebHookTemplateFromXml template, final String field,
			final String value, @NotNull final DataProvider dataProvider) {
		if ("name".equals(field)) {
			if (StringUtil.isEmpty(value)) {
				throw new BadRequestException("Project name cannot be empty.");
			}
			template.setTemplateShortName(value);
			template.persist();
			return;
		} else if ("description".equals(field)) {
			template.setTemplateDescription(value);
			template.persist();
			return;

		}
		throw new BadRequestException(
				"Setting field '"
						+ field
						+ "' is not supported. Supported are: name, description, archived");
	}

	public WebHookTemplateEntity getTemplateFromPosted(
			TemplateFinder templateFinder) {
		return templateFinder.findTemplateById(this.name);
	}

	/*
	 * @NotNull public WebHookTemplate getProjectFromPosted(@NotNull
	 * TemplateFinder templateFinder) { //todo: support posted parentProject
	 * fields here String locatorText = ""; if (internalId != null) locatorText
	 * = "internalId:" + internalId; if (id != null) locatorText +=
	 * (!locatorText.isEmpty() ? "," : "") + "id:" + id; if
	 * (locatorText.isEmpty()) { locatorText = locator; } else { if (locator !=
	 * null) { throw new BadRequestException(
	 * "Both 'locator' and 'id' or 'internalId' attributes are specified. Only one should be present."
	 * ); } } if (jetbrains.buildServer.util.StringUtil.isEmpty(locatorText)){
	 * //find by href for compatibility with 7.0 if
	 * (!jetbrains.buildServer.util.StringUtil.isEmpty(href)){ return
	 * templateFinder
	 * .getTemplate(jetbrains.buildServer.util.StringUtil.lastPartOf(href,
	 * '/')); } throw new BadRequestException(
	 * "No project specified. Either 'id', 'internalId' or 'locator' attribute should be present."
	 * ); } return templateFinder.getProject(locatorText); }
	 */

}
