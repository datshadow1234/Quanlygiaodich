package Presentation.Model;

import java.util.ArrayList;
import java.util.List;

import Presentation.View.Subscriber;

public class Publisher {
    List<Subscriber> subscribers = new ArrayList<>();
    
    public void registerSubscriber(Subscriber sub){
        this.subscribers.add(sub);
    }
    public void removeSubscriber(Subscriber sub){
        this.subscribers.remove(sub);
    }
    public void notifySubscriber(){
        for(Subscriber ele : subscribers)
            ele.update();
    }
}
