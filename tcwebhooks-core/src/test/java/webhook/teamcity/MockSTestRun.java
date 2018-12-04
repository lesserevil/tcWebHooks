package webhook.teamcity;

import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.STest;
import jetbrains.buildServer.serverSide.STestRun;
import jetbrains.buildServer.serverSide.TestFailureInfo;
import jetbrains.buildServer.serverSide.mute.CurrentMuteInfo;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.tests.TestName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MockSTestRun implements STestRun {
    @NotNull
    @Override
    public STest getTest() {
        return new STest() {

            @NotNull
            @Override
            public TestName getName() {
                return null;
            }

            @Override
            public long getTestNameId() {
                return 0;
            }

            @Override
            public long getTestId() {
                return 0;
            }

            @NotNull
            @Override
            public String getProjectId() {
                return null;
            }

            @Nullable
            @Override
            public String getProjectExternalId() {
                return null;
            }

            @Nullable
            @Override
            public TestNameResponsibilityEntry getResponsibility() {
                return null;
            }

            @NotNull
            @Override
            public List<TestNameResponsibilityEntry> getAllResponsibilities() {
                return null;
            }

            @Override
            public boolean isMuted() {
                return false;
            }

            @Nullable
            @Override
            public CurrentMuteInfo getCurrentMuteInfo() {
                return null;
            }
        };
    }

    @NotNull
    @Override
    public SBuild getBuild() {
        return null;
    }

    @Override
    public long getBuildId() {
        return 0;
    }

    @Override
    public int getOrderId() {
        return 0;
    }

    @Override
    public int getTestRunId() {
        return 0;
    }

    @Override
    public int getInvocationCount() {
        return 0;
    }

    @Override
    public int getFailedInvocationCount() {
        return 0;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @NotNull
    @Override
    public Status getStatus() {
        return null;
    }

    @NotNull
    @Override
    public String getStatusText() {
        return null;
    }

    @Nullable
    @Override
    public TestFailureInfo getFailureInfo() {
        return null;
    }

    @Nullable
    @Override
    public SBuild getFirstFailed() {
        return null;
    }

    @Override
    public void setFirstFailedIn(@Nullable SBuild sBuild) {

    }

    @Nullable
    @Override
    public SBuild getFixedIn() {
        return null;
    }

    @Override
    public void setFixedIn(@Nullable SBuild sBuild) {

    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public boolean isNewFailure() {
        return false;
    }

    @Override
    public boolean isMuted() {
        return false;
    }

    @Override
    public boolean isIgnored() {
        return false;
    }

    @Nullable
    @Override
    public MuteInfo getMuteInfo() {
        return null;
    }

    @Nullable
    @Override
    public String getFullText() {
        return null;
    }

    @Nullable
    @Override
    public String getIgnoreComment() {
        return null;
    }
}
