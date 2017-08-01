# Java Observer Design Pattern

## Add listener
In order to react with an event, an object first has to be registered with that event.

> You can decleare event to use in ObserverEvents.

If the method doesn't has any parameter:
```java
EventsDispatcher.Instance().RegisterListener(ObserverEvents.SampleEvent, (param) -> SampleMethod());
```
Otherwise you can cast it like this:
``` java
EventsDispatcher.Instance().RegisterListener(ObserverEvents.SampleEvent, (param) -> SampleMethod((int)param));
```

## Post event
Can be posted anywhere, this will notify all listeners that have been registered for this event.
You can send a variable with the event:
```java
EventsDispatcher.Instance().PostEvent(ObserverEvents.SampleEvent, sampleVariable);
```
or without it:
```java
EventsDispatcher.Instance().PostEvent(ObserverEvents.SampleEvent);
```

## Remove listener
I'm using functional interface in this pattern, so if you want to remove a listener,
first you have to store it like this:
```java
Consumer<Object> sampleMethodCached = (param) -> SampleMethod();
```
and add it like this:
```java
EventsDispatcher.Instance().RegisterListener(ObserverEvents.SampleEvent, sampleMethodCached);
```
then you can remove it:
```java
EventsDispatcher.Instance().RemoveListener(ObserverEvents.OnEventOne, sampleMethodCached);
```


Feel free to use it. Contact me at lydongcanh@gmail.com if you have something to say.
