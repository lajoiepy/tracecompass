/*******************************************************************************
 * Copyright (c) 2012, 2015 Ericsson
 * Copyright (c) 2010, 2011 École Polytechnique de Montréal
 * Copyright (c) 2010, 2011 Alexandre Montplaisir <alexandre.montplaisir@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel;

import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.analysis.os.linux.core.trace.IKernelAnalysisEventLayout;
import org.eclipse.tracecompass.common.core.NonNullUtils;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.IrqEntryHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.IrqExitHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.KernelEventHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.PiSetprioHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.ProcessExitHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.ProcessForkHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.ProcessFreeHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SchedSwitchHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SchedWakeupHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SoftIrqEntryHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SoftIrqExitHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SoftIrqRaiseHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.StateDumpHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SysEntryHandler;
import org.eclipse.tracecompass.internal.analysis.os.linux.core.kernel.handlers.SysExitHandler;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.statesystem.core.exceptions.AttributeNotFoundException;
import org.eclipse.tracecompass.statesystem.core.exceptions.StateValueTypeException;
import org.eclipse.tracecompass.statesystem.core.exceptions.TimeRangeException;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.statesystem.AbstractTmfStateProvider;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

import com.google.common.collect.ImmutableMap;

/**
 * This is the state change input plugin for TMF's state system which handles
 * the LTTng 2.0 kernel traces in CTF format.
 *
 * It uses the reference handler defined in CTFKernelHandler.java.
 *
 * @author Alexandre Montplaisir
 */
public class KernelStateProvider extends AbstractTmfStateProvider {

    // ------------------------------------------------------------------------
    // Static fields
    // ------------------------------------------------------------------------

    /**
     * Version number of this state provider. Please bump this if you modify the
     * contents of the generated state history in some way.
     */
    private static final int VERSION = 10;

    // ------------------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------------------

    private final Map<String, KernelEventHandler> fEventNames;
    private final IKernelAnalysisEventLayout fLayout;

    private final KernelEventHandler fSysEntryHandler;
    private final KernelEventHandler fSysExitHandler;

    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------

    /**
     * Instantiate a new state provider plugin.
     *
     * @param trace
     *            The LTTng 2.0 kernel trace directory
     * @param layout
     *            The event layout to use for this state provider. Usually
     *            depending on the tracer implementation.
     */
    public KernelStateProvider(ITmfTrace trace, IKernelAnalysisEventLayout layout) {
        super(trace, "Kernel"); //$NON-NLS-1$
        fLayout = layout;
        fEventNames = buildEventNames(layout);

        fSysEntryHandler = new SysEntryHandler(fLayout);
        fSysExitHandler = new SysExitHandler(fLayout);
    }

    // ------------------------------------------------------------------------
    // Event names management
    // ------------------------------------------------------------------------

    private static Map<String, KernelEventHandler> buildEventNames(IKernelAnalysisEventLayout layout) {
        ImmutableMap.Builder<String, KernelEventHandler> builder = ImmutableMap.builder();

        builder.put(layout.eventIrqHandlerEntry(), new IrqEntryHandler(layout));
        builder.put(layout.eventIrqHandlerExit(), new IrqExitHandler(layout));
        builder.put(layout.eventSoftIrqEntry(), new SoftIrqEntryHandler(layout));
        builder.put(layout.eventSoftIrqExit(), new SoftIrqExitHandler(layout));
        builder.put(layout.eventSoftIrqRaise(), new SoftIrqRaiseHandler(layout));
        builder.put(layout.eventSchedSwitch(), new SchedSwitchHandler(layout));
        builder.put(layout.eventSchedPiSetprio(), new PiSetprioHandler(layout));
        builder.put(layout.eventSchedProcessFork(), new ProcessForkHandler(layout));
        builder.put(layout.eventSchedProcessExit(), new ProcessExitHandler(layout));
        builder.put(layout.eventSchedProcessFree(), new ProcessFreeHandler(layout));

        final String eventStatedumpProcessState = layout.eventStatedumpProcessState();
        if (eventStatedumpProcessState != null) {
            builder.put(eventStatedumpProcessState, new StateDumpHandler(layout));
        }

        for (String eventSchedWakeup : layout.eventsSchedWakeup()) {
            builder.put(eventSchedWakeup, new SchedWakeupHandler(layout));
        }

        return NonNullUtils.checkNotNull(builder.build());
    }

    // ------------------------------------------------------------------------
    // IStateChangeInput
    // ------------------------------------------------------------------------

    @Override
    public int getVersion() {
        return VERSION;
    }

    @Override
    public void assignTargetStateSystem(ITmfStateSystemBuilder ssb) {
        /* We can only set up the locations once the state system is assigned */
        super.assignTargetStateSystem(ssb);
    }

    @Override
    public KernelStateProvider getNewInstance() {
        return new KernelStateProvider(this.getTrace(), fLayout);
    }

    @Override
    protected void eventHandle(@Nullable ITmfEvent event) {
        if (event == null) {
            return;
        }

        final String eventName = event.getName();

        try {
            final ITmfStateSystemBuilder ss = NonNullUtils.checkNotNull(getStateSystemBuilder());
            /*
             * Feed event to the history system if it's known to cause a state
             * transition.
             */
            KernelEventHandler handler = fEventNames.get(eventName);
            if (handler == null) {
                if (eventName.startsWith(fLayout.eventSyscallExitPrefix())) {
                    handler = fSysExitHandler;
                } else if (eventName.startsWith(fLayout.eventSyscallEntryPrefix())
                        || eventName.startsWith(fLayout.eventCompatSyscallEntryPrefix())) {
                    handler = fSysEntryHandler;
                }
            }
            if (handler != null) {
                handler.handleEvent(ss, event);
            }

        } catch (AttributeNotFoundException ae) {
            /*
             * This would indicate a problem with the logic of the manager here,
             * so it shouldn't happen.
             */
            ae.printStackTrace();

        } catch (TimeRangeException tre) {
            /*
             * This would happen if the events in the trace aren't ordered
             * chronologically, which should never be the case ...
             */
            System.err.println("TimeRangeExcpetion caught in the state system's event manager."); //$NON-NLS-1$
            System.err.println("Are the events in the trace correctly ordered?"); //$NON-NLS-1$
            tre.printStackTrace();

        } catch (StateValueTypeException sve) {
            /*
             * This would happen if we were trying to push/pop attributes not of
             * type integer. Which, once again, should never happen.
             */
            sve.printStackTrace();
        }
    }

}
