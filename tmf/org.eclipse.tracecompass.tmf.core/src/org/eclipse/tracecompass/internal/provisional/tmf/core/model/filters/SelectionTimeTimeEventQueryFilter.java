package org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters;

import java.util.Collection;
import java.util.List;

public class SelectionTimeTimeEventQueryFilter extends SelectionTimeQueryFilter {

    private String fRegex;
    private boolean fHideOthers;

    public SelectionTimeTimeEventQueryFilter(List<Long> times, Collection<Long> items, String regex, boolean hideOthers) {
        super(times, items);
        fRegex = regex;
        fHideOthers = hideOthers;
    }

    public SelectionTimeTimeEventQueryFilter(long start, long end, int n, Collection<Long> items, String regex, boolean hideOthers) {
        super(start, end, n, items);
        fRegex = regex;
        fHideOthers = hideOthers;
    }

    public String getRegex() {
        return fRegex;
    }

    public boolean hideOthers() {
        return fHideOthers;
    }
}
