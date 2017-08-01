package observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventsDispatcher
{
    private static EventsDispatcher instance;

    private EventsDispatcher() { }

    public static EventsDispatcher Instance()
    {
        if (instance == null)
        {
            synchronized (EventsDispatcher.class)
            {
                if (instance == null)
                {
                    instance = new EventsDispatcher();
                }
            }
        }
        return instance;
    }

    /**
     * Store all listeners.
     */
    private Map<ObserverEvents, ArrayList<Consumer<Object>>> listenersDict = new HashMap<ObserverEvents, ArrayList<Consumer<Object>>>();

    /**
     * Register to listen for event.
     * 
     * @param event Event object want to listen.
     * @param method This method will be invoked when the event be raised.
     */
    public void RegisterListener(ObserverEvents event,
            Consumer<Object> method)
    {
        if (method == null)
        {
            throw new NullPointerException("Null method: " + method);
        }
        else
        {
            if (listenersDict.containsKey(event))
            {
                listenersDict.get(event).add(method);
            }
            else
            {
                ArrayList<Consumer<Object>> newMethods = new ArrayList<Consumer<Object>>();
                newMethods.add(method);
                listenersDict.put(event, newMethods);
            }
        }
    }

    /**
     * Remove the listener. Use to unregister listener.
     * 
     * @param event Event object want to remove.
     * @param method This method will be removed from observer's event
     *            dictionary.
     */
    public void RemoveListener(ObserverEvents event,
            Consumer<Object> method)
    {
        if (listenersDict.containsKey(event))
        {
            if (method == null)
            {
                throw new NullPointerException("Null method: " + method);
            }
            else
            {
                listenersDict.get(event).remove(method);
            }
        }
        else
        {
            System.out.println("Tried to remove a nonexistent event.");
        }
    }

    /**
     * Post an event. This will notify all listener that has been registered for
     * this event.
     * 
     * @param event Event object want to post.
     * @param param Parameter can be sent with the event.
     */
    public void PostEvent(ObserverEvents event, Object param)
    {
        if (listenersDict.containsKey(event))
        {
            ArrayList<Consumer<Object>> methods = listenersDict.get(event);

            if (methods != null)
            {
                for (Consumer<Object> method : methods)
                {
                    try
                    {
                        method.accept(param);
                    }
                    catch (Exception e)
                    {
                        // If an error occur, remove that method and keep going.
                        RemoveListener(event, method);
                    }
                }
            }
            else
            {
                // This mean there is no remaining listener. Remove this key.
                listenersDict.remove(event);
            }
        }
        else
        {
            System.out.println("Tried to post a nonexistent event.");
        }
    }

    /**
     * Post an event with null parameter.
     * 
     * @param event Event object want to post.
     */
    public void PostEvent(ObserverEvents event)
    {
        PostEvent(event, null);
    }

    public void ClearAllListener()
    {
        listenersDict.clear();
    }
}
