// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.Point;
import java.awt.dnd.*;

public class DragSourceEventProxy
    implements Runnable
{

    public DragSourceEventProxy(DragSourceContext dragsourcecontext, int i, int j, int k, Point point, int l)
    {
        context = dragsourcecontext;
        type = i;
        userAction = j;
        targetActions = k;
        x = point.x;
        y = point.y;
        modifiers = l;
        success = false;
    }

    public DragSourceEventProxy(DragSourceContext dragsourcecontext, int i, int j, boolean flag, Point point, int k)
    {
        context = dragsourcecontext;
        type = i;
        userAction = j;
        targetActions = j;
        x = point.x;
        y = point.y;
        modifiers = k;
        success = flag;
    }

    private DragSourceDragEvent newDragSourceDragEvent()
    {
        return new DragSourceDragEvent(context, userAction, targetActions, modifiers, x, y);
    }

    public void run()
    {
        switch(type)
        {
        default:
            return;

        case 1: // '\001'
            context.dragEnter(newDragSourceDragEvent());
            return;

        case 2: // '\002'
            context.dragOver(newDragSourceDragEvent());
            return;

        case 3: // '\003'
            context.dropActionChanged(newDragSourceDragEvent());
            return;

        case 4: // '\004'
            context.dragMouseMoved(newDragSourceDragEvent());
            return;

        case 5: // '\005'
            context.dragExit(new DragSourceEvent(context, x, y));
            return;

        case 6: // '\006'
            context.dragExit(new DragSourceDropEvent(context, userAction, success, x, y));
            break;
        }
    }

    public static final int DRAG_ACTION_CHANGED = 3;
    public static final int DRAG_DROP_END = 6;
    public static final int DRAG_ENTER = 1;
    public static final int DRAG_EXIT = 5;
    public static final int DRAG_MOUSE_MOVED = 4;
    public static final int DRAG_OVER = 2;
    private final DragSourceContext context;
    private final int modifiers;
    private final boolean success;
    private final int targetActions;
    private final int type;
    private final int userAction;
    private final int x;
    private final int y;
}
