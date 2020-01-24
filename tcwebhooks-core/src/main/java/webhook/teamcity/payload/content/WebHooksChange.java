package webhook.teamcity.payload.content;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.VcsFileModification;
import jetbrains.buildServer.vcs.VcsRootInstance;

import webhook.teamcity.Loggers;

public class WebHooksChange {

	private static @Nullable String tryGetVcsRootName(final SVcsModification modification) {
		if(modification.isPersonal()) {
			return null;
		}

		final VcsRootInstance vcsRoot;
		try {
			vcsRoot = modification.getVcsRoot();
			return vcsRoot.getName();
		} catch(UnsupportedOperationException e) {
			Loggers.SERVER.debug(e);
			// Modifications tied to personal changes don't have a backing VCS root, and throw when trying
			// to access getVcsRoot() (see issue #132)
			return null;
		} catch(Exception e) {
			Loggers.SERVER.debug(e);
			return null;
		}
	}

	public static WebHooksChange build(SVcsModification modification, boolean includeVcsFileModifications) {
		Loggers.SERVER.warn(modification.toString());
		WebHooksChange change = new WebHooksChange();
		change.setComment(modification.getDescription());
		change.setUsername(modification.getUserName());
		change.setVcsRoot(tryGetVcsRootName(modification));
		if (includeVcsFileModifications) {
			change.files = new ArrayList<>();
			for (VcsFileModification fileModification: modification.getChanges()){
				change.files.add(fileModification.getRelativeFileName());
			}
		}
		return change;
	}


	private List<String> files;
	private String comment;
	private String username;
	private String vcsRoot;
	
	private void setVcsRoot(String name) {
		this.vcsRoot = name;
	}

	public String getVcsRoot() {
		return vcsRoot;
	}
	
	private void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	private void setComment(String description) {
		this.comment = description;
	}
	
	public String getComment() {
		return comment;
	}
	
	public List<String> getFiles() {
		return files;
	}
	
}
