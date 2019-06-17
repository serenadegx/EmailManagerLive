package com.example.emailmanagerlive;

public class Event<T> {
    private T mContent;
    private boolean isHandled;

    public Event(T content) {
        if (content == null){
            throw new IllegalArgumentException("null values in Event are not allowed.");
        }
        this.mContent = content;
    }

    public T getContentIfNotHandled(){
        if (isHandled){
            return null;
        }else {
            isHandled = true;
            return mContent;
        }
    }
}
