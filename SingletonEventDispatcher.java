package observer;

public class SingletonEventDispatcher extends EventsDispatcher
{
    private static SingletonEventDispatcher instance;

    private SingletonEventDispatcher() { }

    public static SingletonEventDispatcher Instance()
    {
        if (instance == null)
        {
            synchronized (SingletonEventDispatcher.class)
            {
                if (instance == null)
                {
                    instance = new SingletonEventDispatcher();
                }
            }
        }
        return instance;
    }
}
