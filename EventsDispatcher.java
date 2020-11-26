package observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Note that different instance of this class can't communicate with each other.
 */
public class EventsDispatcher implements IEventDispatcher 
{
    /** (non-Javadoc)
    * Store all listeners.
    */
    private Map<ObserverEvents, ArrayList<Consumer<Object>>> listenersDict = new HashMap<ObserverEvents, ArrayList<Consumer<Object>>>();

    /** (non-Javadoc)
    * @see observer.IEventDispatcher#RegisterListener(observer.ObserverEvents, java.util.function.Consumer)
    */
    @Override
    public void RegisterListener(ObserverEvents event, Consumer<Object> method)
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

    /** (non-Javadoc)
    * @see observer.IEventDispatcher#RemoveListener(observer.ObserverEvents, java.util.function.Consumer)
    */
    @Override
    public void RemoveListener(ObserverEvents event, Consumer<Object> method)
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

    /** (non-Javadoc)
    * @see observer.IEventDispatcher#PostEvent(observer.ObserverEvents, java.lang.Object)
    */
    @Override
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

    /** (non-Javadoc)
    * @see observer.IEventDispatcher#PostEvent(observer.ObserverEvents)
    */
    @Override
    public void PostEvent(ObserverEvents event)
    {
        PostEvent(event, null);
    }

    /** (non-Javadoc)
    * @see observer.IEventDispatcher#ClearAllListener()
    */
    @Override
    public void ClearAllListener()
    {
        listenersDict.clear();
    }
}
