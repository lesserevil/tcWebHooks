package webhook.teamcity.test.springmock;

import jetbrains.buildServer.ExtensionsCollection;
import jetbrains.buildServer.ServiceNotFoundException;
import jetbrains.buildServer.TeamCityExtension;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.auth.LoginConfiguration;
import jetbrains.buildServer.status.StatusProvider;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.users.UserModel;
import jetbrains.buildServer.util.ItemProcessor;
import jetbrains.buildServer.vcs.VcsManager;
import jetbrains.buildServer.vcs.VcsModificationHistory;
import jetbrains.buildServer.version.ServerVersionInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class MockSBuildServer implements SBuildServer {

	@Override
	public List<String> getResponsibilityIds(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] fetchData(long userId, long buildId, String sourceId,
			String whatToFetch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRootUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRootUrl(String rootUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends TeamCityExtension> void registerExtension(
			Class<T> extensionClass, String sourceId, T extension) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends TeamCityExtension> void unregisterExtension(
			Class<T> extensionClass, String sourceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getSingletonService(Class<T> serviceClass)
			throws ServiceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T findSingletonService(Class<T> serviceClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Collection<T> getServices(Class<T> serviceClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TeamCityExtension> Collection<T> getExtensions(
			Class<T> extensionClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TeamCityExtension> void foreachExtension(
			Class<T> agentExtensionClass, ExtensionAction<T> action) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends TeamCityExtension> Collection<String> getExtensionSources(
			Class<T> extensionClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TeamCityExtension> T getExtension(
			Class<T> extensionClass, String sourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@NotNull
	@Override
	public <T extends TeamCityExtension> ExtensionsCollection<T> getExtensionsCollection(@NotNull Class<T> aClass) {
		return null;
	}

	@Override
	public SBuild findBuildInstanceById(long buildId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SBuild findPreviousBuild(SBuild build) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SBuild findPreviousBuild(SBuild build, BuildDataFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SBuild findNextBuild(SBuild build, BuildDataFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SBuild> findBuildInstances(Collection<Long> buildIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SBuild findBuildInstanceByBuildNumber(String buildTypeId,
			String buildNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SBuild> findBuildInstancesByBuildNumber(String buildTypeId,
			String buildNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processBuilds(BuildQueryOptions options,
			ItemProcessor<SBuild> processor) {
		// TODO Auto-generated method stub

	}

	@Override
	public SRunningBuild findRunningBuildById(long buildId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SRunningBuild getRunningBuildOnAgent(SBuildAgent agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SRunningBuild> getRunningBuilds(User user,
			BuildDataFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SRunningBuild> getRunningBuilds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfRunningBuilds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<SBuildType, List<SRunningBuild>> getRunningStatus(User user,
			BuildDataFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStarted() {
		return false;
	}

	@Override
	public boolean isShuttingDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addListener(BuildServerListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(BuildServerListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public ProjectManager getProjectManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildQueue getQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildHistory getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getUserModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VcsManager getVcsManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VcsModificationHistory getVcsHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean flushQueue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RunTypeRegistry getRunTypeRegistry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLRunner getSQLRunner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonalBuildManager getPersonalBuildManager() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	//public SourceVersionProvider getSourceVersionProvider() {
		// TODO Auto-generated method stub
		//return null;
	//}

	@Override
	public LoginConfiguration getLoginConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getServerMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getServerMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@NotNull
	@Override
	public ServerVersionInfo getVersion() {
		return null;
	}

	@Override
	public String getFullServerVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBuildNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getBuildDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerRootPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScheduledExecutorService getExecutor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildAgentManager getBuildAgentManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusProvider getStatusProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LicensingPolicy getLicensingPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SBuild> getEntriesSince(SBuild build, SBuildType buildType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDatabaseCreatedOnStartup() {
		// TODO Auto-generated method stub
		return false;
	}

}
