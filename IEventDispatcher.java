package observer;

import java.util.function.Consumer;

public interface IEventDispatcher 
{
	/**
	 * Register to listen for event.
	 * 
	 * @param event Event object want to listen.
	 * @param method This method will be invoked when the event be raised.
	 */
	void RegisterListener(ObserverEvents event, Consumer<Object> method);

	/**
	 * Remove the listener. Use to unregister listener.
	 * 
	 * @param event Event object want to remove.
	 * @param method This method will be removed from observer's event
	 *            dictionary.
	 */
	void RemoveListener(ObserverEvents event, Consumer<Object> method);

	/**
	 * Post an event. This will notify all listener that has been registered for
	 * this event.
	 * 
	 * @param event Event object want to post.
	 * @param param Parameter can be sent with the event.
	 */
	void PostEvent(ObserverEvents event, Object param);

	/**
	 * Post an event with null parameter.
	 * 
	 * @param event Event object want to post.
	 */
	void PostEvent(ObserverEvents event);

	void ClearAllListener();

}