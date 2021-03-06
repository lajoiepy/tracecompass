/*******************************************************************************
 * Copyright (c) 2013, 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Patrick Tasse - Initial API and implementation
 *******************************************************************************/

package org.eclipse.tracecompass.tmf.ui.views.callstack;

import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystem;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.TimeGraphEntry;

/**
 * An entry, or row, in the Call Stack view
 *
 * @author Patrick Tasse
 */
@Deprecated
public class CallStackEntry extends TimeGraphEntry {

    private final int fQuark;
    private final int fStackLevel;
    private final int fProcessId;
    private final ITmfTrace fTrace;
    private String fFunctionName;
    private long fFunctionEntryTime;
    private long fFunctionExitTime;
    private @NonNull ITmfStateSystem fSS;

    /**
     * Standard constructor
     *
     * @param name
     *            The parent thread name
     * @param quark
     *            The call stack quark
     * @param stackLevel
     *            The stack level
     * @param processId The ID of the process this entry belongs to
     * @param trace
     *            The trace that this view is talking about
     * @param ss
     *            The call stack state system
     * @since 2.0
     */
    public CallStackEntry(String name, int quark, int stackLevel, int processId,
            ITmfTrace trace, @NonNull ITmfStateSystem ss) {
        super(name, 0, 0);
        fQuark = quark;
        fStackLevel = stackLevel;
        fProcessId = processId;
        fTrace = trace;
        fFunctionName = ""; //$NON-NLS-1$
        fSS = ss;
    }

    /**
     * Get the function name of the call stack entry
     * @return the function name
     */
    public String getFunctionName() {
        return fFunctionName;
    }

    /**
     * Set the function name of the call stack entry
     * @param functionName the function name
     */
    public void setFunctionName(String functionName) {
        fFunctionName = functionName;
    }

    /**
     * Set the selected function entry time
     *
     * @param entryTime
     *            the function entry time
     */
    public void setFunctionEntryTime(long entryTime) {
        fFunctionEntryTime = entryTime;
    }

    /**
     * Get the selected function entry time
     *
     * @return the function entry time
     */
    public long getFunctionEntryTime() {
        return fFunctionEntryTime;
    }

    /**
     * Set the selected function exit time
     *
     * @param exitTime
     *            the function exit time
     */
    public void setFunctionExitTime(long exitTime) {
        fFunctionExitTime = exitTime;
    }

    /**
     * Get the selected function exit time
     *
     * @return the function exit time
     */
    public long getFunctionExitTime() {
        return fFunctionExitTime;
    }

    /**
     * Retrieve the attribute quark that's represented by this entry.
     *
     * @return The integer quark
     */
    public int getQuark() {
        return fQuark;
    }

    /**
     * Retrieve the stack level associated with this entry.
     *
     * @return The stack level or 0
     */
    public int getStackLevel() {
        return fStackLevel;
    }

    /**
     * Retrieve the ID of the process this entry belongs to.
     *
     * @return The ID of the process
     * @since 2.0
     */
    public int getProcessId() {
        return fProcessId;
    }

    /**
     * Retrieve the trace that is associated to this view.
     *
     * @return The trace
     */
    public ITmfTrace getTrace() {
        return fTrace;
    }

    /**
     * Retrieve the call stack state system associated with this entry.
     *
     * @return The call stack state system
     */
    public @NonNull ITmfStateSystem getStateSystem() {
        return fSS;
    }

    @Override
    public boolean matches(@NonNull Pattern pattern) {
        return pattern.matcher(fFunctionName).find();
    }

}
