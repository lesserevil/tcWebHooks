package webhook.teamcity;

import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.serverSide.BuildStatistics;
import jetbrains.buildServer.serverSide.CompilationBlockBean;
import jetbrains.buildServer.serverSide.STestRun;
import jetbrains.buildServer.tests.TestName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MockSBuildStatistics implements BuildStatistics {
    @Override
    public List<STestRun> getIgnoredTests() {
        return null;
    }

    @Override
    public List<STestRun> getPassedTests() {
        return null;
    }

    @Override
    public List<CompilationBlockBean> getCompilationErrorBlocks() {
        return null;
    }

    @Override
    public int getPreviousFailedTestsCount() {
        return 0;
    }

    @Override
    public long getTotalDuration() {
        return 0;
    }

    @Override
    public List<STestRun> getTests(@Nullable Status status, @NotNull Order order) {
        return null;
    }

    @Override
    public List<STestRun> getAllTests() {
        List<STestRun> trun = new ArrayList<>();
        trun.add(new MockSTestRun());
        return trun;
    }

    @Override
    public List<STestRun> findTestsBy(TestName testName) {
        return null;
    }

    @Nullable
    @Override
    public STestRun findTestByTestNameId(long l) {
        return null;
    }

    @Nullable
    @Override
    public STestRun findTestByTestRunId(long l) {
        return null;
    }

    @Override
    public int getAllTestRunCount() {
        return 0;
    }

    @Override
    public boolean isEmpty1() {
        return false;
    }

    @Override
    public Status getBuildStatus() {
        return null;
    }

    @Override
    public String getCurrentStage() {
        return null;
    }

    @Override
    public int getCompilationErrorsCount() {
        return 0;
    }

    @Override
    public int getPassedTestCount() {
        return 0;
    }

    @Override
    public int getNewFailedCount() {
        return 0;
    }

    @Override
    public int getIgnoredTestCount() {
        return 0;
    }

    @Override
    public int getFailedTestCount() {
        return 0;
    }

    @Override
    public int getAllTestCount() {
        return 0;
    }

    @Override
    public List<STestRun> getFailedTests() {
        return null;
    }

    @NotNull
    @Override
    public List<STestRun> getMutedTests() {
        return null;
    }

    @Override
    public int getMutedTestsCount() {
        return 0;
    }

    @NotNull
    @Override
    public List<STestRun> getFailedTestsIncludingMuted() {
        return null;
    }

    @Override
    public int getSignature() {
        return 0;
    }
}
