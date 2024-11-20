OOP-Java Lab 4 - Design Patterns

The 2 design patterns I chose to implement are the Observer Pattern and the Factory Pattern. 

By adding the observer pattern, I simplified adding events from my AddEventModal by adding EventListPanel as an observer of the JDialog whenever the 'Add Event' button is clicked. Upon the click, notifyObservers() is called and alerts all observers of the new Event that has been created. From there, the EventListPanel calls onEventAdded() and updates the list of events and updates the displayPanel accordingly.

The other pattern I implemented was the factory pattern. I created an EventFactory class to create events of both Meeting and Deadline type. The dialog gets the values of all of it's textboxes and passes that array of Strings into createEvent(). The method then creates and returns the correct event depending on the data being passed in.

The advantages of this were to simplify my code and make it more understandable to someone that can recognize design patterns. Rather than having to dig through my code to find how an event is created and added in the dialog, they can simply look at the EventFactory class and see the one simple method used to create events. Similarly, adding the observer pattern shows the relationship between the AddEventModal and the EventListPanel. The EventListPanel must listen to new events being added, so creating a new channel for them to listen for those changes made the code much more simple

NOTE FOR REVIEWERS:
Observer Pattern - in EventListPanel it adds an observer (line 66), and onEventAdded(Event e) at line 235

Factory Pattern - in EventFactory class, createEvent called at line 94 in AddEventModal
