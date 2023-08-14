package com.dspot.profile.main.model.profile;

import java.util.List;

public class ProfilePage {

    List<Profile> content;
    boolean last;

    int numberOfElements;

    boolean empty;


    public ProfilePage(List<Profile> content, boolean last, int numberOfElements, boolean empty) {
        this.content = content;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }

    public List<Profile> getContent() {
        return content;
    }

    public void setContent(List<Profile> content) {
        this.content = content;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}




