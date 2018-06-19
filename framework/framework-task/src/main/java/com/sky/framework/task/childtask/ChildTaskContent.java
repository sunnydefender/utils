package com.sky.framework.task.childtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildTaskContent {
    private Map<String, Object> map = new HashMap<>();

    private List<IChildTaskHandler> childTasks = new ArrayList<>();

    public void addChildTask(IChildTaskHandler ... childs) {
        for (int i=0; i<childs.length; i++) {
            childTasks.add(childs[i]);
        }
    }

    public List<IChildTaskHandler> getChildTasks() {
        return childTasks;
    }

    public void set(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }
}
