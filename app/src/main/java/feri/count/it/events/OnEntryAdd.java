package feri.count.it.events;

import feri.count.datalib.Entry;

public class OnEntryAdd {
    public Entry entry;

    public OnEntryAdd(Entry entry) {
        this.entry = entry;
    }
}
