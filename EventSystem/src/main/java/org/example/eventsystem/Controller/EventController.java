package org.example.eventsystem.Controller;

import org.example.eventsystem.ApiResponse.ApiResponse;
import org.example.eventsystem.Model.Event;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/event-system")
public class EventController {

    ArrayList<Event> events = new ArrayList<>();

    @PostMapping("/create-event")
    public ApiResponse createEvent(@RequestBody Event newEvent){
        for(Event event : events){
            if(event.getId().equals(newEvent.getId()))
                return new ApiResponse("ID already used");
        }
        if(newEvent.getCapacity() < 0)
            return new ApiResponse("Capacity can't be negative");
        if(newEvent.getEndDate().isBefore(newEvent.getStartDate()))
            return new ApiResponse("End date should come after start date");
        events.add(newEvent);
        return new ApiResponse("Event created successfully");
    }

    @GetMapping("/display-events")
    public ArrayList<Event> displayEvents(){
        return events;
    }

    @PutMapping("/update-event/{id}")
    public ApiResponse updateEvent(@PathVariable String id,@RequestBody Event updatedEvent){
        for(int i=0; i<events.size(); i++){
           if(events.get(i).getId().equals(id)){
                updatedEvent.setId(id); //to make sure id not change
                events.set(i,updatedEvent);
                return new ApiResponse("Event changed successfully");
           }
       }
       return new ApiResponse("Event not found");
    }

    @DeleteMapping("/delete-event/{id}")
    public ApiResponse deleteEvent(@PathVariable String id){
        for(int i=0; i<events.size(); i++){
            if(events.get(i).getId().equals(id)){
                events.remove(i);
                return new ApiResponse("Event deleted successfully");
            }
        }
        return new ApiResponse("Event not found");
    }

    @PutMapping("/change-capacity/{id}/{newCapacity}")
    public ApiResponse changeCapacity(@PathVariable String id,@PathVariable int newCapacity){
        if(newCapacity < 0)
            return new ApiResponse("Can't put negative capacity");
        for(Event event: events){
            if(event.getId().equals(id)){
                event.setCapacity(newCapacity);
                return new ApiResponse("Capacity changed successfully");
            }
        }
        return new ApiResponse("Event not found");
    }

    @GetMapping("/search-by-id/{id}")
    public Event searchById(@PathVariable String id){
        for(Event event: events){
            if(event.getId().equals(id))
                return event;
        }
        return null;
    }
}
