package org.jenkins.ci.plugins.jenkinslint.check;

import hudson.model.Node;
import hudson.model.Slave;
import hudson.slaves.DumbSlave;
import hudson.slaves.JNLPLauncher;
import hudson.slaves.NodeProperty;
import hudson.slaves.RetentionStrategy;
import org.jenkins.ci.plugins.jenkinslint.AbstractTestCase;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * SlaveVersionChecker Test Case.
 *
 * @author Victor Martinez
 */
public class SlaveVersionCheckerTestCase extends AbstractTestCase {
    private SlaveVersionChecker checker = new SlaveVersionChecker(true);

    @Test public void testDefaultSlave() throws Exception {
        Slave slave = createSlave("default", "", "");
        assertFalse(checker.executeCheck(slave));
    }
    @Test public void testControlComment() throws Exception {
        Slave slave = createSlave("default", "", "");
        assertFalse(checker.isIgnored(slave.getNodeDescription()));
        slave = createSlave("default", "#lint:ignore:" + checker.getClass().getSimpleName(), "");
        assertTrue(checker.isIgnored(slave.getNodeDescription()));
    }

    private Slave createSlave(String name, String description, String label) throws Exception {
        return new DumbSlave(name, description, "/wherever", "1", Node.Mode.NORMAL, label, new JNLPLauncher(), RetentionStrategy.NOOP, Collections.<NodeProperty<?>>emptyList());
    }
}
