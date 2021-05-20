package gg.amy.soulfire.utils;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amy
 * @since 5/19/21.
 */
public final class AgentUtils {
    private AgentUtils() {
    }

    public static List<String> detectAgents() {
        final var runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        final var jvmArgs = runtimeMXBean.getInputArguments();
        final var agents = new ArrayList<String>();
        for(final var arg : jvmArgs) {
            if(arg.startsWith("-javaagent:")) {
                agents.add(arg.replace("-javaagent:", ""));
            }
        }
        return agents;
    }
}
